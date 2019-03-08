package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ServerCommunicator {
    private Socket serverSocket;
    private DataInputStream in;
    private DataOutputStream out;


    private void connect() throws Exception {
        // TODO: connect to server
        serverSocket = null;

        in = new DataInputStream(serverSocket.getInputStream());
        out = new DataOutputStream(serverSocket.getOutputStream());
    }
}
