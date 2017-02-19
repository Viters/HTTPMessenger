package messenger;

import java.math.BigInteger;
import java.security.SecureRandom;

public final class TokenGenerator {
    private SecureRandom random = new SecureRandom();

    public String nextToken() {
        return new BigInteger(130, random).toString(32);
    }
}
