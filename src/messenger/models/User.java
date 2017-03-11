package messenger.models;

import messenger.helpers.TokenGenerator;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;

public class User {
    public Integer id;
    public String name;
    public String token;
    public Date createdAt;

    public User(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.token = TokenGenerator.nextToken();
        this.createdAt = new Date();
    }

    public User(Integer id, String name, Date createdAt) {
        this.id = id;
        this.name = name;
        this.token = TokenGenerator.nextToken();
        this.createdAt = createdAt;
    }

    public JSONObject toJSON() {
        HashMap<String, String> data = new HashMap<>();
        data.put("id", id.toString());
        data.put("name", name);
        return new JSONObject(data);
    }
}
