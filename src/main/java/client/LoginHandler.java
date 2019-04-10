package client;

public class LoginHandler {
    public static User login(String username, String password) {
        User user = new User(username, password);
        // TODO: check if credentials are already saved on disk.

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

        saveCredentials(username, password);

        return new User(username, password);
    }

    public static void saveCredentials(String usernname, String password) {
        // TODO: write data to disk

        // TODO: send credential data to server
    }

    public static User readCredentials() {
        // TODO: read credentials from disk
        return null;
    }
}
