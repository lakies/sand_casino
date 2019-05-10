package server.games;


import protocol.Response;
import protocol.requests.GameRequest;
import server.ClientData;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class Lottery extends GameInstance {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Map<ClientData, Integer> playerBets = new HashMap<>();
    private ClientData winningClient = null;

    public Lottery(){
        super(50000, 1); //teoorias võib ka max piletihulgata teha
        startTime = LocalDateTime.now();
        endTime = LocalDateTime.now().plusMinutes(1);
    }
    @Override
    public boolean enoughFunds(ClientData client) {
        return true;
    }

    @Override
    public void runGameLogic() {
        if (LocalDateTime.now().compareTo(endTime) > 0){
            int betSum = 0;
            for (ClientData clientData : playerBets.keySet()) {
                betSum += playerBets.get(clientData);
            }

            int winningNumber = new Random().nextInt(betSum);

            for (ClientData clientData : playerBets.keySet()) {
                int clientBet = playerBets.get(clientData);
                if (clientBet >= winningNumber){
                    // Client won
                    break;
                }
                winningNumber -= clientBet;
            }
        }
    }

    @Override
    public void handleRequest(GameRequest request, Response response) {
    }

    @Override
    public void cleanup() {


    }

    @Override
    public boolean isFinished() {
        return winningClient != null && playerBets.keySet().size() == 0;
    }
}
