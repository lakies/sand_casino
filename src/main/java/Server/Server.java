package Server;

public class Server {
    public static void main(String[] args) {
        Thread clientHandler = new Thread(new ClientHandler());
        clientHandler.start();


    }
}
