package messenger;

import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;

public class User {
    private Integer id;
    private String name;
    private String token;
    private Date registerDate;

    public User(Integer id, String name, Date registerDate) {
        this.id = id;
        this.name = name;
        this.token = TokenGenerator.nextToken();
        this.registerDate = registerDate;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getToken() {
        return token;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public JSONObject toJSON() {
        HashMap<String, String> data = new HashMap<>();
        data.put("id", id.toString());
        data.put("name", name);
        return new JSONObject(data);
    }
}
