package be.kdg.teamh.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "deelnames")
public class Deelname implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    private int aangemaakteKaarten;

    @NotNull
    private boolean medeorganisator;

    @ManyToOne(cascade = CascadeType.ALL)
    private Cirkelsessie cirkelsessie;

    @ManyToOne(cascade = CascadeType.ALL)
    private Gebruiker gebruiker;

    public Deelname()
    {
        //
    }

    public Deelname(int aangemaakteKaarten, boolean medeorganisator, Cirkelsessie cirkelsessie, Gebruiker gebruiker)
    {
        this.aangemaakteKaarten = aangemaakteKaarten;
        this.medeorganisator = medeorganisator;
        this.cirkelsessie = cirkelsessie;
        this.gebruiker = gebruiker;
    }

    public int getId()
    {
        return id;
    }

    public int getAangemaakteKaarten()
    {
        return aangemaakteKaarten;
    }

    public void setAangemaakteKaarten(int aangemaakteKaarten)
    {
        this.aangemaakteKaarten = aangemaakteKaarten;
    }

    public boolean isMedeorganisator()
    {
        return medeorganisator;
    }

    public void setMedeorganisator(boolean medeorganisator)
    {
        this.medeorganisator = medeorganisator;
    }

    public Cirkelsessie getCirkelsessie()
    {
        return cirkelsessie;
    }

    public void setCirkelsessie(Cirkelsessie cirkelsessie)
    {
        this.cirkelsessie = cirkelsessie;
    }

    public void setCirkelSessie(Cirkelsessie cirkelSessie)
    {
        this.cirkelsessie = cirkelSessie;
    }

    public Gebruiker getGebruiker()
    {
        return gebruiker;
    }

    public void setGebruiker(Gebruiker gebruiker)
    {
        this.gebruiker = gebruiker;
    }
}
