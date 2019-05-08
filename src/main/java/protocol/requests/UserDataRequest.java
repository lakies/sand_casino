/*
 * Holds username and password for account creation and login
 */

package protocol.requests;

import protocol.MessageType;
import protocol.Request;

public class UserDataRequest extends Request {
    private String username;
    private String password;

    public UserDataRequest(MessageType type, String username, String password) {
        super(type);

        this.username = username;
        this.password = password;
    }

    public UserDataRequest(MessageType type){
        super(type);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
