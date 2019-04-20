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
    private final ClientActions clientActions;

    public ClientTask(Socket clientSocket, Map<GameType, GameInstanceController> gameControllers, ClientActions clientActions) {
        this.clientSocket = clientSocket;
        this.gameControllers = gameControllers;
        this.clientActions = clientActions;
    }


    @Override
    public void run() {
        System.out.println("client connected");

        try (DataInputStream in = new DataInputStream(clientSocket.getInputStream());
             DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
             clientSocket) {
            while (true) {
                String requestString = in.readUTF();
                System.out.println(requestString);
                Response response = new Response();
                MessageBody request;
                try {
                    request = ClassConverter.decode(requestString);
                } catch (ClassNotFoundException e){
                    response.setStatusCode(Response.StatusCodes.ERR_INVALID_REQUEST);
                    out.writeUTF(ClassConverter.encode(response));
                    out.flush();
                    continue;
                }

                switch (request.getType()){

                    case LOGIN:{
                        UserDataRequest loginRequest = (UserDataRequest) request;
                        String authToken = clientActions.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
                        if (authToken == null) {
                            response.setStatusCode(Response.StatusCodes.ERR_INVALID_CREDENTIALS);
                        } else {
                            System.out.println("Token sent");
                            response.setAuthToken(authToken);
                        }
                        break;
                    }
                    case CREATE_ACCOUNT: {
                        UserDataRequest createAccountRequest = (UserDataRequest) request;
                        String authToken = clientActions.createAccount(createAccountRequest.getUsername(), createAccountRequest.getPassword());
                        if(authToken == null){
                            response.setStatusCode(Response.StatusCodes.ERR_ACCOUNT_EXISTS);
                        } else {
                            System.out.println("Account successfully created");
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
                        ClientData client = clientActions.getClientByAuthToken(startGameRequest.getAuthToken());

                        if (client == null) {
                            response.setStatusCode(Response.StatusCodes.ERR_INVALID_CREDENTIALS);
                            break;
                        }

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
