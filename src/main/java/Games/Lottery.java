package Games;

import Server.ClientData;

public class Lottery extends GameInstance {

    public Lottery(){
        super(50000, 1);
        
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
