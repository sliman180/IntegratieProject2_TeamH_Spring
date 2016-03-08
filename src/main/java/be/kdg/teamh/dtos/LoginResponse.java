package be.kdg.teamh.dtos;

import java.io.Serializable;

public class LoginResponse implements Serializable
{
    private int id;
    private String naam;
    private String token;

    public LoginResponse()
    {
        //
    }

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

    public void setId(int id)
    {
        this.id = id;
    }

    public String getNaam()
    {
        return naam;
    }

    public void setNaam(String naam)
    {
        this.naam = naam;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }
}
