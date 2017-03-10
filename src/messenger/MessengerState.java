package messenger;

import messenger.models.Message;
import messenger.models.User;
import org.json.JSONObject;
import server.State;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Stack;

public class MessengerState extends State {
    public Stack<User> users;
    public ArrayList<Message> messages;

    public MessengerState() {
        users = new Stack<>();
        messages = new ArrayList<>();
    }

    public User registerUser(String name) {
        User newUser = new User(this.getNextValidUserId(), name, new Date());
        users.push(newUser);

        return newUser;
    }

    private int getNextValidUserId() {
        if (users.isEmpty()) {
            return 0;
        }
        else {
            return users.peek().id + 1;
        }
    }

    public ArrayList<JSONObject> getSerializedMessagesForUser(String userToken, int lastId) {
        ArrayList<JSONObject> messages = new ArrayList<>();
        this.messages.forEach(m -> {
            if (m.toUser.token.equals(userToken) && m.id > lastId) {
                messages.add(m.toJSON());
            }
        });
        return messages;
    }

    public ArrayList<JSONObject> getSerializedUsers() {
        ArrayList<JSONObject> users = new ArrayList<>();
        this.users.forEach(u -> {
            users.add(u.toJSON());
        });
        return users;
    }

    public User getUserByToken(String token) {
        Iterator<User> iter = users.iterator();
        while (iter.hasNext()) {
            User user = iter.next();
            if (user.token.equals(token)) {
                return user;
            }
        }
        return null;
    }

    public User getUserById(int id) {
        Iterator<User> iter = users.iterator();
        while (iter.hasNext()) {
            User user = iter.next();
            if (user.id == id) {
                return user;
            }
        }
        return null;
    }
}
