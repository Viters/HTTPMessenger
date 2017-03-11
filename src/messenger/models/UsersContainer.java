package messenger.models;

import messenger.exceptions.UserNotFoundException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Stack;

public class UsersContainer {
    private Stack<User> users;

    public UsersContainer() {
        users = new Stack<>();
    }

    public User getByOrdinal(int ordinal) {
        return users.get(ordinal);
    }

    public User getById(int id) throws UserNotFoundException {
        Iterator<User> iter = users.iterator();
        while (iter.hasNext()) {
            User user = iter.next();
            if (user.id == id) {
                return user;
            }
        }
        throw new UserNotFoundException();
    }

    public User getByToken(String token) throws UserNotFoundException {
        Iterator<User> iter = users.iterator();
        while (iter.hasNext()) {
            User user = iter.next();
            if (user.token.equals(token)) {
                return user;
            }
        }
        throw new UserNotFoundException();
    }

    public User registerUser(String name) {
        User user = new User(this.getNextValidUserId(), name, new Date());
        users.push(user);

        return user;
    }

    private int getNextValidUserId() {
        if (users.isEmpty()) {
            return 0;
        }
        else {
            return users.peek().id + 1;
        }
    }

    public ArrayList<JSONObject> serialize() {
        ArrayList<JSONObject> users = new ArrayList<>();
        this.users.forEach(u -> {
            users.add(u.toJSON());
        });
        return users;
    }
}
