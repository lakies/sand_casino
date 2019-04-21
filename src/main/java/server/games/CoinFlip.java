package server.games;

import protocol.Request;
import protocol.Response;
import protocol.requests.GameRequest;
import server.ClientData;



public class CoinFlip extends GameInstance {
    private boolean gameStarted = false;
    private boolean finished = false;

    public enum Sides {
        HEADS(0), TAILS(1);

        Sides(int value) {
            this.value = value;
        }

        private int value;

        public int getValue() {
            return value;
        }
    }

    private int chosenSide;

    @Override
    public boolean enoughFunds(ClientData client) {
        if (client.getCoins() >= 50) {
            return true;
        }
        System.out.println("Insufficient funds");
        return false;
    }

    @Override
    public void runGameLogic() {

    }

    public CoinFlip() {
        super(1, 1);
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public void cleanup() {
        System.out.println("Cleanup called");
    }

    @Override
    public void handleRequest(Request request, Response response) {
        // All game logic happens when request comes in

        GameRequest gameRequest = (GameRequest) request;
        ClientData client = getPlayers().get(0);
        if (!client.getAuthToken().equals(gameRequest.getAuthToken())){
            return;
        }

        if (!enoughFunds(client)){
            response.setStatusCode(Response.StatusCodes.ERR_NOT_ENOUGH_FUNDS);
            finished = true;
            return;
        }

        client.setCoins(client.getCoins() - 50);

        double i = Math.random();

        Sides side = i < 0.49 ? Sides.HEADS : Sides.TAILS;
        chosenSide = gameRequest.getPayload()[0];

        if (side.getValue() == chosenSide) {
            client.setCoins(client.getCoins() + 100);
        }

        response.data = new int[]{side.getValue(), client.getCoins()};
        finished = true;
    }



}

