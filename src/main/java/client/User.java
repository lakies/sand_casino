package client;

import protocol.MessageType;
import protocol.Response;
import protocol.requests.UserDataRequest;

import java.io.IOException;
import java.util.Arrays;

public class User {
    private final String username;
    private final String password;
    private final ServerCommunicator serverCommunicator;
    private byte[] authToken;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.serverCommunicator = new ServerCommunicator();
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

    public boolean authenticate() throws IOException {
        Response response = serverCommunicator.sendRequest(new UserDataRequest(MessageType.LOGIN, username, password));

        if (response.getAuthToken() == null) {
            return false;
        } else {
            setAuthToken(response.getAuthToken());
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

//    public void playGame(GameType gameType) {
//        serverCommunicator.connectToGame(this, gameType);
//    }
}
