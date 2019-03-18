package Games;

import Server.ClientData;

import java.util.ArrayList;
import java.util.List;

public abstract class GameInstance {
    private final int maxPlayers;

    // GameInstanceController ensures that at least minPlayers amount of players are connected to the game
    private final int minPlayers;

    private final List<ClientData> players;

    public GameInstance(int maxPlayers, int minPlayers) {
        this.maxPlayers = maxPlayers;
        this.minPlayers = minPlayers;
        this.players = new ArrayList<>();
    }

    public abstract void runGame();

    public boolean addPlayer(ClientData client) {
        if (players.size() == maxPlayers) {
            return false;
        }
        players.add(client);
        return true;
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
