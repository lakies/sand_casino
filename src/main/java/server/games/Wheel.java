package server.games;

import protocol.Response;
import protocol.requests.GameRequest;
import server.ClientData;

import java.util.Random;

public class Wheel extends GameInstance {
    private int userCoins;
    private double[] winRates = {0, 0, 0.5, 0.75, 1, 1, 1.25, 1.5, 1.5, 2};

    public Wheel(int buyIn) {
        super(1, 1);
    }

    public Wheel() {
        super(1, 1);
    }

    @Override
    public boolean enoughFunds(ClientData client) {
        return client.getCoins() >= userCoins;
    }

    public void setUserCoins(int userCoins) {
        this.userCoins = userCoins;
    }

    @Override
    public void handleRequest(GameRequest request, Response response) {
        int won = 0;
        // All game logic happens when request comes in

        ClientData client = getPlayers().get(0);
        if (!client.getAuthToken().equals(request.getAuthToken())) {
            return;
        }
        this.setUserCoins(request.getPayload()[0]);


        if (!enoughFunds(client)) {
            response.setStatusCode(Response.StatusCodes.ERR_NOT_ENOUGH_FUNDS);
            setFinished(true);
            return;
        }


        client.setCoins(client.getCoins() - userCoins);

        Random generator = new Random();
        int randomIndex = generator.nextInt(winRates.length);
        int winnedCoins = (int) (client.getCoins() + (userCoins * winRates[randomIndex]));
        client.setCoins(winnedCoins);


        response.data = new int[]{winnedCoins};
        setFinished(true);

    }

    @Override
    public void runGameLogic() {


    }

    public void Game(ClientData clientData) {

    }

    @Override
    public void cleanup() {

    }

    @Override
    public boolean isFinished() {
        return getFinished();
    }
}
