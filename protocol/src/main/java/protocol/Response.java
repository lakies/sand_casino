package protocol;

import java.time.LocalDateTime;

public class Response extends MessageBody{
    public enum StatusCodes {
        SUCCESS,
        ERR_INVALID_CREDENTIALS,
        ERR_ACCOUNT_EXISTS,
        ERR_FAILED_USER_CREATION,
        ERR_INVALID_REQUEST,
        ERR_NOT_ENOUGH_FUNDS,
        TIME_ERROR
    }

    public Response() {
        super(MessageType.RESPONSE);
    }

    private String authToken;
    private StatusCodes statusCode = StatusCodes.SUCCESS;

    public StatusCodes getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(StatusCodes statusCode) {
        this.statusCode = statusCode;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String message;

    public LocalDateTime time;

    public int[] data;

}
