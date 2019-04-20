package server;

import java.util.Arrays;

public class AuthToken {

    private byte[] token;

    public AuthToken(byte[] token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthToken authToken = (AuthToken) o;
        return Arrays.equals(token, authToken.token);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(token);
    }
}
