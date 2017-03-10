package messenger;

import com.google.common.collect.ImmutableMap;
import messenger.models.Message;
import messenger.models.User;
import org.json.JSONObject;
import server.*;
import java.util.ArrayList;
import java.util.Map;

class MessengerController extends Controller {
    private static MessengerState state = (MessengerState) HTTPServer.getState();

    static Response fetchNewMessagesForUser(Request request) {
        String userToken = request.body.get("apiToken");
        int id = Integer.parseInt(request.body.get("id"));
        ArrayList<JSONObject> messages = state.getSerializedMessagesForUser(userToken, id);
        return ResponseFactory.json(messages);
    }

    static Response fetchNewUsers(Request request) {
        ArrayList<JSONObject> users = state.getSerializedUsers();
        return ResponseFactory.json(users);
    }

    static Response createNewMessage(Request request) {
        String userToken = request.body.get("apiToken");
        String messageText = request.body.get("content");
        int idTo = Integer.parseInt(request.body.get("toUser"));
        User from = state.getUserByToken(userToken);
        User to = state.getUserById(idTo);

        if (from == null || to == null)
            return ResponseFactory.notFound();

        int lastId = 0;
        if (state.messages.size() > 0) {
            lastId = state.messages.get(state.messages.size() - 1).id;
        }
        Message message = new Message(lastId + 1, messageText, from, to);
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

    static Response registerNewUser(Request request) {
        String name = request.body.get("name");

        User user = state.registerUser(name);

        Map<String, String> responseData = ImmutableMap.of(
                "id", user.id.toString(),
                "name", user.name,
                "token", user.token
        );
        return ResponseFactory.json(responseData);
    }
}