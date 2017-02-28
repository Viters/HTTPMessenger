package server;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class RequestTest {
    private static final String exampleGetRequest;
    private static final String examplePostRequest;
    private static final String exampleOptionsRequest;

    static {
        exampleGetRequest = "GET http://www.w3.org/pub/WWW/TheProject.html?data=test&field=value HTTP/1.1";
        examplePostRequest =
                "POST /user HTTP/1.1\r\n" +
                        "Host: localhost:8080\r\n" +
                        "Connection: keep-alive\r\n" +
                        "Content-Length: 139\r\n" +
                        "Postman-Token: 8c1905bc-d60e-b691-947f-7c05bb7fda8b\r\n" +
                        "Cache-Control: no-cache\r\n" +
                        "Origin: chrome-extension://fhbjgbiflinjbdggehcddcbncdddomop\r\n" +
                        "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36\r\n" +
                        "Content-Type: application/x-www-form-urlencoded\r\n" +
                        "Accept: */*\r\n" +
                        "Accept-Encoding: gzip, deflate, br\r\n" +
                        "Accept-Language: pl-PL,pl;q=0.8,en-US;q=0.6,en;q=0.4\r\n" +
                        "\r\n" +
                        "------WebKitFormBoundaryAIXmQw4jM3Umma2B\r\n" +
                        "Content-Disposition: form-data; name=\"name\"\r\n" +
                        "\r\n" +
                        "test\r\n" +
                        "------WebKitFormBoundaryAIXmQw4jM3Umma2B--";
        exampleOptionsRequest = "OPTIONS * HTTP/1.1";
    }

    @Test
    void getMethodIsCorrectlyAssigned() throws IOException {
        // given
        Request request = parseClientRequestAndMakeRequestObject(exampleGetRequest);

        // when
        boolean[] requestMethodsChecks = {request.isGet(), request.isPost(), request.isOption()};

        // then
        assertThat(requestMethodsChecks).isEqualTo(new boolean[]{true, false, false});
    }

    @Test
    void postMethodIsCorrectlyAssigned() throws IOException {
        // given
        Request request = parseClientRequestAndMakeRequestObject(examplePostRequest);

        // when
        boolean[] requestMethodsChecks = {request.isGet(), request.isPost(), request.isOption()};

        // then
        assertThat(requestMethodsChecks).isEqualTo(new boolean[]{false, true, false});
    }

    @Test
    void optionsMethodIsCorrectlyAssigned() throws IOException {
        // given
        Request request = parseClientRequestAndMakeRequestObject(exampleOptionsRequest);

        // when
        boolean[] requestMethodsChecks = {request.isGet(), request.isPost(), request.isOption()};

        // then
        assertThat(requestMethodsChecks).isEqualTo(new boolean[]{false, false, true});
    }

    @Test
    void getMethodHasCorrectUrl() throws IOException {
        // given
        Request request = parseClientRequestAndMakeRequestObject(exampleGetRequest);

        // when
        String requestUrl = request.url;

        // then
        assertThat(requestUrl).isEqualTo("http://www.w3.org/pub/WWW/TheProject.html");
    }

    @Test
    void postMethodHasCorrectUrl() throws IOException {
        // given
        Request request = parseClientRequestAndMakeRequestObject(examplePostRequest);

        // when
        String requestUrl = request.url;

        // then
        assertThat(requestUrl).isEqualTo("/user");
    }

    @Test
    void getMethodHasCorrectData() throws IOException {
        // given
        Request request = parseClientRequestAndMakeRequestObject(exampleGetRequest);

        // when
        Map<String, String> requestData = request.data;

        // then
        assertThat(requestData).containsEntry("data", "test").containsEntry("field", "value");
    }

    @Test
    void postMethodHasCorrectData() throws IOException {
        // given
        Request request = parseClientRequestAndMakeRequestObject(examplePostRequest);

        // when
        Map<String, String> requestData = request.data;

        // then
        assertThat(requestData).containsEntry("name", "test");
    }

    private static Request parseClientRequestAndMakeRequestObject(String clientRequest) throws IOException {
        StringReader clientInput = new StringReader(clientRequest);
        return RequestParser.parseRequest(new BufferedReader(clientInput));
    }
}