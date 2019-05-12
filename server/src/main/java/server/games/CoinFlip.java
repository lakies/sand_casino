package server.games;

import protocol.Response;
import protocol.requests.GameRequest;
import server.ClientActions;
import server.ClientData;
import server.DatabaseHandler;


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
    public void runGameLogic() {
        System.out.println("Running game");
    }

    public CoinFlip(ClientActions clientActions) {
        super(1, 1, clientActions);
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

        if (client.getCoins() < 50){
            response.setStatusCode(Response.StatusCodes.ERR_NOT_ENOUGH_FUNDS);
            setFinished(true);
            return;
        }

        updateCoins(client, client.getCoins() - 50);

        double i = Math.random();

        Sides side = i < 0.49 ? Sides.HEADS : Sides.TAILS;
        int chosenSide = request.getPayload()[0];

        if (side.getValue() == chosenSide) {
            updateCoins(client, client.getCoins() + 100);
            won = 1;
        }

        response.data = new int[]{won, client.getCoins()};
        setFinished(true);

    }

}