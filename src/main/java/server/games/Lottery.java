package server.games;

import protocol.Response;
import protocol.requests.GameRequest;
import server.ClientData;

import java.util.Random;


public class Lottery extends GameInstance {


    public Lottery(){ //Iga nädal luuakse automaatselt see, mis peaks teoorias serveris jooksma nädal aeag
        super(50000, 1); //teoorias võib ka max piletihulgata teha

    }
    @Override
    public boolean enoughFunds(ClientData client) {
        if (client.getCoins() >= 50){
            client.setCoins(client.getCoins()-50);
            return true;}
        return false;
    }

    @Override
    public void runGameLogic() {
        Random generator = new Random();
        int randomIndex = generator.nextInt(getPlayers().size());
        System.out.println("Winner is: " + getPlayers().get(randomIndex));
        System.out.println("The prize was: " + getPlayers().size()*49); //Mängu tasu 50, võitja saab 49 korda sisseostjad tagasi
        getPlayers().get(randomIndex).setCoins(getPlayers().get(randomIndex).getCoins() + getPlayers().size()*49);
        //
    }

    @Override
    public void handleRequest(GameRequest request, Response response) {

    }

    @Override
    public void cleanup() {


    }

    @Override
    public boolean isFinished() {

                    //Siia peaks tegema, et automaatselt iga teatud aja tagant algab uus loterii;
        return false;
    }
}
