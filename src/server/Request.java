package server;

import java.util.HashMap;
import java.util.Map;

public class Request {
    public String method;
    public String target;
    public Map<String, String> body;

    Request() {
        body = new HashMap<>();
    }

    public boolean isGet() {
        return method.equalsIgnoreCase("GET");
    }

    public boolean isPost() {
        return method.equalsIgnoreCase("POST");
    }

    public boolean isOptions() {
        return method.equalsIgnoreCase("OPTIONS");
    }

    @Override
    public String toString() {
        return "Request{" +
                "method='" + method + '\'' +
                ", target='" + target + '\'' +
                ", body=" + body +
                '}';
    }
}
