package messenger;

import messenger.models.MessagesContainer;
import messenger.models.UsersContainer;

public class State extends server.State {
    public UsersContainer users;
    public MessagesContainer messages;

    public State() {
        users = new UsersContainer();
        messages = new MessagesContainer();
    }
}
