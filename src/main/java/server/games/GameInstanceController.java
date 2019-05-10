package server.games;

import protocol.Response;
import protocol.requests.GameRequest;
import server.ClientData;

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

    public GameInstanceController(GameType gameType) {
        this.queuedPlayers = new ArrayBlockingQueue<>(10); // is 10 enough?
        this.gameType = gameType;
        newGame = null;
    }

    private GameInstance gameInstanceCreator() { // TODO: add data for each game
        switch (gameType) {

            case COINFLIP:
                return new CoinFlip();
            case WHEEL:
                return new Wheel();
            case LOTTERY:
                return new Lottery();
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
        // TODO: implement an internal timer for game instances to allow more than minimum amount of players to connect. Then should add check for maxPlayers as well
        if (newGame == null) {
            newGame = gameInstanceCreator();
            newGame.addPlayer(player);
            if (newGame.getMinPlayers() == 1) {
                runningGames.add(newGame);
            }
        } else {
            if (newGame.getMinPlayers() > newGame.getPlayers().size()) {
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
