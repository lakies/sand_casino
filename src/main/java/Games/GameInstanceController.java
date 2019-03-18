package Games;

import Server.ClientData;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class GameInstanceController implements Runnable {
    // for each game type one of this object is created. this handles the players and spins up games for each of them
    // each game's controller lives in a thread


    private BlockingQueue<ClientData> queuedPlayers;
    private ArrayList<GameInstance> runningGames;
    private GameInstance newGame;
    private GameTypes gameType;

    public GameInstanceController(GameTypes gameType) {
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

    private void addPlayer(ClientData player) {
        if (newGame == null) {
            newGame = gameInstanceCreator();
            newGame.addPlayer(player);
        } else {
            if (newGame.getMinPlayers() > newGame.getPlayers().size()) {
                newGame.addPlayer(player);
            } else {
                newGame = gameInstanceCreator();
                newGame.addPlayer(player);
            }
        }
    }

    @Override
    public void run() {
        // TODO: take players from queuedPlayers and assign them to games, then run game main logic
        System.out.println("hi");
    }
}
