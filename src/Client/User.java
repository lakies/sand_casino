package Client;

import java.util.Arrays;

public class User {
    private final String username;
    private final String password;
    private final ServerCommunicator comms;
    private byte[] authToken;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.comms = new ServerCommunicator();
    }

    //
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }


    public String getUsername() {
        return username;
    }

    private void setAuthToken(byte[] authToken) {
        this.authToken = authToken;
    }

    public boolean authenticate(){
        // TODO: authenticate with server. Return false if authentication failed
        byte[] token = comms.sendAuthentication(username, password);
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
}
