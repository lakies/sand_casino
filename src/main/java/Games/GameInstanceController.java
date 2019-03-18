package Games;

import Server.ClientData;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class GameInstanceController implements Runnable {
    // for each game type one of this object is created. this handles the players and spins up games for each of them
    // each game's controller lives in a thread


    private BlockingQueue<ClientData> queuedPlayers;
    private Games gameType;

    public GameInstanceController(Games gameType) {
        this.queuedPlayers = new ArrayBlockingQueue<>(10); // is 10 enough?
        this.gameType = gameType;
    }

    private GameInstance gameInstanceCreator(Games type) { // TODO: add data for each game
        switch (type) {

            case COINFLIP:
                return new CoinFlip();
            break;
            case WHEEL:
                return null;
            break;
            case LOTTERY:
                return null;
            break;
        }

        // Shouldn't ever reach here
        return null;
    }

    @Override
    public void run() {
        // TODO: take players from queuedPlayers and assign them to games, then run game main logic
    }
}
