package client;

import java.io.IOException;

public class LoginHandler {
    public static User login(String username, String password) throws IOException {
        User user = new User(username, password);

        if (user.authenticate()) {
            // Return the authenticated user
            return user;
        } else {
            return null;
        }
    }

    public static User createAccount(String username, String password) {
        // TODO: check with server that an account with the same name has not yet been created

        // TODO: maybe test password security

        return new User(username, password);
    }
}
