package server.games;

import server.ClientData;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class GameInstanceController implements Runnable {
    // for each game type one of this object is created. this handles the players and spins up server.games for each of them
    // each game's controller lives in a thread


    private BlockingQueue<ClientData> queuedPlayers;
    private ArrayList<GameInstance> runningGames;
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
                break;
            case LOTTERY:
                break;
        }

        // If reaches here then need to add new game to above switch
        assert false;
        return null;
    }

    public void addPlayer(ClientData player) {
        // TODO: implement an internal timer for game instances to allow more than minimum amount of players to connect. Then should add check for maxPlayers as well
        if (newGame == null) {
            newGame = gameInstanceCreator();
            newGame.addPlayer(player);
        } else {
            if (newGame.getMinPlayers() > newGame.getPlayers().size()) {
                newGame.addPlayer(player);
            } else {
                runningGames.add(newGame);
                newGame = gameInstanceCreator();
                newGame.addPlayer(player);
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            for (GameInstance runningGame : runningGames) {
                if (runningGame.ifFinished()) {
                    runningGame.cleanup();
                    runningGames.remove(runningGame);
                }
            }

            for (GameInstance runningGame : runningGames) {
                runningGame.runGameLogic();
            }
        }

    }
}
