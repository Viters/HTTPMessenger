package server;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Viters on 10.01.2017.
 */
public class Response {
    private List<String> headers;
    private String data;

    public String make() {
        String headers = formatHeaders();
        return headers + data;
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
            .appendHeader("Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS")
            .appendHeader("Access-Control-Allow-Headers: Content-Type, Authorization, Content-Length, X-Requested-With");
        return this;
    }

    public Response withData(String data) {
        this.data = data;
        return this;
    }

    public void setData(String data) {
        this.data = data;
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
                ", data='" + data + '\'' +
                '}';
    }
}
