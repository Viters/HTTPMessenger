package messenger;

import messenger.models.MessagesContainer;
import messenger.models.UsersContainer;
import server.State;

public class State extends server.State {
    public UsersContainer users;
    public MessagesContainer messages;

    public State() {
        users = new UsersContainer();
        messages = new MessagesContainer();
    }
}
