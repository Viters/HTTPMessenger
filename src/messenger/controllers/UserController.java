package messenger.controllers;

import messenger.State;
import messenger.models.User;
import org.json.JSONObject;
import server.*;

import java.util.ArrayList;

public class UserController extends Controller {
    private static State state = (State) HTTPServer.getState();

    public static Response fetchNewUsers() {
        ArrayList<JSONObject> users = state.users.serialize();

        return ResponseFactory.json(users);
    }

    public static Response registerNewUser(Request request) {
        String name = request.body.get("name");
        User user = state.users.register(name);

        return ResponseFactory.json(user.toJSON());
    }
}
