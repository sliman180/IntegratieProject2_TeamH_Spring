package be.kdg.teamh.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Cirkelsessie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String naam;
    private Date datum;
    private int maxAantalKaarten;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Subthema subthema;

    public Cirkelsessie() {
    }

    public Cirkelsessie(String naam, Date datum, int maxAantalKaarten, Subthema subthema) {
        this.naam = naam;
        this.datum = datum;
        this.maxAantalKaarten = maxAantalKaarten;
        this.subthema = subthema;
    }

    public int getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public int getMaxAantalKaarten() {
        return maxAantalKaarten;
    }

    public void setMaxAantalKaarten(int maxAantalKaarten) {
        this.maxAantalKaarten = maxAantalKaarten;
    }

    public Subthema getSubthema() {
        return subthema;
    }

    public void setSubthema(Subthema subthema) {
        this.subthema = subthema;
    }
}
