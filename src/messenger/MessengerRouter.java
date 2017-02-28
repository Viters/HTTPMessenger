package messenger;

import server.*;

public class MessengerRouter extends Router {
    @Override
    public Response getRoute(Request request) {
        switch(request.url) {
            case "/":
                return ResponseFactory.ok().appendBodyAndReturnSelf("<h1>Hello!</h1>");
            case "/messages":
                return MessengerController.fetchNewMessagesForUser(request);
            case "/users":
                return MessengerController.fetchNewUsers(request);
            default:
                return ResponseFactory.notFound();
        }
    }

    @Override
    public Response postRoute(Request request) {
        switch(request.url) {
            case "/user":
                return MessengerController.registerNewUser(request);
            case "/message":
                return MessengerController.createNewMessage(request);
            default:
                return ResponseFactory.notFound();
        }
    }
}
