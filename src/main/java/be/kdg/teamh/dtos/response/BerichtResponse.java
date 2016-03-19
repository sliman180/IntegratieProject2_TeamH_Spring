package be.kdg.teamh.dtos.response;

import org.joda.time.DateTime;

import java.io.Serializable;

public class BerichtResponse implements Serializable {
    private int id;
    private String tekst;
    private DateTime datum;
    private int cirkelsessie;
    private int gebruiker;

    public BerichtResponse() {
        //
    }

    public BerichtResponse(int id, String tekst, DateTime datum, int cirkelsessie, int gebruiker) {
        this.id = id;
        this.tekst = tekst;
        this.datum = datum;
        this.cirkelsessie = cirkelsessie;
        this.gebruiker = gebruiker;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public DateTime getDatum() {
        return datum;
    }

    public void setDatum(DateTime datum) {
        this.datum = datum;
    }

    public int getCirkelsessie() {
        return cirkelsessie;
    }

    public void setCirkelsessie(int cirkelsessie) {
        this.cirkelsessie = cirkelsessie;
    }

    public int getGebruiker() {
        return gebruiker;
    }

    public void setGebruiker(int gebruiker) {
        this.gebruiker = gebruiker;
    }
}
