package server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Request {
    public String method;
    public String url;
    public Map<String, String> data;

    Request() throws IOException {
        data = new HashMap<>();
    }

    boolean isGet() {
        return method.equalsIgnoreCase("GET");
    }

    boolean isPost() {
        return method.equalsIgnoreCase("POST");
    }

    boolean isOption() {
        return method.equalsIgnoreCase("OPTIONS");
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
