package Games;

import Server.ClientData;

import java.util.ArrayList;
import java.util.List;

public abstract class GameInstance {


    private final int maxPlayers;

    // GameInstanceController ensures that at least minPlayers amount of players are connected to the game
    private final int minPlayers;
    private final List<ClientData> players;
    private double buyIn; // Pileti sissemüük

    public GameInstance(int maxPlayers, int minPlayers) {
        this.maxPlayers = maxPlayers;
        this.minPlayers = minPlayers;
        this.players = new ArrayList<>();
    }

    public double getBuyIn() {
        return buyIn;
    }

    public void setBuyIn(double buyIn) {
        this.buyIn = buyIn;
    }

    public abstract void runGameLogic();

    public abstract boolean enoughFunds(ClientData clientData);

    public abstract void cleanup();

    public abstract boolean ifFinished();

    public boolean addPlayer(ClientData client) {
        if (players.size() == maxPlayers) {
            return false;
        }
        if (enoughFunds(client)) {
            players.add(client);
            return true;
        }
        return false;
    }

    public List<ClientData> getPlayers() {
        return players;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

}
