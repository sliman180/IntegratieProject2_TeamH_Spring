package be.kdg.teamh.dtos.request;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class GebruikerRequest implements Serializable
{
    @NotNull
    private String email;

    @NotNull
    private String gebruikersnaam;

    @NotNull
    private String wachtwoord;

    private String voornaam;
    private String familienaam;
    private String telefoon;

    public GebruikerRequest()
    {
        //
    }

    public GebruikerRequest(String email, String gebruikersnaam, String wachtwoord)
    {
        this.email = email;
        this.gebruikersnaam = gebruikersnaam;
        this.wachtwoord = wachtwoord;
    }

    public GebruikerRequest(String email, String gebruikersnaam, String wachtwoord, String voornaam, String familienaam, String telefoon)
    {
        this.email = email;
        this.gebruikersnaam = gebruikersnaam;
        this.wachtwoord = wachtwoord;
        this.voornaam = voornaam;
        this.familienaam = familienaam;
        this.telefoon = telefoon;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
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

    public String getVoornaam()
    {
        return voornaam;
    }

    public void setVoornaam(String voornaam)
    {
        this.voornaam = voornaam;
    }

    public String getFamilienaam()
    {
        return familienaam;
    }

    public void setFamilienaam(String familienaam)
    {
        this.familienaam = familienaam;
    }

    public String getTelefoon()
    {
        return telefoon;
    }

    public void setTelefoon(String telefoon)
    {
        this.telefoon = telefoon;
    }
}
