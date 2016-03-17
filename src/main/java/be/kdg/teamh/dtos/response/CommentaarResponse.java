package be.kdg.teamh.dtos.response;

import org.joda.time.DateTime;

import java.io.Serializable;

public class CommentaarResponse implements Serializable {
    private int id;
    private String tekst;
    private DateTime datum;
    private int gebruiker;
    private int kaart;

    public CommentaarResponse() {
        //
    }

    public CommentaarResponse(int id, String tekst, DateTime datum, int gebruiker, int kaart) {
        this.id = id;
        this.tekst = tekst;
        this.datum = datum;
        this.gebruiker = gebruiker;
        this.kaart = kaart;
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

    public int getGebruiker() {
        return gebruiker;
    }

    public void setGebruiker(int gebruiker) {
        this.gebruiker = gebruiker;
    }

    public int getKaart() {
        return kaart;
    }

    public void setKaart(int kaart) {
        this.kaart = kaart;
    }
}
