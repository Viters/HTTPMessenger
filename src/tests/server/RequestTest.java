package server;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.assertj.core.api.Assertions.assertThat;

class RequestTest {
    private static final String exampleGetRequest;
    private static final String examplePostRequest;
    private static final String exampleOptionsRequest;

    static {
        exampleGetRequest = "GET http://www.w3.org/pub/WWW/TheProject.html?data=test HTTP/1.1";
        examplePostRequest =
                "POST /user HTTP/1.1\n" +
                        "Host: localhost:8080\n" +
                        "Connection: keep-alive\n" +
                        "Content-Length: 139\n" +
                        "Postman-Token: 8c1905bc-d60e-b691-947f-7c05bb7fda8b\n" +
                        "Cache-Control: no-cache\n" +
                        "Origin: chrome-extension://fhbjgbiflinjbdggehcddcbncdddomop\n" +
                        "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36\n" +
                        "Content-Type: application/x-www-form-urlencoded\n" +
                        "Accept: */*\n" +
                        "Accept-Encoding: gzip, deflate, br\n" +
                        "Accept-Language: pl-PL,pl;q=0.8,en-US;q=0.6,en;q=0.4\n" +
                        "\n" +
                        "------WebKitFormBoundaryAIXmQw4jM3Umma2B\n" +
                        "Content-Disposition: form-data; name=\"name\"\n" +
                        "\n" +
                        "test\n" +
                        "------WebKitFormBoundaryAIXmQw4jM3Umma2B--";
        exampleOptionsRequest = "OPTIONS * HTTP/1.1";
    }

    @Test
    void checkIfGetMethodIsCorrectlyAssigned() throws IOException {
        // given
        StringReader clientInput = new StringReader(exampleGetRequest);

        // when
        Request request = new Request(new BufferedReader(clientInput));
        boolean[] methodCheck = {request.isGet(), request.isPost(), request.isOption()};

        // then
        assertThat(methodCheck).isEqualTo(new boolean[]{true, false, false});
    }

    @Test
    void checkIfPostMethodIsCorrectlyAssigned() throws IOException {
        // given
        StringReader clientInput = new StringReader(examplePostRequest);

        // when
        Request request = new Request(new BufferedReader(clientInput));
        boolean[] methodCheck = {request.isGet(), request.isPost(), request.isOption()};

        // then
        assertThat(methodCheck).isEqualTo(new boolean[]{false, true, false});
    }

    @Test
    void checkIfOptionsMethodIsCorrectlyAssigned() throws IOException {
        // given
        StringReader clientInput = new StringReader(exampleOptionsRequest);

        // when
        Request request = new Request(new BufferedReader(clientInput));
        boolean[] methodCheck = {request.isGet(), request.isPost(), request.isOption()};

        // then
        assertThat(methodCheck).isEqualTo(new boolean[]{false, false, true});
    }
}