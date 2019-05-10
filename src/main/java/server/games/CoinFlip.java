package server.games;

import protocol.Response;
import protocol.requests.GameRequest;
import server.ClientData;



public class CoinFlip extends GameInstance {

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
        System.out.println("Running game");
    }

    public CoinFlip() {
        super(1, 1);
    }

    @Override
    public boolean isFinished() {
        return getFinished();
    }

    @Override
    public void cleanup() {
        System.out.println("Cleanup called");
    }

    @Override
    public void handleRequest(GameRequest request, Response response) {
        ClientData client = getPlayer(request);

        if (client == null) return;

        int won = 0;

        if (!enoughFunds(client)){
            response.setStatusCode(Response.StatusCodes.ERR_NOT_ENOUGH_FUNDS);
            setFinished(true);
            return;
        }

        client.setCoins(client.getCoins() - 50);

        double i = Math.random();

        Sides side = i < 0.49 ? Sides.HEADS : Sides.TAILS;
        int chosenSide = request.getPayload()[0];

        if (side.getValue() == chosenSide) {
            client.setCoins(client.getCoins() + 100);
            won = 1;
        }

        response.data = new int[]{won, client.getCoins()};
        setFinished(true);
    }



}

