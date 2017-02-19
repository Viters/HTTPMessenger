package server.tests;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import server.Response;
import server.ResponseFactory;

import java.util.ArrayList;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by sir.viters on 19.02.2017.
 */
class ResponseFactoryTest {
    @Test
    void allowCORSResponseContainsNeededData() {
        // given
        Response response = ResponseFactory.allowCORS();

        // when
        String responseToBeSent = response.make();

        // then
        assertThat(responseToBeSent)
                .contains("HTTP")
                .contains("200 OK")
                .contains("Access-Control-Allow-Origin")
                .contains("Access-Control-Allow-Methods")
                .contains("Access-Control-Allow-Headers");
    }

    @Test
    void okResponseContainsNeededData() {
        // given
        Response response = ResponseFactory.ok();

        // when
        String responseToBeSent = response.make();

        // then
        assertThat(responseToBeSent)
                .contains("HTTP")
                .contains("200 OK");
    }

    @Test
    void notFoundResponseContainsNeededData() {
        // given
        Response response = ResponseFactory.notFound();

        // when
        String responseToBeSent = response.make();

        // then
        assertThat(responseToBeSent)
                .contains("HTTP")
                .contains("404 Not Found");
    }

    @Test
    void jsonResponseCreatedWithListContainsNeededData() {
        // given
        ArrayList<String> body = new ArrayList<>();
        body.add("val1");
        body.add("val2");
        Response response = ResponseFactory.json(body);

        // when
        String responseToBeSent = response.make();

        // then
        assertThat(responseToBeSent)
                .contains("HTTP")
                .contains("200 OK")
                .contains("Access-Control-Allow-Origin")
                .contains("Access-Control-Allow-Methods")
                .contains("Access-Control-Allow-Headers")
                .contains("val1")
                .contains("val2");
    }

    @Test
    void jsonResponseCreatedWithJsonArrayContainsNeededData() {
        // given
        JSONArray body = new JSONArray();
        body.put("val1");
        body.put("val2");
        Response response = ResponseFactory.json(body);

        // when
        String responseToBeSent = response.make();

        // then
        assertThat(responseToBeSent)
                .contains("HTTP")
                .contains("200 OK")
                .contains("Access-Control-Allow-Origin")
                .contains("Access-Control-Allow-Methods")
                .contains("Access-Control-Allow-Headers")
                .contains("\"val1\"")
                .contains("\"val2\"");
    }

    @Test
    void jsonResponseCreatedWithMapContainsNeededData() {
        // given
        HashMap<String, String> body = new HashMap<>();
        body.put("key1", "val1");
        body.put("key2", "val2");
        Response response = ResponseFactory.json(body);

        // when
        String responseToBeSent = response.make();

        // then
        assertThat(responseToBeSent)
                .contains("HTTP")
                .contains("200 OK")
                .contains("Access-Control-Allow-Origin")
                .contains("Access-Control-Allow-Methods")
                .contains("Access-Control-Allow-Headers")
                .contains("\"key1\":\"val1\"")
                .contains("\"key2\":\"val2\"");
    }

    @Test
    void jsonResponseCreatedWithJsonObjectContainsNeededData() {
        // given
        JSONObject body = new JSONObject();
        body.put("key1", "val1");
        body.put("key2", "val2");
        Response response = ResponseFactory.json(body);

        // when
        String responseToBeSent = response.make();

        // then
        assertThat(responseToBeSent)
                .contains("HTTP")
                .contains("200 OK")
                .contains("Access-Control-Allow-Origin")
                .contains("Access-Control-Allow-Methods")
                .contains("Access-Control-Allow-Headers")
                .contains("\"key1\":\"val1\"")
                .contains("\"key2\":\"val2\"");
    }

}