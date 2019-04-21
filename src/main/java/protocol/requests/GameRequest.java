package protocol.requests;

import protocol.MessageType;
import protocol.Request;

public class GameRequest extends Request {
    private int[] payload;

    public GameRequest(int[] payload) {
        super(MessageType.GAME_DATA);
        this.payload = payload;
    }

    public int[] getPayload() {
        return payload;
    }
}
