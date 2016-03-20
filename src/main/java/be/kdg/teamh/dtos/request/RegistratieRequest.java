package be.kdg.teamh.dtos.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class RegistratieRequest implements Serializable
{
    @NotNull
    private String email;

    @NotNull
    private String gebruikersnaam;

    @NotNull
    @Size(min = 8)
    private String wachtwoord;

    @NotNull
    @Size(min = 8)
    private String confirmatie;

    public RegistratieRequest()
    {
        //
    }

    public RegistratieRequest(String email, String gebruikersnaam, String wachtwoord, String confirmatie)
    {
        this.email = email;
        this.gebruikersnaam = gebruikersnaam;
        this.wachtwoord = wachtwoord;
        this.confirmatie = confirmatie;
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

    public String getConfirmatie()
    {
        return confirmatie;
    }

    public void setConfirmatie(String confirmatie)
    {
        this.confirmatie = confirmatie;
    }
}
