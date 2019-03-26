package Games;

import Server.ClientData;

public class Wheel extends GameInstance {
    private double userCoins;

    public Wheel(double buyIn) {
        super(1, 1);
        this.userCoins = buyIn;
    }

    public Wheel() {
        super(1, 1);
    }

    @Override
    public boolean enoughFunds(ClientData client) {
        if (client.getCoins() >= userCoins) {
            client.setCoins(client.getCoins() - userCoins);
            return true;
        }
        System.out.println("Insufficient funds");
        return false;
    }

    @Override
    public void runGameLogic() {


    }
    public void wheel(ClientData clientData){
        if (enoughFunds(clientData)){
            

        }
    }

    @Override
    public void cleanup() {

    }

    @Override
    public boolean ifFinished() {
        return false;
    }
}
