package protocol.requests;

import protocol.MessageType;
import protocol.Request;

public class GameRequest extends Request {
    public enum GameRequestType {
        LOTTERY_PROGRESS_QUERY,
        LOTTERY_ADD_BET
    }

    private int[] payload;

    private GameRequestType requestType;

    public GameRequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(GameRequestType requestType) {
        this.requestType = requestType;
    }

    public GameRequest(int[] payload) {
        super(MessageType.GAME_DATA);
        this.payload = payload;
    }



    public int[] getPayload() {
        return payload;
    }
}
