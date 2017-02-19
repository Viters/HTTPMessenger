import org.junit.jupiter.api.Test;
import server.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResponseTest {
    @Test
    void formatResponseAsStringValidForSending() {
        // given
        Response response = new Response();
        response.appendConnectionAndReturnSelf("close")
                .appendBodyAndReturnSelf("Hello!");

        // when
        String responseToBeSent = response.make();

        // then
        assertEquals("Connection: close\r\n\r\nHello!", responseToBeSent);
    }
}