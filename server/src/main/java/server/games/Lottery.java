package server.games;


import protocol.Response;
import protocol.requests.GameRequest;
import server.ClientActions;
import server.ClientData;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class Lottery extends GameInstance {
    public static final int gameLength = 30; // in seconds
    private long startTime;
    private long endTime;
    private Map<String, Integer> playerBets = new HashMap<>();
    private int betSum = 0;
    private String winningClient = null;

    public Lottery(ClientActions clientActions){
        super(50000, 1, clientActions); //teoorias võib ka max piletihulgata teha
        startTime = System.currentTimeMillis();
        endTime = startTime + gameLength * 1000;
        playerBets.put("mock", 0);
    }

    @Override
    public void runGameLogic() {
        if (getFinished() || winningClient != null) return;

        if (new Random().nextInt(100) > 97){
            int fakeBet = new Random().nextInt(50);
            betSum += fakeBet;
            playerBets.replace("mock", playerBets.get("mock") + fakeBet);
        }

        if (System.currentTimeMillis() > endTime){
            int bets = 0;
            for (String authToken : playerBets.keySet()) {
                bets += playerBets.get(authToken);
            }

            if (bets == 0){
                setFinished(true);
                return;
            }

            int winningNumber = new Random().nextInt(bets);

            for (String authToken : playerBets.keySet()) {
                int clientBet = playerBets.get(authToken);
                if (clientBet >= winningNumber){
                    playerBets.remove("mock");
                    // Client won
                    System.out.println("Client " + authToken + " won");
                    ClientData client = getClientActions().getClientByAuthToken(authToken);
                    if (client == null){
                        setFinished(true);
                        break;
                    }
                    updateCoins(client, client.getCoins() + bets);
                    winningClient = authToken;
                    break;
                }
                winningNumber -= clientBet;
            }
        }
    }

    @Override
    public void handleRequest(GameRequest request, Response response) {
        ClientData client = getPlayer(request);

        if (client == null || request.getRequestType() == null) return;

        switch (request.getRequestType()){
            case LOTTERY_PROGRESS_QUERY:{
                // data[0] = has the winner been selected yet, data[1] = is the current client the winner, data[2] = player bet sum, data[3] = total bet sum
                int[] data = new int[4];
                if (winningClient != null) {
                    System.out.println("Client " + client.getAuthToken() + " requested game end info");
                    data[0] = 1;
                    if (winningClient.equals(client.getAuthToken())){
                        data[1] = 1;
                    }

                    playerBets.remove(client.getAuthToken());
                }
                data[2] = playerBets.getOrDefault(client.getAuthToken(), 0);
                data[3] = betSum;
                response.data = data;
                response.remainingTime = (endTime - System.currentTimeMillis()) / 1000;
                break;
            }
            case LOTTERY_ADD_BET:{
                int[] payload = request.getPayload();
                if (payload[0] > client.getCoins()){
                    response.setStatusCode(Response.StatusCodes.ERR_NOT_ENOUGH_FUNDS);
                    return;
                }
                updateCoins(client, client.getCoins() - payload[0]);
                playerBets.put(client.getAuthToken(), payload[0] + playerBets.getOrDefault(client.getAuthToken(), 0));
                betSum += payload[0];
                break;
            }
        }
    }

    @Override
    public void cleanup() {


    }

    @Override
    public boolean isFinished() {
        return getFinished() || winningClient != null && playerBets.keySet().size() == 0;
    }
}
