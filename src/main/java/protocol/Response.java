package protocol;

public class Response extends MessageBody{
    public enum StatusCodes {
        SUCCESS,
        ERR_INVALID_CREDENTIALS,
        ERR_ACCOUNT_EXISTS
    }

    public Response() {
        super(MessageType.RESPONSE);
    }

    private byte[] authToken;
    private StatusCodes statusCode = StatusCodes.SUCCESS;

    public StatusCodes getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(StatusCodes statusCode) {
        this.statusCode = statusCode;
    }

    public byte[] getAuthToken() {
        return authToken;
    }

    public void setAuthToken(byte[] authToken) {
        this.authToken = authToken;
    }

    public String message;
}
