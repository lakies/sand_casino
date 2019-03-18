package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerCommunicator {
    public static String serverIP = "localhost";
    private Socket serverSocket;
    private DataInputStream in;
    private DataOutputStream out;

    public ServerCommunicator(User user) {
        connect();
    }

    private void connect() {
        while (serverSocket == null || serverSocket.isClosed()){
            try {
                serverSocket = new Socket(serverIP, 9999);
                in = new DataInputStream(serverSocket.getInputStream());
                out = new DataOutputStream(serverSocket.getOutputStream());
            } catch (IOException e) {
                System.out.println("Could not connect to server: " + e);
            }
        }
    }

    public byte[] sendAuthentication(String username, String password) {
        try {
            out.writeInt(2);
            out.writeUTF(username);
            out.writeUTF(password);

            int accepted = in.readInt(); // Always 1

            boolean result = in.readBoolean();
            if (!result) {
                return null;
            } else {
                byte[] token = new byte[20];
                in.readNBytes(token, 0, 20);
                return token;
            }
        } catch (IOException e) {
            return null;
        }
    }

    public void connectToGame(){

    }
}
