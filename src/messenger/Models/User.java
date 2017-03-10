package messenger.Models;

import messenger.Helpers.TokenGenerator;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;

public class User {
    public Integer id;
    public String name;
    public String token;
    public Date registerDate;

    public User(Integer id, String name, Date registerDate) {
        this.id = id;
        this.name = name;
        this.token = TokenGenerator.nextToken();
        this.registerDate = registerDate;
    }

    public JSONObject toJSON() {
        HashMap<String, String> data = new HashMap<>();
        data.put("id", id.toString());
        data.put("name", name);
        return new JSONObject(data);
    }
}
