package server;

import server.games.GameInstanceController;
import server.games.GameType;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ClientHandler implements Runnable {
    private Map<GameType, GameInstanceController> gameControllers;

    public ClientHandler(Map<GameType, GameInstanceController> gameControllers) {
        this.gameControllers = gameControllers;
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
                Future<?> future = clientPool.submit(new ClientTask(s, gameControllers));
            }
        } catch (IOException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }
}
