package Games;

import Server.ClientData;


public class CoinFlip extends GameInstance {
    public static void main(String[] args) {

    }
    public void coinflip(ClientData client){
        double i = Math.random();
        if (i < 0.5){
            //heads
        } else {
            //tails
        }
    }
}
