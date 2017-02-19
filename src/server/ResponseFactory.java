package server;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by Viters on 10.01.2017.
 */
public class ResponseFactory {
    static String RESPONSE_STATUS_OK = "200 OK";
    static String RESPONSE_STATUS_NOT_FOUND = "404 Not Found";
    static String RESPONSE_TYPE_HTML = "text/html";
    static String RESPONSE_TYPE_JSON = "application/json";

    public static Response AllowCORS() {
        Response response = new Response();
        response.appendStatus(RESPONSE_STATUS_OK)
                .appendCORSHeaders();
        return response;
    }

    public static Response OK() {
        Response response = new Response();
        response.appendStatus(RESPONSE_STATUS_OK)
                .appendConnection("close")
                .appendContentType(RESPONSE_TYPE_HTML)
                .appendCORSHeaders();
        return response;
    }

    public static Response NotFound() {
        Response response = new Response();
        response.appendStatus(RESPONSE_STATUS_NOT_FOUND)
                .appendConnection("close")
                .appendContentType(RESPONSE_TYPE_HTML)
                .appendCORSHeaders();
        response.setData("<h1>Not found</h1>");
        return response;
    }

    public static Response jsonResponse(List<?> data) {
        Response response = new Response();
        response.appendStatus(RESPONSE_STATUS_OK)
                .appendConnection("close")
                .appendContentType(RESPONSE_TYPE_JSON)
                .appendCORSHeaders();
        JSONArray jsonArray = new JSONArray(data);
        response.setData(jsonArray.toString());
        return response;
    }

    public static Response jsonResponse(JSONArray data) {
        Response response = new Response();
        response.appendStatus(RESPONSE_STATUS_OK)
                .appendConnection("close")
                .appendContentType(RESPONSE_TYPE_JSON)
                .appendCORSHeaders();
        response.setData(data.toString());
        return response;
    }

    public static Response jsonResponse(Map<?, ?> data) {
        Response response = new Response();
        response.appendStatus(RESPONSE_STATUS_OK)
                .appendConnection("close")
                .appendContentType(RESPONSE_TYPE_JSON)
                .appendCORSHeaders();
        JSONObject jsonObject = new JSONObject(data);
        response.setData(jsonObject.toString());
        return response;
    }

    public static Response jsonResponse(JSONObject data) {
        Response response = new Response();
        response.appendStatus(RESPONSE_STATUS_OK)
                .appendConnection("close")
                .appendContentType(RESPONSE_TYPE_JSON)
                .appendCORSHeaders();
        response.setData(data.toString());
        return response;
    }
}
