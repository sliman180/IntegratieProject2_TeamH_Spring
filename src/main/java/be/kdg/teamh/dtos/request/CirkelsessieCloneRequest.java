package be.kdg.teamh.dtos.request;

import be.kdg.teamh.entities.Status;
import org.joda.time.DateTime;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class CirkelsessieCloneRequest implements Serializable
{
    @NotNull
    private String naam;

    @NotNull
    private Status status;

    private int aantalCirkels;

    private int maxAantalKaarten;

    @NotNull
    private DateTime startDatum;

    private int subthema;

    private int gebruiker;

    public CirkelsessieCloneRequest()
    {
        //
    }

    public CirkelsessieCloneRequest(String naam, Status status, int aantalCirkels, int maxAantalKaarten, DateTime startDatum, int subthema, int gebruiker)
    {
        this.naam = naam;
        this.status = status;
        this.aantalCirkels = aantalCirkels;
        this.maxAantalKaarten = maxAantalKaarten;
        this.startDatum = startDatum;
        this.subthema = subthema;
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

    public Status getStatus()
    {
        return status;
    }

    public void setStatus(Status status)
    {
        this.status = status;
    }

    public int getAantalCirkels()
    {
        return aantalCirkels;
    }

    public void setAantalCirkels(int aantalCirkels)
    {
        this.aantalCirkels = aantalCirkels;
    }

    public int getMaxAantalKaarten()
    {
        return maxAantalKaarten;
    }

    public void setMaxAantalKaarten(int maxAantalKaarten)
    {
        this.maxAantalKaarten = maxAantalKaarten;
    }

    public DateTime getStartDatum()
    {
        return startDatum;
    }

    public void setStartDatum(DateTime startDatum)
    {
        this.startDatum = startDatum;
    }

    public int getSubthema()
    {
        return subthema;
    }

    public void setSubthema(int subthema)
    {
        this.subthema = subthema;
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
