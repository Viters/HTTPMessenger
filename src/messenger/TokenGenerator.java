package messenger;

import java.math.BigInteger;
import java.security.SecureRandom;

final class TokenGenerator {
    private static SecureRandom random = new SecureRandom();

    static String nextToken() {
        return new BigInteger(130, random).toString(32);
    }
}
