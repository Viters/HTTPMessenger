package messenger.models;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MessagesContainerTest {
    @Test
    public void savingIncrementsIds() {
        MessagesContainer messages = new MessagesContainer();

        User user1 = new User(0, "Andrzej");
        User user2 = new User(1, "Andrzej");

        Message message1 = messages.saveMessage("Hello", user1, user2);
        Message message2 = messages.saveMessage("Im busy r8 now", user2, user1);

        assertThat(message2.id).isGreaterThan(message1.id);
    }
}