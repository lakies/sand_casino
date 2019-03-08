package Server;

import Client.User;

public class ClientActions {
    public static boolean checkUsernameExists(String username) {
        // TODO: read all users and check if name exists already
        return false;
    }

    public static boolean registerUser(User user) {
        return !checkUsernameExists(user.getUsername());
        // TODO: write user data to file
    }

    public static String authenticateUser(User user) {
        // TODO: check password, generate new auth token, save it and return it. If auth fails return null
        return "placeholder";
    }
}
