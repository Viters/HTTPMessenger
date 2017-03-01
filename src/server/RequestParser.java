package server;

import com.sun.org.apache.regexp.internal.RE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class RequestParser {
    private final static Pattern VAR_NAME_PATTERN;
    private final static Pattern CONTENT_LENGTH_VALUE_PATTERN;
    private final static String LINE_SEPARATOR = "\r\n";

    private Request request;
    private String clientRequestFirstLine;
    private ArrayList<String> requestHeaders = new ArrayList<>();

    static {
        // matches: name="value"
        VAR_NAME_PATTERN = Pattern.compile("(?<=name\\=\\\")(.+)(?=\\\")");

        // matches: Content-Length: [any int]
        CONTENT_LENGTH_VALUE_PATTERN = Pattern.compile("(?<=Content-Length\\: )([0-9]+)");
    }

    Request parseRequest(BufferedReader clientBufferedRequest) throws IOException {
        prepareNewRequest();

        clientRequestFirstLine = clientBufferedRequest.readLine();

        request.method = extractMethodFromRequestFirstLine();

        if (request.isGet()) {
            processGetRequest();
        } else if (request.isOptions()) {
            processOptionsRequest();
        } else if (request.isPost()) {
            processPostRequest(clientBufferedRequest);
        }

        return request;
    }

    private void prepareNewRequest() throws IOException {
        request = new Request();
    }

    private String extractMethodFromRequestFirstLine() {
        return clientRequestFirstLine.substring(0, clientRequestFirstLine.indexOf(" "));
    }

    private void processGetRequest() throws UnsupportedEncodingException {
        String targetWithURLParameters = extractTargetFromRequestFirstLine();
        String[] targetAndURLParametersParted = targetWithURLParameters.split("\\?");
        request.target = targetAndURLParametersParted[0];
        boolean requestHasBody = targetAndURLParametersParted.length > 1;
        if (requestHasBody) {
            request.body = extractGetRequestBody(targetAndURLParametersParted[1]);
        }
    }

    private String extractTargetFromRequestFirstLine() {
        return clientRequestFirstLine.substring(
                clientRequestFirstLine.indexOf(" ") + 1,
                clientRequestFirstLine.lastIndexOf("HTTP") - 1);
    }

    private Map<String, String> extractGetRequestBody(String bodyURLEncoded) throws UnsupportedEncodingException {
        HashMap<String, String> body = new HashMap<>();
        String[] pairs = bodyURLEncoded.split("&");
        for (String pair : pairs) {
            String key = URLDecoder.decode(pair.substring(0, pair.indexOf("=")), "UTF-8");
            String value = URLDecoder.decode(pair.substring(pair.indexOf("=") + 1), "UTF-8");
            body.put(key, value);
        }
        return body;
    }

    private void processOptionsRequest() {
        request.target = extractTargetFromRequestFirstLine();
    }

    private void processPostRequest(BufferedReader clientBufferedRequest) throws IOException {
        request.target = extractTargetFromRequestFirstLine();

        processPostRequestHeaders(clientBufferedRequest);
        int contentLength = findContentLengthHeaderValue();
        if (contentLength == 0) {
            return;
        }

        ArrayList<String> unprocessedPostBody = getUnprocessedPostBody(clientBufferedRequest, contentLength);
        processPostBody(unprocessedPostBody);
    }

    private void processPostRequestHeaders(BufferedReader clientBufferedRequest) throws IOException {
        String currentHeader;
        do {
            currentHeader = clientBufferedRequest.readLine();
            requestHeaders.add(currentHeader);
        }
        while (currentHeader.length() > 0);
    }

    private int findContentLengthHeaderValue() throws IOException {
        for (String requestHeader : requestHeaders) {
            Matcher lineContainsContentLengthMatcher = CONTENT_LENGTH_VALUE_PATTERN.matcher(requestHeader);
            if (lineContainsContentLengthMatcher.find()) {
                return Integer.parseInt(lineContainsContentLengthMatcher.group(0));
            }
        }
        return 0;
    }

    private ArrayList<String> getUnprocessedPostBody(BufferedReader clientBufferedRequest, int contentLength) throws IOException {
        skipLine(clientBufferedRequest);
        String requestBody = extractPostBodyFromRequest(clientBufferedRequest, contentLength);
        return new ArrayList<>(
                Arrays.asList(requestBody.split(LINE_SEPARATOR))
        );
    }

    private void skipLine(BufferedReader clientBufferedRequest) throws IOException {
        clientBufferedRequest.readLine();
    }

    private String extractPostBodyFromRequest(BufferedReader clientBufferedRequest, int contentLength) throws IOException {
        char bodyBuffer[] = new char[contentLength];
        clientBufferedRequest.read(bodyBuffer);
        return new String(bodyBuffer);
    }

    private void processPostBody(ArrayList<String> unprocessedPostBody) {
        Iterator<String> requestBodyIterator = unprocessedPostBody.iterator();

        while (requestBodyIterator.hasNext()) {
            String line = requestBodyIterator.next();
            Matcher lineContainsNameMatcher = VAR_NAME_PATTERN.matcher(line);
            if (lineContainsNameMatcher.find()) {
                addBodyNameValuePair(requestBodyIterator, lineContainsNameMatcher);
            }
        }
    }

    private void addBodyNameValuePair(Iterator<String> requestBodyIterator, Matcher lineContainsNameMatcher) {
        String name = lineContainsNameMatcher.group(0);
        requestBodyIterator.next();
        String value = requestBodyIterator.next();
        request.body.put(name, value);
    }
}
