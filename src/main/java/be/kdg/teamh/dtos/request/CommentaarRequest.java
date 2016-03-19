package be.kdg.teamh.dtos.request;

import org.joda.time.DateTime;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class CommentaarRequest implements Serializable
{
    @NotNull
    private String tekst;

    @NotNull
    private DateTime datum;

    @NotNull
    private int gebruiker;

    @NotNull
    private int kaart;

    public CommentaarRequest()
    {
        //
    }

    public CommentaarRequest(String tekst, DateTime datum, int gebruiker, int kaart)
    {
        this.tekst = tekst;
        this.datum = datum;
        this.gebruiker = gebruiker;
        this.kaart = kaart;
    }

    public String getTekst()
    {
        return tekst;
    }

    public void setTekst(String tekst)
    {
        this.tekst = tekst;
    }

    public DateTime getDatum()
    {
        return datum;
    }

    public void setDatum(DateTime datum)
    {
        this.datum = datum;
    }

    public int getGebruiker()
    {
        return gebruiker;
    }

    public void setGebruiker(int gebruiker)
    {
        this.gebruiker = gebruiker;
    }

    public int getKaart()
    {
        return kaart;
    }

    public void setKaart(int kaart)
    {
        this.kaart = kaart;
    }
}
