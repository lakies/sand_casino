package server;

public class ClientData {
    private final String username;
    private int coins;

    public ClientData(String username) {
        this.username = username;
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
}
