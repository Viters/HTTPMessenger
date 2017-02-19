package server;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class ResponseFactory {
    private static String RESPONSE_STATUS_OK = "200 OK";
    private static String RESPONSE_STATUS_NOT_FOUND = "404 Not Found";
    private static String RESPONSE_TYPE_HTML = "text/html";
    private static String RESPONSE_TYPE_JSON = "application/json";

    public static Response allowCORS() {
        return new Response()
                .appendStatus(RESPONSE_STATUS_OK)
                .appendCORSHeaders();
    }

    public static Response ok() {
        return new Response()
                .appendStatus(RESPONSE_STATUS_OK)
                .appendContentType(RESPONSE_TYPE_HTML);
    }

    public static Response notFound() {
        return new Response()
                .appendStatus(RESPONSE_STATUS_NOT_FOUND)
                .appendContentType(RESPONSE_TYPE_HTML)
                .appendBody("<h1>Not found</h1>");
    }

    public static Response json(List<?> body) {
        JSONArray jsonArray = new JSONArray(body);
        return createResponseWithJSONHeaders()
                .appendBody(jsonArray.toString());
    }

    public static Response json(JSONArray body) {
        return createResponseWithJSONHeaders()
                .appendBody(body.toString());
    }

    public static Response json(Map<?, ?> body) {
        JSONObject jsonObject = new JSONObject(body);
        return createResponseWithJSONHeaders()
                .appendBody(jsonObject.toString());
    }

    public static Response json(JSONObject body) {
        return createResponseWithJSONHeaders()
                .appendBody(body.toString());
    }

    private static Response createResponseWithJSONHeaders() {
        return new Response()
                .appendStatus(RESPONSE_STATUS_OK)
                .appendContentType(RESPONSE_TYPE_JSON)
                .appendCORSHeaders();
    }
}
