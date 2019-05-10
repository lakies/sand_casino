package server;

import protocol.ClassConverter;
import protocol.Request;
import protocol.Response;
import protocol.requests.GameRequest;
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
                try {
                    String requestString = in.readUTF();
                    System.out.println(requestString);
                    Response response = new Response();
                    Request request = ClassConverter.decode(requestString, Request.class);

                    switch (request.getType()){
                        case LOGIN: {
                            UserDataRequest loginRequest = ClassConverter.decode(requestString, UserDataRequest.class);
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
                            UserDataRequest createAccountRequest = ClassConverter.decode(requestString, UserDataRequest.class);

                            try {
                                String authToken = clientActions.createAccount(createAccountRequest.getUsername(), createAccountRequest.getPassword());
                                if (authToken == null) {
                                    response.setStatusCode(Response.StatusCodes.ERR_ACCOUNT_EXISTS);
                                } else {
                                    System.out.println("Account successfully created");
                                    response.setAuthToken(authToken);
                                }
                            } catch (DatabaseException e) {
                                response.setStatusCode(Response.StatusCodes.ERR_FAILED_USER_CREATION);
                            }
                            break;
                        }
                        case TEST:{
                            TestRequest testRequest = (TestRequest) request;
                            response.message = "test string";
                            break;
                        }
                        case COIN_AMOUNT:{
                            UserDataRequest coinRequest = ClassConverter.decode(requestString, UserDataRequest.class);
                            ClientData client = clientActions.getClientByAuthToken(coinRequest.getAuthToken());

                            if (client == null) {
                                response.setStatusCode(Response.StatusCodes.ERR_INVALID_CREDENTIALS);
                                break;
                            }

                            response.data = new int[]{client.getCoins()};

                            break;
                        }
                        case START_GAME:{
                            StartGameRequest startGameRequest = ClassConverter.decode(requestString, StartGameRequest.class);
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
                                    System.out.println("client started playing wheel of fortune");
                                    gameControllers.get(GameType.WHEEL).addPlayer(client);
                                    break;
                                case LOTTERY:
                                    break;
                            }
                            break;
                        }

                        case GAME_DATA: {
                            GameRequest gameRequest = ClassConverter.decode(requestString, GameRequest.class);
                            for (GameInstanceController gameInstanceController : gameControllers.values()) {
                                gameInstanceController.passRequest(gameRequest, response);
                            }
                        }
                    }

                    out.writeUTF(ClassConverter.encode(response));
                    out.flush();
                } catch (ClassNotFoundException e){
                    Response response = new Response();
                    response.setStatusCode(Response.StatusCodes.ERR_INVALID_REQUEST);
                    out.writeUTF(ClassConverter.encode(response));
                    out.flush();
                }

            }
        } catch (IOException e) {
            System.out.println("client closed the connection");
            throw new RuntimeException(e);
        } catch (RuntimeException e){
            e.printStackTrace();
            throw e;
        }
    }
}
