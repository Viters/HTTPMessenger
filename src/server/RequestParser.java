package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestParser {
    private final static Pattern VAR_NAME_PATTERN;
    private final static Pattern CONTENT_LENGTH_PATTERN;
    private final static String LINE_SEPARATOR = "\r\n";

    private Request request;
    private String clientRequestFirstLine;

    static {
        // matches: name="value"
        VAR_NAME_PATTERN = Pattern.compile("(?<=name\\=\\\")(.+)(?=\\\")");

        // matches: Content-Length: [any int]
        CONTENT_LENGTH_PATTERN = Pattern.compile("(?<=Content-Length\\: )([0-9]+)");
    }

    public Request parseRequest(BufferedReader clientBufferedRequest) throws IOException {
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
        String targetWithBody = extractTargetFromRequestFirstLine();
        String[] targetAndBodyParted = targetWithBody.split("\\?");
        request.target = targetAndBodyParted[0];
        boolean requestHasBody = targetAndBodyParted.length > 1;
        if (requestHasBody) {
            request.body = extractGetRequestBody(targetAndBodyParted[1]);
        }
    }

    private Map<String, String> extractGetRequestBody(String bodyUrlEncoded) throws UnsupportedEncodingException {
        HashMap<String, String> body = new HashMap<>();
        String[] pairs = bodyUrlEncoded.split("&");
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

        String data;
        int contentLength = 0;
        while ((data = clientBufferedRequest.readLine()).length() > 0) {
            Matcher matcher = CONTENT_LENGTH_PATTERN.matcher(data);
            if (matcher.find()) {
                contentLength = Integer.parseInt(matcher.group(0));
            }
        }

        if (contentLength == 0) {
            return;
        }

        clientBufferedRequest.readLine();
        char buffer[] = new char[contentLength];
        clientBufferedRequest.read(buffer);
        String requestBody = new String(buffer);
        ArrayList requestBodySplit = new ArrayList<>(
                Arrays.asList(requestBody.split(LINE_SEPARATOR))
        );

        Iterator<String> requestIterator = requestBodySplit.iterator();

        while (requestIterator.hasNext()) {
            String line = requestIterator.next();
            Matcher matcher = VAR_NAME_PATTERN.matcher(line);
            if (matcher.find()) {
                String name = matcher.group(0);
                requestIterator.next();
                String value = requestIterator.next();
                request.body.put(name, value);
            }
        }
    }

    private String extractTargetFromRequestFirstLine() {
        return clientRequestFirstLine.substring(
                clientRequestFirstLine.indexOf(" ") + 1,
                clientRequestFirstLine.lastIndexOf("HTTP") - 1);
    }
}
