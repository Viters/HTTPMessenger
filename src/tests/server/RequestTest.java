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
        exampleGetRequest = "GET http://www.w3.org/pub/WWW/TheProject.html HTTP/1.1";
        examplePostRequest = "";
        exampleOptionsRequest = "";
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