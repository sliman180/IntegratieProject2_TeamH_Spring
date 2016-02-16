package be.kdg.teamh.entities;

import javax.persistence.*;

@Entity
@Table(name = "organisatie")
public class Organisatie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String naam;
    private String beschrijving;

    @ManyToOne
    private Gebruiker organisator;

    public Organisatie() {
    }

    public Organisatie(String naam, String beschrijving, Gebruiker organisator) {
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
}
