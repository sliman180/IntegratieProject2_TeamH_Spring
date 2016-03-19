package be.kdg.teamh.dtos.request;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class GebruikerRequest implements Serializable
{
    @NotNull
    private String gebruikersnaam;

    @NotNull
    private String wachtwoord;

    public GebruikerRequest()
    {
        //
    }

    public GebruikerRequest(String gebruikersnaam, String wachtwoord)
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
