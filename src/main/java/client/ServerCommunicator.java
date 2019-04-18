package client;

import server.games.GameTypes;

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

    private void reconnect(User user) {
        System.out.println("Connection lost. Reconnecting.");
        serverSocket = null;
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            // do nothing, just continue
        }
        sendToken(user.getAuthToken());
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


    public boolean sendToken(byte[] token) {
        // Connection was lost and reconnection with token is needed
        connect();

        try {
            out.writeInt(3);
            out.write(token);
            in.readInt();
            return in.readBoolean();
        } catch (IOException e) {
            // TODO: Maybe call sendToken recursively until it connects
            throw new RuntimeException(e);
        }
    }

    public void connectToGame(User user, GameTypes gameType) {
        try {

            switch (gameType) {

                case COINFLIP:
                    out.writeInt(10);
                    if (in.readInt() == -2) {
                        // TODO: handle the token expiration somehow
                    }

                    System.out.println("Connected to coinflip");
                    break;
                case WHEEL:
                    break;
                case LOTTERY:
                    break;
            }
        } catch (IOException e) {
            reconnect(user);
            connectToGame(user, gameType);
            // recursively connect again?
        }
    }
}
