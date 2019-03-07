package Client;

public class User {
    private final String username;
    private final String password;
    private String authToken;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }

    private void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public boolean authenticate(){
        // TODO: authenticate with server. Return false if authentication failed

        setAuthToken("placeholder");
        return true;
    }

    public boolean isAuthenticated(){
        // TODO: check with server that the auth token has not expired
        return authToken != null;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                '}';
    }
}
