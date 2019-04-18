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

    public ServerCommunicator() {
        connect();
    }

    private void connect() {
        try {
            serverSocket = new Socket(serverIP, 9999);
            in = new DataInputStream(serverSocket.getInputStream());
            out = new DataOutputStream(serverSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Could not connect to server: " + e);
        }
    }

    public Response sendRequest (Request request) throws IOException{
        String requestAsString = ClassConverter.encode(request);

        try{
            System.out.println("Sending request " + requestAsString);
            out.writeUTF(requestAsString);
            out.flush();
            String response = in.readUTF();

            return ClassConverter.decode(response);

        } catch (NullPointerException e){
            // Kui tekib out või in on null, siis ei saanud serveriga ühendust. Mõistlik visata IOException vist.
            throw new IOException(e);
        }
    }
}
