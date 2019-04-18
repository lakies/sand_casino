package server.games;

import server.ClientData;

import java.util.Random;

public class Wheel extends GameInstance {
    private double userCoins;
    private double[] winRates = {0, 0, 0.5, 0.75,  1, 1, 1.25, 1.5,  1.5, 2};

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
    public void Game(ClientData clientData){
            Random generator = new Random();
            int randomIndex = generator.nextInt(winRates.length);
            System.out.println("Your ratio was: " + winRates[randomIndex]);
            clientData.setCoins(clientData.getCoins()+userCoins*winRates[randomIndex]);

    }

    @Override
    public void cleanup() {

    }

    @Override
    public boolean ifFinished() {
        return false;
    }
}
