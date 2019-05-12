package server.games;

import protocol.GameType;
import protocol.Response;
import protocol.requests.GameRequest;
import server.ClientActions;
import server.ClientData;
import server.DatabaseHandler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameInstanceController implements Runnable {
    // for each game type one of this object is created. this handles the players and spins up server.games for each of them
    // each game's controller lives in a thread

    private List<GameInstance> runningGames = new ArrayList<>();
    private GameInstance newGame;
    private GameType gameType;
    private DatabaseHandler dbHandler;
    private ClientActions clientActions;

    public GameInstanceController(GameType gameType, ClientActions clientActions) {
        this.gameType = gameType;
        newGame = null;
        this.clientActions = clientActions;
        this.dbHandler = clientActions.getDbHandler();
    }

    private GameInstance gameInstanceCreator() { // TODO: add data for each game
        switch (gameType) {

            case COINFLIP:
                return new CoinFlip(clientActions);
            case WHEEL:
                return new Wheel(clientActions);
            case LOTTERY:
                return new Lottery(clientActions);
        }

        // If reaches here then need to add new game to above switch
        assert false;
        return null;
    }

    public synchronized void passRequest(GameRequest request, Response response){
        for (GameInstance game : runningGames) {
            game.handleRequest(request, response);
        }
    }

    public synchronized void addPlayer(ClientData player) {
        if (newGame == null) {
            newGame = gameInstanceCreator();
            newGame.addPlayer(player);
            runningGames.add(newGame);
        } else {
            if (newGame.getPlayers().size() < newGame.getMaxPlayers() && !newGame.isFinished()) {
                newGame.addPlayer(player);
            } else {
                newGame = gameInstanceCreator();
                newGame.addPlayer(player);
                runningGames.add(newGame);
            }
        }
    }

    @Override
    public void run() {

        while (true) {
            synchronized (this) {
                Iterator<GameInstance> iterator = runningGames.iterator();

                while (iterator.hasNext()) {
                    GameInstance runningGame = iterator.next();
                    if (runningGame.isFinished()) {
                        runningGame.cleanup();
                        iterator.remove();
                    } else {
                        runningGame.runGameLogic();
                    }
                }
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }
    }
}
