package messenger.models;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MessagesContainer {
    private HashMap<Integer, Message> messages;
    private int nextAvailableId;

    public MessagesContainer() {
        messages = new HashMap<>();
        nextAvailableId = 0;
    }

    public Message saveMessage(String messageText, User sender, User receiver) {
        Message message = new Message(nextAvailableId, messageText, sender, receiver);
        messages.put(nextAvailableId, message);

        nextAvailableId++;
        return message;
    }

    public ArrayList<JSONObject> getSerializedMessagesForUser(String userToken, int lastId) {
        ArrayList<JSONObject> messages = new ArrayList<>();
        this.messages.values().forEach(m -> {
            if (m.receiver.token.equals(userToken) && m.id > lastId) {
                messages.add(m.toJSON());
            }
        });
        return messages;
    }
}
