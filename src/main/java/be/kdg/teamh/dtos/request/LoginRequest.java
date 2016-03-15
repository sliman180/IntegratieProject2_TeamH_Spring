package be.kdg.teamh.dtos.request;

import java.io.Serializable;

public class LoginRequest implements Serializable
{
    private String gebruikersnaam;
    private String wachtwoord;

    public LoginRequest()
    {
        //
    }

    public LoginRequest(String gebruikersnaam, String wachtwoord)
    {
        this.gebruikersnaam = gebruikersnaam;
        this.wachtwoord = wachtwoord;
    }

    public String getGebruikersnaam()
    {
        return gebruikersnaam;
    }

    public void setGebruikersnaam(String gebruikersnaam)
    {
        this.gebruikersnaam = gebruikersnaam;
    }

    public String getWachtwoord()
    {
        return wachtwoord;
    }

    public void setWachtwoord(String wachtwoord)
    {
        this.wachtwoord = wachtwoord;
    }
}
