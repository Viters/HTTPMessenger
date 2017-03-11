package messenger.models;

import org.json.JSONObject;

import java.util.ArrayList;

public class MessagesContainer {
    private ArrayList<Message> messages;

    public MessagesContainer() {
        messages = new ArrayList<>();
    }

    public ArrayList<JSONObject> getSerializedMessagesForUser(String userToken, int lastId) {
        ArrayList<JSONObject> messages = new ArrayList<>();
        this.messages.forEach(m -> {
            if (m.toUser.token.equals(userToken) && m.id > lastId) {
                messages.add(m.toJSON());
            }
        });
        return messages;
    }
}
