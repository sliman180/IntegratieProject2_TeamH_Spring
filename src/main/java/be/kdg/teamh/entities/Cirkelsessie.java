package be.kdg.teamh.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by S on 22-2-2016.
 */
@Entity
public class Cirkelsessie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    private String naam;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Gebruiker gebruiker;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Deelname> deelnames = new ArrayList<>();

    @NotNull
    private int maxAantalKaarten;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Subthema subthema;

    public Cirkelsessie(String naam, Gebruiker gebruiker, Integer maxAantalKaarten, Subthema subthema) {
        this.naam = naam;
        this.gebruiker = gebruiker;
        this.maxAantalKaarten = maxAantalKaarten;
        this.subthema = subthema;
    }

    public Cirkelsessie() {
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

    public Gebruiker getGebruiker() {
        return gebruiker;
    }

    public void setGebruiker(Gebruiker gebruiker) {
        this.gebruiker = gebruiker;
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

    public List<Deelname> getDeelnames() {
        return deelnames;
    }

    public void setDeelnames(List<Deelname> deelnames) {
        this.deelnames = deelnames;
    }

    public void addDeelname(Deelname deelname){this.deelnames.add(deelname);}
}
