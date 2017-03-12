package messenger.controllers;

import messenger.State;
import messenger.exceptions.UserNotFoundException;
import messenger.models.Message;
import messenger.models.User;
import server.*;

import java.util.ArrayList;

public class MessageController extends Controller {
    private static State state = (State) HTTPServer.getState();

    public static Response fetchNewMessagesForUser(Request request) {
        try {
            String userToken = request.body.get("apiToken");
            User user = state.users.getByToken(userToken);

            ArrayList<Message> messages = new ArrayList<>();
            user.pendingMessages.removeIf(messages::add);

            return ResponseFactory.json(messages);
        } catch (UserNotFoundException e) {
            return ResponseFactory.notFound();
        }
    }

    public static Response receiveMessage(Request request) {
        try {
            String senderToken = request.body.get("apiToken");
            String messageText = request.body.get("content");
            int receiverId = Integer.parseInt(request.body.get("receiverId"));
            User sender = state.users.getByToken(senderToken);
            User receiver = state.users.getById(receiverId);

            Message message = state.messages.saveMessage(messageText, sender, receiver);
            receiver.pendingMessages.add(message);

            return ResponseFactory.json(message.toJSON());
        } catch (UserNotFoundException e) {
            return ResponseFactory.notFound();
        }
    }
}
