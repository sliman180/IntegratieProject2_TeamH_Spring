package be.kdg.teamh.dtos.request;

import org.joda.time.DateTime;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class DeelnameRequest implements Serializable {
    @NotNull
    private int aangemaakteKaarten;

    @NotNull
    private boolean medeorganisator;

    @NotNull
    private DateTime datum;

    @NotNull
    private int cirkelsessie;

    @NotNull
    private int gebruiker;

    public DeelnameRequest() {
        //
    }

    public DeelnameRequest(int aangemaakteKaarten, boolean medeorganisator, DateTime datum, int cirkelsessie, int gebruiker) {
        this.aangemaakteKaarten = aangemaakteKaarten;
        this.medeorganisator = medeorganisator;
        this.datum = datum;
        this.cirkelsessie = cirkelsessie;
        this.gebruiker = gebruiker;
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
}
