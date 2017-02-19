package server;

import java.util.ArrayList;
import java.util.List;

public class Response {
    private List<String> headers;
    private String body;

    public String make() {
        String headers = formatHeaders();
        return headers + body;
    }

    public Response() {
        headers = new ArrayList<>();
    }

    public Response appendHeader(String header) {
        headers.add(header);
        return this;
    }

    public Response appendStatus(String status) {
        return appendHeader("HTTP/1.1 " + status);
    }

    public Response appendConnection(String connection) {
        return appendHeader("Connection: " + connection);
    }

    public Response appendContentType(String contentType) {
        return appendHeader("Content-Type: " + contentType);
    }

    public Response appendCORSHeaders() {
        this.appendHeader("Access-Control-Allow-Origin: *")
            .appendHeader("Access-Control-Allow-Methods: GET, POST, OPTIONS")
            .appendHeader("Access-Control-Allow-Headers: Content-Type, Authorization, Content-Length, X-Requested-With");
        return this;
    }

    public Response appendBody(String body) {
        this.body = body;
        return this;
    }

    private String formatHeaders() {
        String header = "";
        for (String s : headers) {
            header += s + "\r\n";
        }
        header += "\r\n";
        return header;
    }

    @Override
    public String toString() {
        return "Response{" +
                "headers=" + headers +
                ", body='" + body + '\'' +
                '}';
    }
}
