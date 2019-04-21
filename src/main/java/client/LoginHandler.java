package client;

import protocol.MessageType;
import protocol.Response;
import protocol.requests.UserDataRequest;

import java.io.IOException;

public class LoginHandler {
    public static User login(String username, String password) throws IOException {
        User user = new User(username, password);

        if (user.authenticate()) {
            // Return the authenticated user
            return user;
        } else {
            return null;
        }
    }

    public static User createAccount(String username, String password) throws IOException {
        User user = new User(username, password);
        ServerCommunicator serverCommunicator = new ServerCommunicator(user);
        UserDataRequest userDataRequest = new UserDataRequest(MessageType.CREATE_ACCOUNT, username, password);
        Response response = serverCommunicator.sendRequest(userDataRequest);
        if (response.getStatusCode() == Response.StatusCodes.ERR_ACCOUNT_EXISTS) {
            return null;
        } else if (response.getStatusCode() == Response.StatusCodes.ERR_FAILED_USER_CREATION) {
            throw new RuntimeException();
        }
        // TODO: maybe test password security
        user.setAuthToken(response.getAuthToken());
        return user;
    }
}
