package messenger.controllers;

import messenger.State;
import messenger.exceptions.UserNotFoundException;
import messenger.models.Message;
import messenger.models.User;
import org.json.JSONObject;
import server.*;

import java.util.ArrayList;

public class MessageController extends Controller {
    private static State state = (State) HTTPServer.getState();

    public static Response fetchNewMessagesForUser(Request request) {
        String userToken = request.body.get("apiToken");
        int id = Integer.parseInt(request.body.get("id"));
        ArrayList<JSONObject> messages = state.messages.getSerializedMessagesForUser(userToken, id);
        return ResponseFactory.json(messages);
    }

    public static Response createNewMessage(Request request) {
        try {
            String senderToken = request.body.get("apiToken");
            String messageText = request.body.get("content");
            int receiverId = Integer.parseInt(request.body.get("receiverId"));
            User sender = state.users.getByToken(senderToken);
            User receiver = state.users.getById(receiverId);

            Message message = state.messages.saveMessage(messageText, sender, receiver);

            return ResponseFactory.json(message.toJSON());
        } catch (UserNotFoundException e) {
            return ResponseFactory.notFound();
        }
    }
}
