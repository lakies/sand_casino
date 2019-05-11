package server;

import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClientActions {

    private Map<String, ClientData> authTokens = new ConcurrentHashMap<>();
    private DatabaseHandler dbHandler;

    public ClientActions(DatabaseHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    public DatabaseHandler getDbHandler() {
        return dbHandler;
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
        String authToken = "";
        while (true) {
            byte[] authTokenBytes = new byte[20];
            SecureRandom r = new SecureRandom();
            r.nextBytes(authTokenBytes);
            authToken = new String(authTokenBytes);
            if (getClientByAuthToken(authToken) == null){
                break;
            }
        }

        System.out.println("User was successfully authenticated. Token: " + authToken);
        authTokens.put(authToken, new ClientData(username, authToken, dbHandler.getCoins(username)));
        return authToken;
    }


    public ClientData getClientByAuthToken(String authToken) {
        if (!authTokens.containsKey(authToken)){
            return null;
        }
        return authTokens.get(authToken);
    }
}
