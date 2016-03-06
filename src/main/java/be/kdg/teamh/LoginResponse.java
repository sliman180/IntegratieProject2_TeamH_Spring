package be.kdg.teamh;

import java.io.Serializable;

public class LoginResponse implements Serializable
{
    private int id;
    private String naam;
    private String token;

    public LoginResponse(int id, String naam, String token)
    {
        this.id = id;
        this.naam = naam;
        this.token = token;
    }

    public int getId()
    {
        return id;
    }

    public String getNaam()
    {
        return naam;
    }

    public String getToken()
    {
        return token;
    }
}
