package server;

import server.games.GameInstanceController;
import protocol.GameType;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        dbHandler.setupDatabase();
        Map<GameType, GameInstanceController> gameControllers = new HashMap<>();
        ExecutorService gameControllerExecutor = Executors.newFixedThreadPool(GameType.values().length);
        ClientActions clientActions = new ClientActions(dbHandler);

        for (GameType gameType : GameType.values()) {
            GameInstanceController gameInstanceController = new GameInstanceController(gameType, clientActions);
            gameControllerExecutor.submit(gameInstanceController);
            gameControllers.put(gameType, gameInstanceController);
        }

        Thread clientHandler = new Thread(new ClientHandler(gameControllers, clientActions));
        clientHandler.start();



    }
}
