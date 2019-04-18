package server;

import server.games.GameInstanceController;
import server.games.GameTypes;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) {
        Map<GameTypes, GameInstanceController> gameControllers = new HashMap<>();
        ExecutorService gameControllerExecutor = Executors.newFixedThreadPool(GameTypes.values().length);
        for (GameTypes gameType : GameTypes.values()) {
            GameInstanceController gameInstanceController = new GameInstanceController(gameType);
            gameControllerExecutor.submit(gameInstanceController);
            gameControllers.put(gameType, gameInstanceController);
        }

        Thread clientHandler = new Thread(new ClientHandler(gameControllers));
        clientHandler.start();



    }
}
