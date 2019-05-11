package server;

public class ClientData {
    private final String username;
    private final String authToken;
    private int coins;

    public ClientData(String username, String authToken, int coins) {
        this.username = username;
        this.authToken = authToken;
        this.coins = coins;
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
