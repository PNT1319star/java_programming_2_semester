package authentication;

import java.util.UUID;

public class SessionIdCreator {
    public static String createSessionId() {
        return UUID.randomUUID().toString();
    }
}
