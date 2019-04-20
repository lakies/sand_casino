package server;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class ClientActions {

    private Map<AuthToken, ClientData> authTokens = new ConcurrentHashMap<>();
    private DatabaseHandler dbHandler;

    public ClientActions(DatabaseHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    public synchronized byte[] createAccount(String username, String password) {
        // TODO: return null if account with same name already exists
        if (dbHandler.checkUsernameExists(username)) {
            return null;
        }
        dbHandler.addUserToDatabase(username, password);
        return authenticateUser(username, password);
    }

    public byte[] authenticateUser(String username, String password) {
        // TODO: check password, generate new auth token, save it and return it. If auth fails return null
        if (!dbHandler.checkPassword(username, password)) {
            return null;
        }
        byte[] authToken = new byte[20];
        Random r = new Random();
        r.nextBytes(authToken);
        System.out.println("User was successfully authenticated. Token: " + Arrays.toString(authToken));
        AuthToken token = new AuthToken(authToken);
        authTokens.put(token, new ClientData(username));
        return authToken;
    }

    public byte[] readAuthentication(DataInputStream in) throws IOException{
        byte[] authToken = new byte[20];
        in.readNBytes(authToken, 0, 20);
        return authToken;
    }

    public ClientData getClientByAuthToken(byte[] authToken) {
        return authTokens.get(new AuthToken(authToken));
    }
}
