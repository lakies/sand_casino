package protocol.requests;

import protocol.MessageType;
import protocol.Request;
import server.games.GameType;

public class StartGameRequest extends Request {
    private GameType gameType;
    private String authToken;

    public StartGameRequest(GameType gameType, String authToken) {
        super(MessageType.START_GAME);
        this.gameType = gameType;
        this.authToken = authToken;
    }

    public String getAuthToken() {
        return authToken;
    }

    public GameType getGameType() {
        return gameType;
    }
}
