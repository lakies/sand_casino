package Games;

import Server.ClientData;

import java.util.ArrayList;
import java.util.List;

public abstract class GameInstance {
    private int maxPlayers;

    private List<ClientData> players;

    public GameInstance(int maxPlayers) {
        this.maxPlayers = maxPlayers;
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
}
