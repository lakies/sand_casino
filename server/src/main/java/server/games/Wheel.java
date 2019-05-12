package server.games;

import protocol.Response;
import protocol.requests.GameRequest;
import server.ClientActions;
import server.ClientData;
import server.DatabaseHandler;

import java.util.Random;

public class Wheel extends GameInstance {
    private int userCoins;
//    private static double[] winRates = {0, 0, 0.5, 0.75, 1, 1, 1.25, 1.5, 1.5, 2, 2, 5};
    private double[] winRates = {5, 0, 0.5, 2, 1.5, 1, 1.25, 1, 1.5, 2, 0.75, 0};

    public Wheel(ClientActions clientActions) {
        super(1, 1, clientActions);
    }

    public void setUserCoins(int userCoins) {
        this.userCoins = userCoins;
    }

    @Override
    public void handleRequest(GameRequest request, Response response) {
        ClientData client = getPlayer(request);

        if (client == null) return;

        this.setUserCoins(request.getPayload()[0]);
        if (request.getRequestType() == GameRequest.GameRequestType.WHEEL_PAID) {
            if (client.getCoins() < userCoins) {
                response.setStatusCode(Response.StatusCodes.ERR_NOT_ENOUGH_FUNDS);
                setFinished(true);
                return;
            }
            updateCoins(client, client.getCoins() - userCoins);

        } else{
            if ((System.currentTimeMillis() - getTime(client))/1000 < 1800){
            response.setStatusCode(Response.StatusCodes.TIME_ERROR);
            setFinished(true);
            return;}

            setTime(client);
        }


        Random generator = new Random();
        int randomIndex = generator.nextInt(winRates.length);
        System.out.println(winRates[randomIndex]);
        int win = (int) ( userCoins*winRates[randomIndex]);
        int winnedCoins =  (client.getCoins() + (win));
        updateCoins(client, winnedCoins);

        response.data = new int[]{win, randomIndex};
        setFinished(true);
    }

    @Override
    public void runGameLogic() {


    }

    @Override
    public void cleanup() {

    }

    @Override
    public boolean isFinished() {
        return getFinished();
    }
}
