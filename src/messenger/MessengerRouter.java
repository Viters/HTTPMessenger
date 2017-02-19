package messenger;

import server.*;

/**
 * Created by Viters on 15.01.2017.
 */
public class MessengerRouter extends Router {
    @Override
    public Response getRoute(Request request) {
        switch(request.getUrl()) {
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
        switch(request.getUrl()) {
            case "/user":
                return MessengerController.registerNewUser(request);
            case "/message":
                return MessengerController.createNewMessage(request);
            default:
                return ResponseFactory.notFound();
        }
    }
}
