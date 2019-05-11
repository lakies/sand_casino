package protocol.requests;

import protocol.MessageType;
import protocol.Request;
import protocol.GameType;

public class StartGameRequest extends Request {
    private GameType gameType;

    public StartGameRequest(GameType gameType) {
        super(MessageType.START_GAME);
        this.gameType = gameType;
    }

    public GameType getGameType() {
        return gameType;
    }
}
