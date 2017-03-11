package messenger.models;

import messenger.exceptions.UserNotFoundException;
import org.json.JSONObject;

import java.util.*;

public class UsersContainer {
    private HashMap<Integer, User> users;
    private int nextAvailableId = 0;

    public UsersContainer() {
        users = new HashMap<>();
    }

    public User getById(int id) throws UserNotFoundException {
        users.get(id);
        throw new UserNotFoundException();
    }

    public User getByToken(String token) throws UserNotFoundException {
        Iterator<User> iter = users.values().iterator();
        while (iter.hasNext()) {
            User user = iter.next();
            if (user.token.equals(token)) {
                return user;
            }
        }
        throw new UserNotFoundException();
    }

    public User register(String name) {
        User user = new User(nextAvailableId, name, new Date());
        users.put(nextAvailableId, user);

        nextAvailableId++;
        return user;
    }

    public ArrayList<JSONObject> serialize() {
        ArrayList<JSONObject> users = new ArrayList<>();
        this.users.values().forEach(u -> {
            users.add(u.toJSON());
        });
        return users;
    }
}
