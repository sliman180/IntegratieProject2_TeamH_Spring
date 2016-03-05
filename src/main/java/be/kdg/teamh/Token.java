package be.kdg.teamh;

import java.io.Serializable;

public class Token implements Serializable {
    public String token;

    public Token() {
        // JPA Constructor
    }

    public Token(final String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
