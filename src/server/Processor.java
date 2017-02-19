package server;

import java.io.*;
import java.net.Socket;

public class Processor implements Runnable {
    private Socket client;
    private Router router;

    public Processor(Socket client, Router router) {
        this.client = client;
        this.router = router;
    }

    @Override
    public void run() {
        try {
            Request request = parseRequest();
            System.out.println(request);
            Response response = router.route(request);
            System.out.println(response);
            sendResponse(response);
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Request parseRequest() throws IOException {
        BufferedReader clientInput = new BufferedReader(new InputStreamReader(client.getInputStream()));

        return new Request(clientInput);
    }

    private void sendResponse(Response response) throws IOException {
        DataOutputStream outputStream = new DataOutputStream(client.getOutputStream());
        outputStream.write(response.make().getBytes());
        outputStream.flush();
        outputStream.close();
    }
}
