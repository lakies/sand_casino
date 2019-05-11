package server.games;

import protocol.Request;
import protocol.Response;
import protocol.requests.GameRequest;
import server.ClientActions;
import server.ClientData;
import server.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

public abstract class GameInstance {

    private boolean gameStarted = false;
    private boolean finished = false;
    private DatabaseHandler dbHandler;
    private ClientActions clientActions;

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean getFinished(){
        return finished;
    }

    private final int maxPlayers;

    // GameInstanceController ensures that at least minPlayers amount of players are connected to the game
    private final int minPlayers;
    private final List<ClientData> players;
    private double buyIn; // Pileti sissemüük

    public GameInstance(int maxPlayers, int minPlayers, ClientActions clientActions) {
        this.maxPlayers = maxPlayers;
        this.minPlayers = minPlayers;
        this.players = new ArrayList<>();
        this.dbHandler = clientActions.getDbHandler();
        this.clientActions = clientActions;
    }

    public ClientActions getClientActions() {
        return clientActions;
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

    public abstract boolean isFinished();

    public abstract void handleRequest(GameRequest request, Response response);

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

    protected void updateCoins(ClientData clientData, int coins) {
        clientData.setCoins(coins);
        dbHandler.saveCoins(clientData.getUsername(), coins);
    }

    public ClientData getPlayer(Request request){
        for (ClientData player : players) {
            if (request.getAuthToken().equals(player.getAuthToken())){
                return player;
            }
        }

        return null;
    }

}
