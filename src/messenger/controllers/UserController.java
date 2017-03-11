package messenger.controllers;

import com.google.common.collect.ImmutableMap;
import messenger.State;
import messenger.models.User;
import org.json.JSONObject;
import server.*;

import java.util.ArrayList;
import java.util.Map;

public class UserController extends Controller {
    private static State state = (State) HTTPServer.getState();

    static Response fetchNewUsers(Request request) {
        ArrayList<JSONObject> users = state.users.getSerializedUsers();
        return ResponseFactory.json(users);
    }

    static Response registerNewUser(Request request) {
        String name = request.body.get("name");

        User user = state.users.registerUser(name);

        Map<String, String> responseData = ImmutableMap.of(
                "id", user.id.toString(),
                "name", user.name,
                "token", user.token
        );
        return ResponseFactory.json(responseData);
    }
}
