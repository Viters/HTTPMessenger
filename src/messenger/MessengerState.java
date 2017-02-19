package messenger;

import org.json.JSONObject;
import server.State;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Viters on 16.01.2017.
 */
public class MessengerState extends State {
    private ArrayList<User> users;
    private ArrayList<Message> messages;

    public MessengerState() {
        users = new ArrayList<>();
        messages = new ArrayList<>();
    }

    public ArrayList<User> getUsers() {
        return users;
    }
    public ArrayList<Message> getMessages() {
        return messages;
    }

    public ArrayList<JSONObject> getSerializedMessagesForUser(String userToken, int lastId) {
        ArrayList<JSONObject> messages = new ArrayList<>();
        this.messages.forEach(m -> {
            if (m.getTo().getToken().equals(userToken) && m.getId() > lastId) {
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
            if (user.getToken().equals(token)) {
                return user;
            }
        }
        return null;
    }

    public User getUserById(int id) {
        Iterator<User> iter = users.iterator();
        while (iter.hasNext()) {
            User user = iter.next();
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }
}
