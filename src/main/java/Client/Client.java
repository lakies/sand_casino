package Client;

import Games.GameTypes;

public class Client {
    public static void main(String[] args) {
        // TODO: Terminal ui
        System.out.println("Welcome to the best internet casino.");

        User user = LoginHandler.login();

        if (user == null){
            System.out.println("Auth failed");
            return;
        }

        System.out.println(user);
        System.out.println(user.isAuthenticated());

        user.playGame(GameTypes.COINFLIP);
    }
}
