package Client;

import Games.GameTypes;

import java.util.Arrays;

public class User {
    private final String username;
    private final String password;
    private final ServerCommunicator serverCommunicator;
    private byte[] authToken;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.serverCommunicator = new ServerCommunicator(this);
    }

    public String getUsername() {
        return username;
    }

    private void setAuthToken(byte[] authToken) {
        this.authToken = authToken;
    }

    public byte[] getAuthToken() {
        return authToken;
    }

    public boolean authenticate(){
        // TODO: authenticate with server. Return false if authentication failed
        byte[] token = serverCommunicator.sendAuthentication(username, password);
        if (token == null) {
            return false;
        } else {
            setAuthToken(token);
            return true;
        }
    }

    public boolean isAuthenticated(){
        // TODO: check with server that the auth token has not expired
        System.out.println(Arrays.toString(authToken));
        return authToken != null;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                '}';
    }

    public void playGame(GameTypes gameType) {
        serverCommunicator.connectToGame(this, gameType);
    }
}
