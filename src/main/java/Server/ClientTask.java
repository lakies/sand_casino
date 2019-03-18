package main.java.Server;

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
        System.out.println("main.java.Client connected");

        try (DataInputStream in = new DataInputStream(clientSocket.getInputStream());
             DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream())) {
            // TODO: put this in a loop so that multiple commands can be sent without opening a new conenction every time
            // Maybe types 0-10 don't require authentication, but all above that do

            /*
            main.java.Server response protocol:
            0 - int, 1 if the command was successfully fulfilled, -1 if the command was of unknown type and -2 if the authentication was wrong
            1 - Any data returned by the command
             */
            int requestType = in.readInt();

            if (requestType > 10) {
                if (!ClientActions.checkAuthentication(in)) {
                    out.writeInt(-2);
                }
            }

            switch (requestType) {
                case 0: {
                    // Check if username exists
                    String username = in.readUTF();
                    out.writeInt(1);
                    out.writeBoolean(ClientActions.checkUsernameExists(username));
                    break;
                }
                case 1: {
                    // Register new user
                    String username = in.readUTF();
                    String password = in.readUTF();
                    ClientData newUser = new ClientData(username, password);
                    boolean registerResult = ClientActions.registerUser(newUser);
                    out.writeInt(1);
                    out.writeBoolean(registerResult);
                    break;
                }
                case 2: {
                    // Authenticate
                    String username = in.readUTF();
                    String password = in.readUTF();
                    ClientData user = new ClientData(username, password);
                    byte[] authToken = ClientActions.authenticateUser(user);

                    out.writeInt(1);

                    if (authToken == null) {
                        out.writeBoolean(false);
                    } else {
                        out.writeBoolean(true);
                        out.write(authToken);
                        System.out.println("Token sent");
                    }

                    break;
                }

                default: {
                    out.writeInt(-1);
                }
            }
        } catch (IOException e) {
            System.out.println("Streams could not be opened: " + e);
        }
    }
}
