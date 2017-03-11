package messenger;

import server.HTTPServer;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            server.Router router = new Router();
            server.State state = new State();
            HTTPServer server = new HTTPServer(8080, 4, router, state);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
