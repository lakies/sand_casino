package server;

public class ClientData {
    private final String username;
    private final String authToken;
    private int coins = 400;

    public ClientData(String username, String authToken) {
        this.username = username;
        this.authToken = authToken;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public String getUsername() {
        return username;
    }

    public String getAuthToken() {
        return authToken;
    }
}
