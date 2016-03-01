package be.kdg.teamh.entities;

import java.io.Serializable;

public class LoginResponse implements Serializable {
    public String token;

    public LoginResponse() {
    }

    public LoginResponse(final String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
