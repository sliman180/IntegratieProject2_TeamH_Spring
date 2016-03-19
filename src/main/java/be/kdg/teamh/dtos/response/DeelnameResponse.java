package be.kdg.teamh.dtos.response;

import org.joda.time.DateTime;

import java.io.Serializable;

public class DeelnameResponse implements Serializable {
    private int id;
    private int aangemaakteKaarten;
    private boolean medeorganisator;
    private boolean isAanDeBeurt;
    private DateTime datum;
    private int cirkelsessie;
    private int gebruiker;


    public DeelnameResponse() {
        //
    }

    public DeelnameResponse(int id, int aangemaakteKaarten, boolean medeorganisator, DateTime datum, int cirkelsessie, int gebruiker, boolean isAanDeBeurt) {
        this.id = id;
        this.aangemaakteKaarten = aangemaakteKaarten;
        this.medeorganisator = medeorganisator;
        this.datum = datum;
        this.cirkelsessie = cirkelsessie;
        this.gebruiker = gebruiker;
        this.isAanDeBeurt = isAanDeBeurt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAangemaakteKaarten() {
        return aangemaakteKaarten;
    }

    public void setAangemaakteKaarten(int aangemaakteKaarten) {
        this.aangemaakteKaarten = aangemaakteKaarten;
    }

    public boolean isMedeorganisator() {
        return medeorganisator;
    }

    public void setMedeorganisator(boolean medeorganisator) {
        this.medeorganisator = medeorganisator;
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

    public boolean isAanDeBeurt() {
        return isAanDeBeurt;
    }

    public void setAanDeBeurt(boolean aanDeBeurt) {
        isAanDeBeurt = aanDeBeurt;
    }
}
