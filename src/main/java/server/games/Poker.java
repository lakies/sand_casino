package server.games;

import protocol.Response;
import protocol.requests.GameRequest;
import server.ClientData;

public class Poker extends GameInstance {
    private String[] Cards = {};

    public Poker() {
    super(5, 2);
    }
    @Override
    public boolean enoughFunds(ClientData client) {
        if (client.getCoins() >= 50){
            client.setCoins(client.getCoins()-50);
            return true;}
        return false;
    }

    @Override
    public void handleRequest(GameRequest request, Response response) {

    }

    @Override
    public void runGameLogic() {


    }

    @Override
    public void cleanup() {

    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
