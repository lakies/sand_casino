package client;

import protocol.MessageType;
import protocol.Response;
import protocol.requests.UserDataRequest;

import java.io.IOException;

public class User {
    private final String username;
    private final String password;
    private final ServerCommunicator serverCommunicator;
    private String authToken;

    public User(String username, String password) throws IOException{
        this.username = username;
        this.password = password;
        this.serverCommunicator = new ServerCommunicator(this);
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getAuthToken() {
        return authToken;
    }

    public ServerCommunicator getServerCommunicator() {
        return serverCommunicator;
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

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                '}';
    }
}
