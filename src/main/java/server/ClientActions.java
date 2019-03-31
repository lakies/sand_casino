package server;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class ClientActions {
    public static boolean checkUsernameExists(String username) {
        // TODO: read all users and check if name exists already
        return false;
    }

    public static byte[] registerUser(String username, String password) {
        // TODO: write user data to file
        checkUsernameExists(username);
        return authenticateUser(username, password);
    }

    public static byte[] authenticateUser(String username, String password) {
        // TODO: check password, generate new auth token, save it and return it. If auth fails return null

        byte[] authToken = new byte[20];
        Random r = new Random();
        r.nextBytes(authToken);
        System.out.println("User was successfully authenticated. Token: " + Arrays.toString(authToken));
        return authToken;
    }

    public static boolean checkAuthentication(byte[] token) {


        // TODO: read the auth tokens file and check if the one that the client sends exists and has not expired
        return true;
    }

    public static byte[] readAuthentication(DataInputStream in) throws IOException{
        byte[] authToken = new byte[20];
        in.readNBytes(authToken, 0, 20);
        return authToken;
    }

    public static ClientData getClientByAuthToken(byte[] authToken) {
        // TODO: read auth tokens file and create a new clientData instance
        return new ClientData("", authToken);
    }
}
