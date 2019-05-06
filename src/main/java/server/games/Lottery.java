package server.games;


import client.Client;
import protocol.Response;
import protocol.requests.GameRequest;
import server.ClientData;

import java.util.List;
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

    }

    @Override
    public void handleRequest(GameRequest request, Response response) {
        Random generator = new Random();
        int randomIndex = generator.nextInt(getPlayers().size());
        ClientData cd = getPlayers().get(randomIndex);
        int prizemoney = getPlayers().size();


        cd.setCoins(cd.getCoins() + prizemoney);


       // response.data = new int[]{cd.getAuthToken(), prizemoney};
        setFinished(true);
    }

    @Override
    public void cleanup() {


    }

    @Override
    public boolean isFinished() {

        return false;
    }
}
