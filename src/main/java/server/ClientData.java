package server;

public class ClientData {
    private final String username;
    private final byte[] authToken;
    private double coins;

    public ClientData(String username, byte[] authToken) {
        this.username = username;
        this.authToken = authToken;
    }

    public double getCoins() {
        return coins;
    }

    public void setCoins(double coins) {
        this.coins = coins;
    }

    public String getUsername() {
        return username;
    }

    public byte[] getAuthToken() {
        return authToken;
    }
}
