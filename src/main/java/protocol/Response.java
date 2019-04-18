package protocol;

public class Response extends MessageBody{
    public enum statusCodes {
        SUCCESS,
        ERR_INVALID_CREDENTIALS,
        ERR_ACCOUNT_EXISTS
    }

    public Response() {
        super(MessageType.RESPONSE);
    }

    private byte[] authToken;
    private statusCodes statusCode = statusCodes.SUCCESS;

    public statusCodes getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(statusCodes statusCode) {
        this.statusCode = statusCode;
    }

    public byte[] getAuthToken() {
        return authToken;
    }

    public void setAuthToken(byte[] authToken) {
        this.authToken = authToken;
    }
}
