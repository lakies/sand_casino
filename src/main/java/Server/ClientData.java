package Server;

public class ClientData {
    private final String username;
    private final byte[] authToken;

    public ClientData(String username, byte[] authToken) {
        this.username = username;
        this.authToken = authToken;
    }

    public String getUsername() {
        return username;
    }

    public byte[] getAuthToken() {
        return authToken;
    }
}
