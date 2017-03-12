package messenger.models;

import com.google.common.collect.ImmutableMap;
import org.json.JSONObject;
import server.interfaces.JSONSerializable;

import java.util.Date;
import java.util.Map;

public class Message implements JSONSerializable {
    public Integer id;
    public String content;
    public User sender;
    public User receiver;
    public Date createdAt;

    public Message(Integer id, String content, User sender, User receiver) {
        this.id = id;
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
        this.createdAt = new Date();
    }

    public JSONObject toJSON() {
        Map<String, String> data = ImmutableMap.of(
                "id", id.toString(),
                "content", content,
                "sender", sender.id.toString(),
                "receiver", receiver.id.toString(),
                "date", createdAt.toString()
        );
        return new JSONObject(data);
    }
}
