package be.kdg.teamh.dtos.request;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class HoofdthemaRequest implements Serializable
{
    @NotNull
    @Column(unique = true)
    private String naam;

    @NotNull
    private String beschrijving;

    @NotNull
    private int organisatie;

    @NotNull
    private int gebruiker;

    public HoofdthemaRequest()
    {
        //
    }

    public HoofdthemaRequest(String naam, String beschrijving, int organisatie, int gebruiker)
    {
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.organisatie = organisatie;
        this.gebruiker = gebruiker;
    }

    public String getNaam()
    {
        return naam;
    }

    public void setNaam(String naam)
    {
        this.naam = naam;
    }

    public String getBeschrijving()
    {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving)
    {
        this.beschrijving = beschrijving;
    }

    public int getOrganisatie()
    {
        return organisatie;
    }

    public void setOrganisatie(int organisatie)
    {
        this.organisatie = organisatie;
    }

    public int getGebruiker()
    {
        return gebruiker;
    }

    public void setGebruiker(int gebruiker)
    {
        this.gebruiker = gebruiker;
    }
}
