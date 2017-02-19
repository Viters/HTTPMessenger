package server;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by Viters on 10.01.2017.
 */
public class ResponseFactory {
    private static String RESPONSE_STATUS_OK = "200 OK";
    private static String RESPONSE_STATUS_NOT_FOUND = "404 Not Found";
    private static String RESPONSE_TYPE_HTML = "text/html";
    private static String RESPONSE_TYPE_JSON = "application/json";

    public static Response AllowCORS() {
        Response response = new Response();
        response.appendStatus(RESPONSE_STATUS_OK)
                .appendCORSHeaders();
        return response;
    }

    public static Response OK() {
        Response response = new Response();
        response.appendStatus(RESPONSE_STATUS_OK)
                .appendContentType(RESPONSE_TYPE_HTML);
        return response;
    }

    public static Response NotFound() {
        Response response = new Response();
        response.appendStatus(RESPONSE_STATUS_NOT_FOUND)
                .appendContentType(RESPONSE_TYPE_HTML)
                .withData("<h1>Not found</h1>");
        return response;
    }

    public static Response jsonResponse(List<?> data) {
        Response response = new Response();
        JSONArray jsonArray = new JSONArray(data);
        response.appendStatus(RESPONSE_STATUS_OK)
                .appendContentType(RESPONSE_TYPE_JSON)
                .appendCORSHeaders()
                .withData(jsonArray.toString());
        return response;
    }

    public static Response jsonResponse(JSONArray data) {
        Response response = new Response();
        response.appendStatus(RESPONSE_STATUS_OK)
                .appendContentType(RESPONSE_TYPE_JSON)
                .appendCORSHeaders()
                .withData(data.toString());
        return response;
    }

    public static Response jsonResponse(Map<?, ?> data) {
        Response response = new Response();
        JSONObject jsonObject = new JSONObject(data);
        response.appendStatus(RESPONSE_STATUS_OK)
                .appendContentType(RESPONSE_TYPE_JSON)
                .appendCORSHeaders().
                withData(jsonObject.toString());
        return response;
    }

    public static Response jsonResponse(JSONObject data) {
        Response response = new Response();
        response.appendStatus(RESPONSE_STATUS_OK)
                .appendContentType(RESPONSE_TYPE_JSON)
                .appendCORSHeaders()
                .withData(data.toString());
        return response;
    }
}
