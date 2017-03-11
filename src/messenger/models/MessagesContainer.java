package messenger.models;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.Stack;

public class MessagesContainer {
    private Stack<Message> messages;

    public MessagesContainer() {
        messages = new Stack<>();
    }

    public Message saveMessage(String messageText, User sender, User receiver) {
        Message message = new Message(this.getNextValidMessageId(), messageText, sender, receiver);
        messages.push(message);

        return message;
    }

    private int getNextValidMessageId() {
        if (messages.isEmpty()) {
            return 0;
        }
        else {
            return messages.peek().id + 1;
        }
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
