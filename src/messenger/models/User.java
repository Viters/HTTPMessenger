package messenger.models;

import com.google.common.collect.ImmutableMap;
import messenger.helpers.TokenGenerator;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
        Map<String, String> data = ImmutableMap.of(
                "id", id.toString(),
                "name", name,
                "token", token
        );
        return new JSONObject(data);
    }
}
