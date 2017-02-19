package messenger;

import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;

public class Message {
    private Integer id;
    private String message;
    private User from;
    private User to;
    private Date createdAt;

    public Message(Integer id, String message, User from, User to) {
        this.id = id;
        this.message = message;
        this.from = from;
        this.to = to;
        this.createdAt = new Date();
    }

    public Integer getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public User getFrom() {
        return from;
    }

    public User getTo() {
        return to;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public JSONObject toJSON() {
        HashMap<String, String> data = new HashMap<>();
        data.put("id", id.toString());
        data.put("message", message);
        data.put("from", from.getId().toString());
        data.put("to", to.getId().toString());
        data.put("date", createdAt.toString());
        return new JSONObject(data);
    }
}
