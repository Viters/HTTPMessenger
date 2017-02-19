package messenger;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by Viters on 15.01.2017.
 */
public final class TokenGenerator {
    private SecureRandom random = new SecureRandom();

    public String nextToken() {
        return new BigInteger(130, random).toString(32);
    }
}
