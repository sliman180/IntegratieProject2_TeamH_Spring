package be.kdg.teamh.entities;

import javax.persistence.*;

@Entity
public class Subthema {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String naam;
    private String beschrijving;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Hoofdthema hoofdthema;

    public Subthema() {
    }

    public Subthema(String naam, String beschrijving, Hoofdthema hoofdthema) {
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.hoofdthema = hoofdthema;
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

    public Hoofdthema getHoofdthema() {
        return hoofdthema;
    }

    public void setHoofdthema(Hoofdthema hoofdthema) {
        this.hoofdthema = hoofdthema;
    }
}
