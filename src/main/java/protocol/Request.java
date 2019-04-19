package protocol;

public abstract class Request extends MessageBody {
    private byte[] authToken;

    public byte[] getAuthToken() {
        return authToken;
    }

    public void setAuthToken(byte[] authToken) {
        this.authToken = authToken;
    }

    public Request(MessageType type) {
        super(type);
    }
}
