package server;

import server.exceptions.UnknownMethodException;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket client;
    private Router router;

    ClientHandler(Socket client, Router router) {
        this.client = client;
        this.router = router;
    }

    @Override
    public void run() {
        try {
            Request request = parseRequest();
            logRequest(request);
            Response response = prepareResponse(request);
            logResponse(response);
            sendResponse(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Request parseRequest() throws IOException {
        BufferedReader clientInput = new BufferedReader(new InputStreamReader(client.getInputStream()));

        return new RequestParser().parseRequest(clientInput);
    }

    private void logRequest(Request request) {
        System.out.println(request);
    }

    private Response prepareResponse(Request request) {
        try {
            return router.route(request);
        } catch (UnknownMethodException e) {
            e.printStackTrace();
            return new Response();
        }
    }

    private void sendResponse(Response response) throws IOException {
        DataOutputStream outputStream = new DataOutputStream(client.getOutputStream());
        outputStream.write(response.make().getBytes());
        outputStream.flush();
        outputStream.close();
    }

    private void logResponse(Response response) {
        System.out.println(response);
    }
}
