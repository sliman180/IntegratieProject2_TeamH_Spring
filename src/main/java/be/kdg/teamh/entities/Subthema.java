package be.kdg.teamh.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Subthema {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    private String naam;

    @NotNull
    private String beschrijving;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Hoofdthema hoofdthema;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Cirkelsessie> cirkelsessies = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Kaart> kaarten = new ArrayList<>();

    public Subthema() {
        // JPA Constructor
    }

    public Subthema(String naam, String beschrijving, Hoofdthema hoofdthema) {
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.hoofdthema = hoofdthema;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<Cirkelsessie> getCirkelsessies() {
        return cirkelsessies;
    }

    public void setCirkelsessies(List<Cirkelsessie> cirkelsessies) {
        this.cirkelsessies = cirkelsessies;
    }

    public List<Kaart> getKaarten() {
        return kaarten;
    }

    public void setKaarten(List<Kaart> kaarten) {
        this.kaarten = kaarten;
    }
}