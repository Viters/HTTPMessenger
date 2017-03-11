package messenger;

import messenger.models.User;
import messenger.models.UsersContainer;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UsersContainerTest {
    @Test
    public void registrationIncrementsIds() {
        UsersContainer users = new UsersContainer();

        User user1 = users.register("Tomasz");
        User user2 = users.register("Andrzej");

        assertThat(user2.id).isGreaterThan(user1.id);
    }
}