package be.kdg.teamh.entities;

import javax.persistence.*;

@Entity
public class Subthema {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String naam;
    private String beschrijving;
    @ManyToOne
    private Hoofdthema hoofdthema;
    @ManyToOne
    private Gebruiker gebruiker;

    public Subthema(String naam, String beschrijving, Hoofdthema hoofdthema, Gebruiker gebruiker) {
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.hoofdthema = hoofdthema;
        this.gebruiker = gebruiker;
    }

    public Subthema() {
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

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }
}
