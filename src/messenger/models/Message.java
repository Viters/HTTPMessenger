package messenger.models;

import com.google.common.collect.ImmutableMap;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
        Map<String, String> data = ImmutableMap.of(
                "id", id.toString(),
                "content", content,
                "fromUser", fromUser.id.toString(),
                "toUser", toUser.id.toString(),
                "date", createdAt.toString()
        );
        return new JSONObject(data);
    }
}
