package messenger;

import messenger.models.UsersContainer;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UsersContainerTest {
    @Test
    public void usersHaveIncrementedIDs() {
        UsersContainer users = new UsersContainer();

        users.registerUser("Tomasz");
        users.registerUser("Andrzej");

        assertThat(users.get(1).id).isGreaterThan(users.get(0).id);
    }
}