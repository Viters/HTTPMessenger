package messenger.models;

import com.google.common.collect.ImmutableMap;
import messenger.helpers.TokenGenerator;
import org.json.JSONObject;
import server.interfaces.JSONSerializable;

import java.util.Date;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class User implements JSONSerializable {
    public Integer id;
    public String name;
    public String token;
    public Date createdAt;
    public Queue<Message> pendingMessages;

    public User(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.token = TokenGenerator.nextToken();
        this.createdAt = new Date();
        this.pendingMessages = new LinkedList<>();
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
