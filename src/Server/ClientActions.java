package Server;

import Client.User;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class ClientActions {
    public static boolean checkUsernameExists(String username) {
        // TODO: read all users and check if name exists already
        return false;
    }

    public static boolean registerUser(ClientData client) {
        // TODO: write user data to file
        return !checkUsernameExists(client.getUsername());
    }

    public static byte[] authenticateUser(ClientData client) {
        // TODO: check password, generate new auth token, save it and return it. If auth fails return null

        byte[] authToken = new byte[20];
        Random r = new Random();
        r.nextBytes(authToken);
        System.out.println("User was successfully authenticated. Token: " + Arrays.toString(authToken));
        return authToken;
    }

    public static boolean checkAuthentication(DataInputStream in) throws IOException {
        byte[] authToken = new byte[20];
        in.readNBytes(authToken, 0, 20);

        // TODO: read the auth tokens file and check if the one that the client sends exists and has not expired
        return true;
    }
}
