package messenger.controllers;

import com.google.common.collect.ImmutableMap;
import messenger.State;
import messenger.models.Message;
import messenger.models.User;
import org.json.JSONObject;
import server.*;

import java.util.ArrayList;
import java.util.Map;

public class MessageController extends Controller {
    private static State state = (State) HTTPServer.getState();

    public static Response fetchNewMessagesForUser(Request request) {
        String userToken = request.body.get("apiToken");
        int id = Integer.parseInt(request.body.get("id"));
        ArrayList<JSONObject> messages = state.messages.getSerializedMessagesForUser(userToken, id);
        return ResponseFactory.json(messages);
    }

    public static Response createNewMessage(Request request) {
        String senderToken = request.body.get("apiToken");
        String messageText = request.body.get("content");
        int receiverId = Integer.parseInt(request.body.get("toUser"));
        User sender = state.users.getUserByToken(senderToken);
        User receiver = state.users.getUserById(receiverId);

        if (sender == null || receiver == null)
            return ResponseFactory.notFound();

        Message message = state.messages.saveMessage(messageText, sender, receiver);

        return ResponseFactory.json(message.toJSON());
    }
}
