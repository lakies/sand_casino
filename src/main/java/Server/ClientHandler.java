package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientHandler implements Runnable {

    @Override
    public void run() {
        ExecutorService clientPool = Executors.newCachedThreadPool();

        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            System.out.println("Listening on 9999");

            while (true) {
                Socket s = serverSocket.accept();
                clientPool.submit(new ClientTask(s));
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
