package server.games;

import protocol.Response;
import protocol.requests.GameRequest;
import server.ClientData;
import server.DatabaseHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class GameInstanceController implements Runnable {
    // for each game type one of this object is created. this handles the players and spins up server.games for each of them
    // each game's controller lives in a thread


    private BlockingQueue<ClientData> queuedPlayers;
    private List<GameInstance> runningGames = Collections.synchronizedList(new ArrayList<>());
    private GameInstance newGame;
    private GameType gameType;
    private DatabaseHandler dbHandler;

    public GameInstanceController(GameType gameType, DatabaseHandler dbHandler) {
        this.queuedPlayers = new ArrayBlockingQueue<>(10); // is 10 enough?
        this.gameType = gameType;
        newGame = null;
        this.dbHandler = dbHandler;
    }

    private GameInstance gameInstanceCreator() { // TODO: add data for each game
        switch (gameType) {

            case COINFLIP:
                return new CoinFlip(dbHandler);
            case WHEEL:
                return new Wheel(dbHandler);
            case LOTTERY:
                return new Lottery(dbHandler);
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
            if (newGame.getPlayers().size() < newGame.getMaxPlayers()) {
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
            Iterator<GameInstance> iterator = runningGames.iterator();

            while (iterator.hasNext()) {
                GameInstance runningGame = iterator.next();
                if (runningGame.isFinished()) {
                    runningGame.cleanup();
                    iterator.remove();
                }
            }

            for (GameInstance runningGame : runningGames) {
                runningGame.runGameLogic();
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
