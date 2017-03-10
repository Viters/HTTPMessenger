package messenger;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MessengerStateTest {
    @Test
    public void usersHaveIncrementedIDs() {
        MessengerState state = new MessengerState();

        state.registerUser("Tomasz");
        state.registerUser("Andrzej");

        assertThat(state.users.get(1).id).isGreaterThan(state.users.get(0).id);
    }
}