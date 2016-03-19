package be.kdg.teamh.dtos.request;

import org.joda.time.DateTime;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class BerichtRequest implements Serializable
{
    @NotNull
    private String tekst;

    @NotNull
    private DateTime datum;

    @NotNull
    private int cirkelsessie;

    @NotNull
    private int gebruiker;

    public BerichtRequest()
    {
        //
    }

    public BerichtRequest(String tekst, DateTime datum, int cirkelsessie, int gebruiker)
    {
        this.tekst = tekst;
        this.datum = datum;
        this.cirkelsessie = cirkelsessie;
        this.gebruiker = gebruiker;
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

    public int getCirkelsessie()
    {
        return cirkelsessie;
    }

    public void setCirkelsessie(int cirkelsessie)
    {
        this.cirkelsessie = cirkelsessie;
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
