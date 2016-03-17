package be.kdg.teamh.dtos.response;

import java.io.Serializable;

public class LoginResponse implements Serializable {
    private String token;

    public LoginResponse() {
        //
    }

    public LoginResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
