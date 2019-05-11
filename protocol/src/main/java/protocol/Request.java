package protocol;

public class Request extends MessageBody {
    private String authToken;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Request(MessageType type) {
        super(type);
    }
}
