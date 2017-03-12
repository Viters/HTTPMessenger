package messenger.models;

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
}
