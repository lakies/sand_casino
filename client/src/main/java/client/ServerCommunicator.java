package client;

import protocol.ClassConverter;
import protocol.Request;
import protocol.Response;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerCommunicator {
    private static String serverIP = "localhost";
    private Socket serverSocket;
    private DataInputStream in;
    private DataOutputStream out;
    private final User user;

    public ServerCommunicator(User user) throws IOException{
        this.user = user;
        connect();
    }

    private void connect() throws IOException{
        try {
            serverSocket = new Socket(serverIP, 9999);
            in = new DataInputStream(serverSocket.getInputStream());
            out = new DataOutputStream(serverSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Could not connect to server: " + e);
            throw e;
        }
    }

    public synchronized Response sendRequest (Request request) throws IOException{
        request.setAuthToken(user.getAuthToken());
        String requestAsString = ClassConverter.encode(request);

        if (out == null || in == null){
            throw new IOException("connection with server not established");
        }

        out.writeUTF(requestAsString);
        out.flush();

        try {
            return ClassConverter.decode(in.readUTF(), Response.class);
        } catch (ClassNotFoundException e){
            throw new RuntimeException("Server sent illegal response");
        }

    }
}
