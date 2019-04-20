package server;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class ClientActions {

    private Map<String, ClientData> authTokens = new ConcurrentHashMap<>();
    private DatabaseHandler dbHandler;

    public ClientActions(DatabaseHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    public synchronized String createAccount(String username, String password) {
        // TODO: return null if account with same name already exists
        if (dbHandler.checkUsernameExists(username)) {
            return null;
        }
        dbHandler.addUserToDatabase(username, password);
        return authenticateUser(username, password);
    }

    public String authenticateUser(String username, String password) {
        // TODO: check password, generate new auth token, save it and return it. If auth fails return null
        if (!dbHandler.checkPassword(username, password)) {
            return null;
        }
        byte[] authTokenBytes = new byte[20];
        Random r = new Random();
        r.nextBytes(authTokenBytes);
        String authToken = new String(authTokenBytes);

        System.out.println("User was successfully authenticated. Token: " + authToken);
        authTokens.put(authToken, new ClientData(username));
        return authToken;
    }


    public ClientData getClientByAuthToken(String authToken) {
        return authTokens.get(authToken);
    }
}
