package Server;

import Client.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientTask implements Runnable {
    private final Socket clientSocket;

    public ClientTask(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        System.out.println("Client connected");

        try (DataInputStream in = new DataInputStream(clientSocket.getInputStream());
             DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream())) {
            int requestType = in.readInt();

            switch (requestType) {
                case 0: {
                    // Check if username exists
                    String username = in.readUTF();
                    out.writeBoolean(ClientActions.checkUsernameExists(username));
                    break;
                }
                case 1: {
                    // Register new user
                    String username = in.readUTF();
                    String password = in.readUTF();
                    User newUser = new User(username, password);
                    boolean registerResult = ClientActions.registerUser(newUser);
                    out.writeBoolean(registerResult);
                    break;
                }
                case 2: {
                    // Authenticate
                    String username = in.readUTF();
                    String password = in.readUTF();
                    User user = new User(username, password);
                    String authToken = ClientActions.authenticateUser(user);

                    if (authToken == null) {
                        out.writeBoolean(false);
                    } else {
                        out.writeUTF(authToken);
                    }

                    break;
                }

            }
        } catch (IOException e) {
            System.out.println("Streams could not be opened: " + e);
        }
    }
}
