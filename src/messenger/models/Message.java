package messenger.models;

import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;

public class Message {
    public Integer id;
    public String content;
    public User fromUser;
    public User toUser;
    public Date createdAt;

    public Message(Integer id, String content, User fromUser, User toUser) {
        this.id = id;
        this.content = content;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.createdAt = new Date();
    }

    public JSONObject toJSON() {
        HashMap<String, String> data = new HashMap<>();
        data.put("id", id.toString());
        data.put("content", content);
        data.put("fromUser", fromUser.id.toString());
        data.put("toUser", toUser.id.toString());
        data.put("date", createdAt.toString());
        return new JSONObject(data);
    }
}
