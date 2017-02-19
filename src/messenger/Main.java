package messenger;

import server.HTTPServer;
import server.Router;
import server.State;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            Router router = new MessengerRouter();
            State state = new MessengerState();
            HTTPServer server = new HTTPServer(8080, 4, router, state);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
