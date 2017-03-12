package messenger;

import messenger.controllers.MessageController;
import messenger.controllers.UserController;
import server.*;

public class Router extends server.Router {
    @Override
    public Response getRoute(Request request) {
        switch(request.target) {
            case "/":
                return ResponseFactory.ok();
            case "/messages":
                return MessageController.fetchNewMessagesForUser(request);
            case "/users":
                return UserController.fetchNewUsers();
            default:
                return ResponseFactory.notFound();
        }
    }

    @Override
    public Response postRoute(Request request) {
        switch(request.target) {
            case "/user":
                return UserController.registerNewUser(request);
            case "/message":
                return MessageController.receiveMessage(request);
            default:
                return ResponseFactory.notFound();
        }
    }
}
