package server;

import protocol.ClassConverter;
import protocol.MessageBody;
import protocol.Response;
import protocol.requests.StartGameRequest;
import protocol.requests.TestRequest;
import protocol.requests.UserDataRequest;
import server.games.GameInstanceController;
import server.games.GameType;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public class ClientTask implements Runnable {
    private final Socket clientSocket;
    private final Map<GameType, GameInstanceController> gameControllers;

    public ClientTask(Socket clientSocket, Map<GameType, GameInstanceController> gameControllers) {
        this.clientSocket = clientSocket;
        this.gameControllers = gameControllers;
    }


    @Override
    public void run() {
        System.out.println("client connected");

        try (DataInputStream in = new DataInputStream(clientSocket.getInputStream());
             DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
             clientSocket) {
            // TODO: put this in a loop so that multiple commands can be sent without opening a new connection every time
            while (true) {
                String requestString = in.readUTF();
                System.out.println(requestString);
                MessageBody request = ClassConverter.decode(requestString);
                Response response = new Response();


                switch (request.getType()){

                    case LOGIN:{
                        UserDataRequest loginRequest = (UserDataRequest) request;
                        byte[] authToken = ClientActions.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
                        if (authToken == null) {
                            response.setStatusCode(Response.statusCodes.ERR_INVALID_CREDENTIALS);
                        } else {
                            System.out.println("Token sent");
                            response.setAuthToken(authToken);
                        }
                        break;
                    }
                    case CREATE_ACCOUNT: {
                        UserDataRequest createAccountRequest = (UserDataRequest) request;
                        byte[] authToken = ClientActions.createAccount(createAccountRequest.getUsername(), createAccountRequest.getPassword());
                        if(authToken == null){
                            response.setStatusCode(Response.statusCodes.ERR_ACCOUNT_EXISTS);
                        } else {
                            System.out.println("Account succesfully created");
                            response.setAuthToken(authToken);
                        }
                        break;
                    }
                    case TEST:{
                        TestRequest testRequest = (TestRequest) request;
                        response.message = "test string";
                        break;
                    }
                    case START_GAME:{
                        StartGameRequest startGameRequest = (StartGameRequest) request;

                        if (startGameRequest.getAuthToken() == null || !ClientActions.checkAuthentication(startGameRequest.getAuthToken())){
                            response.setStatusCode(Response.statusCodes.ERR_INVALID_CREDENTIALS);
                            break;
                        }

                        ClientData client = ClientActions.getClientByAuthToken(startGameRequest.getAuthToken());

                        switch (startGameRequest.getGameType()){
                            case COINFLIP:
                                System.out.println("client started playing coinflip");
                                gameControllers.get(GameType.COINFLIP).addPlayer(client);
                                break;
                            case WHEEL:
                                break;
                            case LOTTERY:
                                break;
                        }
                        break;
                    }
                }

                out.writeUTF(ClassConverter.encode(response));
                out.flush();
            }
        } catch (IOException e) {
            System.out.println("client closed the connection");
            throw new RuntimeException(e);
        }
    }
}
