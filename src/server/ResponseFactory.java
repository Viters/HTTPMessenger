package server;

import org.json.JSONArray;
import org.json.JSONObject;
import server.interfaces.JSONSerializable;

import java.util.List;
import java.util.Map;

public class ResponseFactory {
    private static String RESPONSE_STATUS_OK = "200 OK";
    private static String RESPONSE_STATUS_NOT_FOUND = "404 Not Found";
    private static String RESPONSE_TYPE_HTML = "text/html";
    private static String RESPONSE_TYPE_JSON = "application/json";

    public static Response allowCORS() {
        return new Response()
                .appendStatusAndReturnSelf(RESPONSE_STATUS_OK)
                .appendCORSHeadersAndReturnSelf();
    }

    public static Response ok() {
        return new Response()
                .appendStatusAndReturnSelf(RESPONSE_STATUS_OK)
                .appendContentTypeAndReturnSelf(RESPONSE_TYPE_HTML);
    }

    public static Response notFound() {
        return new Response()
                .appendStatusAndReturnSelf(RESPONSE_STATUS_NOT_FOUND)
                .appendContentTypeAndReturnSelf(RESPONSE_TYPE_HTML)
                .appendBodyAndReturnSelf("<h1>Not found</h1>");
    }

    public static Response json(List<?> body) {
        JSONArray jsonArray = new JSONArray(body);
        return createResponseWithJSONHeaders()
                .appendBodyAndReturnSelf(jsonArray.toString());
    }

    public static Response json(JSONArray body) {
        return createResponseWithJSONHeaders()
                .appendBodyAndReturnSelf(body.toString());
    }

    public static Response json(Map<?, ?> body) {
        JSONObject jsonObject = new JSONObject(body);
        return createResponseWithJSONHeaders()
                .appendBodyAndReturnSelf(jsonObject.toString());
    }

    public static Response json(JSONObject body) {
        return createResponseWithJSONHeaders()
                .appendBodyAndReturnSelf(body.toString());
    }

    public static Response json(JSONSerializable body) {
        return createResponseWithJSONHeaders()
                .appendBodyAndReturnSelf(body.toJSON().toString());
    }

    private static Response createResponseWithJSONHeaders() {
        return new Response()
                .appendStatusAndReturnSelf(RESPONSE_STATUS_OK)
                .appendContentTypeAndReturnSelf(RESPONSE_TYPE_JSON)
                .appendCORSHeadersAndReturnSelf();
    }
}
