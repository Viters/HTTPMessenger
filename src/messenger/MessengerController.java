package messenger;

import com.google.common.collect.ImmutableMap;
import org.json.JSONObject;
import server.*;
import java.util.ArrayList;
import java.util.Date;
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
        String messageText = request.body.get("message");
        int idTo = Integer.parseInt(request.body.get("to"));
        User from = state.getUserByToken(userToken);
        User to = state.getUserById(idTo);

        if (from == null || to == null)
            return ResponseFactory.notFound();

        int lastId = 0;
        if (state.getMessages().size() > 0) {
            lastId = state.getMessages().get(state.getMessages().size() - 1).getId();
        }
        Message message = new Message(lastId + 1, messageText, from, to);
        state.getMessages().add(message);
        Map<String, String> responseData = ImmutableMap.of(
                "id", message.getId().toString(),
                "message", message.getMessage(),
                "from", message.getFrom().getId().toString(),
                "date", message.getCreatedAt().toString(),
                "to", message.getTo().getId().toString()
        );
        return ResponseFactory.json(responseData);
    }

    static Response registerNewUser(Request request) {
        String name = request.body.get("name");
        int lastId = 0;
        if (state.getUsers().size() > 0) {
            lastId = state.getUsers().get(state.getUsers().size() - 1).getId();
        }
        User user = new User(lastId + 1, name, new Date());
        state.getUsers().add(user);
        Map<String, String> responseData = ImmutableMap.of(
                "id", user.getId().toString(),
                "name", user.getName(),
                "token", user.getToken()
        );
        return ResponseFactory.json(responseData);
    }
}