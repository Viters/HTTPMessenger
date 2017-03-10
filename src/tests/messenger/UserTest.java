package messenger;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {
    @Test
    public void userTokensAreDifferent() {
        User user1 = new User(1, "Tomek", new Date());
        User user2 = new User(2, "Andrzej", new Date());

        assertThat(user1.getToken()).isNotEqualTo(user2.getToken());
    }
}