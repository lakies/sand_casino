package server;

import server.games.GameInstanceController;
import protocol.GameType;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ClientHandler implements Runnable {
    private final Map<GameType, GameInstanceController> gameControllers;
    private final ClientActions clientActions;

    public ClientHandler(Map<GameType, GameInstanceController> gameControllers, ClientActions clientActions) {
        this.gameControllers = gameControllers;
        this.clientActions = clientActions;
    }

    @Override
    public void run() {
        ExecutorService clientPool = Executors.newCachedThreadPool();

        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            System.out.println("Listening on 9999");

            while (true) {
                Socket s = serverSocket.accept();
                // TODO: somehow handle exceptions with the future
                Future<?> future = clientPool.submit(new ClientTask(s, gameControllers, clientActions));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
