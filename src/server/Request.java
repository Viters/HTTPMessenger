package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Request {
    private String method;
    private String url;
    private Map<String, String> data;
    private static Pattern varNamePattern;
    private static Pattern contentLengthPattern;

    static {
        varNamePattern = Pattern.compile("(?<=name\\=\\\")(.+)(?=\\\")");
        contentLengthPattern = Pattern.compile("(?<=Content-Length\\: )([0-9]+)");
    }

    public Request(BufferedReader request) throws IOException {
        data = new HashMap<>();
        parseRequest(request);
    }

    public String getMethod() {
        return method;
    }
    public String getUrl() {
        return url;
    }
    public Map<String, String> getData() {
        return data;
    }

    public boolean isGet() {
        return method.equalsIgnoreCase("GET");
    }

    public boolean isPost() {
        return method.equalsIgnoreCase("POST");
    }

    public boolean isOption() {
        return method.equalsIgnoreCase("OPTIONS");
    }

    private void parseRequest(BufferedReader request) throws IOException {
        String firstLine = request.readLine();
        String query = "";
        method = firstLine.substring(0, firstLine.indexOf("/") - 1).trim();

        if (this.isOption()) {
            return;
        }

        if (this.isGet()) {
            query = firstLine.substring(4, (firstLine.lastIndexOf("HTTP/1.1")) - 1);
        }
        else if (this.isPost()) {
            query = firstLine.substring(5, (firstLine.lastIndexOf("HTTP/1.1")) - 1);
        }

        if (this.isGet() == true) {
            data = splitQuery(query);
            return;
        }

        url = query;
        String data;
        int contentLength = 0;
        while ((data = request.readLine()).length() > 0) {
            Matcher matcher = contentLengthPattern.matcher(data);
            if (matcher.find()) {
                contentLength = Integer.parseInt(matcher.group(0));
            }
        }

        if (contentLength == 0) {
            return;
        }

        request.readLine();
        char buffer[] = new char[contentLength];
        request.read(buffer);
        String requestBody = new String(buffer);
        ArrayList requestBodySplit = new ArrayList<>(
                Arrays.asList(requestBody.split("\r\n"))
        );

        Iterator<String> requestIterator = requestBodySplit.iterator();

        while (requestIterator.hasNext()) {
            String line = requestIterator.next();
            Matcher matcher = varNamePattern.matcher(line);
            if (matcher.find()) {
                String name = matcher.group(0);
                requestIterator.next();
                String value = requestIterator.next();
                this.data.put(name, value);
            }
        }
    }

    private Map<String, String> splitQuery(String url) throws UnsupportedEncodingException {
        Map<String, String> query_pairs = new HashMap<>();
        ArrayList<String> pairs = new ArrayList<>(Arrays.asList(url.split("&")));
        this.url = pairs.remove(0);
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
        }
        return query_pairs;
    }

    @Override
    public String toString() {
        return "Request{" +
                "method='" + method + '\'' +
                ", url='" + url + '\'' +
                ", data=" + data +
                '}';
    }
}
