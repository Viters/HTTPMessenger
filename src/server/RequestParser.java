package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestParser {
    private final static Pattern varNamePattern;
    private final static Pattern contentLengthPattern;
    private final static String LINE_SEPARATOR = "\r\n";

    static {
        varNamePattern = Pattern.compile("(?<=name\\=\\\")(.+)(?=\\\")");
        contentLengthPattern = Pattern.compile("(?<=Content-Length\\: )([0-9]+)");
    }

    public static Request parseRequest(BufferedReader clientInputRequest) throws IOException {
        Request request = new Request();
        String firstLine = clientInputRequest.readLine();
        request.method = parseMethod(firstLine);

        request.url = parseUrl(firstLine);

        if (request.isOption()) {
            return request;
        }

        if (request.isGet()) {
            request.data = splitQuery(request);
            return request;
        }

        String data;
        int contentLength = 0;
        while ((data = clientInputRequest.readLine()).length() > 0) {
            Matcher matcher = contentLengthPattern.matcher(data);
            if (matcher.find()) {
                contentLength = Integer.parseInt(matcher.group(0));
            }
        }

        if (contentLength == 0) {
            return request;
        }

        clientInputRequest.readLine();
        char buffer[] = new char[contentLength];
        clientInputRequest.read(buffer);
        String requestBody = new String(buffer);
        ArrayList requestBodySplit = new ArrayList<>(
                Arrays.asList(requestBody.split(LINE_SEPARATOR))
        );

        Iterator<String> requestIterator = requestBodySplit.iterator();

        while (requestIterator.hasNext()) {
            String line = requestIterator.next();
            Matcher matcher = varNamePattern.matcher(line);
            if (matcher.find()) {
                String name = matcher.group(0);
                requestIterator.next();
                String value = requestIterator.next();
                request.data.put(name, value);
            }
        }

        return request;
    }

    private static String parseMethod(String requestFirstLine) {
        return requestFirstLine.substring(0, requestFirstLine.indexOf(" "));
    }

    private static String parseUrl(String requestFirstLine) {
        return requestFirstLine.substring(requestFirstLine.indexOf(" ") + 1, requestFirstLine.lastIndexOf("HTTP") - 1);
    }

    private static Map<String, String> splitQuery(Request request) throws UnsupportedEncodingException {
        Map<String, String> query_pairs = new HashMap<>();
        String[] querySplit = request.url.split("\\?");
        ArrayList<String> pairs = new ArrayList<>(Arrays.asList(querySplit[1].split("&")));
        request.url = querySplit[0];
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
        }
        return query_pairs;
    }
}
