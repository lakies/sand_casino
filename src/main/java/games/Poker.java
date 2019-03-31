package games;

import server.ClientData;

public class Poker extends GameInstance {
    private double buyIn = 25;
    public Poker() {
    super(5, 2);
    }
    @Override
    public boolean enoughFunds(ClientData client) {
        return false;
    }

    @Override
    public void runGameLogic() {

    }

    @Override
    public void cleanup() {

    }

    @Override
    public boolean ifFinished() {
        return false;
    }
}
