package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Viters on 10.01.2017.
 */
public class HTTPServer {
    private ServerSocket serverSocket;
    private ExecutorService executorService;
    private Router router;
    private static State state;

    public HTTPServer(int port, int threads, Router router, State state) throws IOException {
        executorService = Executors.newFixedThreadPool(threads);
        serverSocket = new ServerSocket(port);
        this.router = router;
        this.state = state;
        loop();
    }

    public static State getState() {
        return state;
    }

    public void loop() throws IOException {
        while (true) {
            Socket client = serverSocket.accept();
            executorService.submit(new Processor(client, router));
        }
    }
}
