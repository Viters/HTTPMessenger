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

    public static Response json(List<?> data) {
        JSONArray jsonArray = new JSONArray(data);
        return createResponseWithJSONHeaders()
                .appendBody(jsonArray.toString());
    }

    public static Response json(JSONArray data) {
        return createResponseWithJSONHeaders()
                .appendBody(data.toString());
    }

    public static Response json(Map<?, ?> data) {
        JSONObject jsonObject = new JSONObject(data);
        return createResponseWithJSONHeaders()
                .appendBody(jsonObject.toString());
    }

    public static Response json(JSONObject data) {
        return createResponseWithJSONHeaders()
                .appendBody(data.toString());
    }

    private static Response createResponseWithJSONHeaders() {
        return new Response()
                .appendStatus(RESPONSE_STATUS_OK)
                .appendContentType(RESPONSE_TYPE_JSON)
                .appendCORSHeaders();
    }
}
