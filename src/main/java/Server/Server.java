package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) {
        ExecutorService clientPool = Executors.newCachedThreadPool();

        // TODO: make the client handling into a separate thread so that other things can run on the server
        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            System.out.println("Listening on 9999");

            while (true) {
                Socket s = serverSocket.accept();
                clientPool.submit(new main.java.Server.ClientTask(s));
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
