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

        int lastId = 0;
        if (state.messages.size() > 0) {
            lastId = state.messages.get(state.messages.size() - 1).id;
        }
        Message message = new Message(lastId + 1, messageText, sender, receiver);
        state.messages.add(message);
        Map<String, String> responseData = ImmutableMap.of(
                "id", message.id.toString(),
                "content", message.content,
                "fromUser", message.fromUser.id.toString(),
                "date", message.createdAt.toString(),
                "toUser", message.toUser.id.toString()
        );
        return ResponseFactory.json(responseData);
    }
}
