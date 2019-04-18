package protocol.requests;

import protocol.MessageType;
import protocol.Request;
import server.games.GameType;

public class StartGameRequest extends Request {
    private GameType gameType;
    private byte[] authToken;

    public StartGameRequest(GameType gameType, byte[] authToken) {
        super(MessageType.START_GAME);
        this.gameType = gameType;
        this.authToken = authToken;
    }

    public byte[] getAuthToken() {
        return authToken;
    }

    public GameType getGameType() {
        return gameType;
    }
}
