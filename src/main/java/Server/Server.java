package Server;

import Games.GameInstanceController;
import Games.GameTypes;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) {
        Thread clientHandler = new Thread(new ClientHandler());
        clientHandler.start();

        ExecutorService gameControllers = Executors.newFixedThreadPool(GameTypes.values().length);
        for (GameTypes gameType : GameTypes.values()) {
            gameControllers.submit(new GameInstanceController(gameType));
        }

    }
}
