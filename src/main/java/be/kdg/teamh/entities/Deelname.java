package be.kdg.teamh.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by S on 22-2-2016.
 */
@Entity
public class Deelname {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Cirkelsessie cirkelSessie;

    @NotNull
    private int aangemaakteKaarten;

    @NotNull
    private boolean medeorganisator;

    public Deelname(Gebruiker gebruiker, Cirkelsessie cirkelSessie, int aangemaakteKaarten, boolean medeorganisator) {
        this.cirkelSessie = cirkelSessie;
        this.aangemaakteKaarten = aangemaakteKaarten;
        this.medeorganisator = medeorganisator;
    }

    public Deelname() {
    }

    public Cirkelsessie getCirkelSessie() {
        return cirkelSessie;
    }

    public void setCirkelSessie(Cirkelsessie cirkelSessie) {
        this.cirkelSessie = cirkelSessie;
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
}
