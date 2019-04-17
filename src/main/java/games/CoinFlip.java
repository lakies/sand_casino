package games;

import server.ClientData;


public class CoinFlip extends games.GameInstance {

    @Override
    public boolean enoughFunds(ClientData client) {
        if (client.getCoins() >= 50) {
            client.setCoins(client.getCoins() - 50);
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
    public boolean ifFinished() {
        return false;
    }

    @Override
    public void cleanup() {

    }

    public static void main(String[] args) {


    }
    public void coinflip(ClientData client){
        if (this.enoughFunds(client)){
            double i = Math.random();
            //Siia saaks kliendile luua valiku illusiooni?
            if (i < 0.49) { //Kasiino võiks kasumlik olla xd
                client.setCoins(client.getCoins() + 100); //victory condition
            } else {
                System.out.println("You lost");
            }
        }

    }
}

