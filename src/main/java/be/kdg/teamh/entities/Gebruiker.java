package be.kdg.teamh.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "gebruikers")
public class Gebruiker implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String naam;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Hoofdthema> hoofdthemas = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Kaart> kaarten = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Deelname> deelnames = new ArrayList<>();

    public Gebruiker() {
        // JPA constructor
    }

    public Gebruiker(String naam) {
        this.naam = naam;
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

    public List<Hoofdthema> getHoofdthemas() {
        return hoofdthemas;
    }

    public void setHoofdthemas(List<Hoofdthema> hoofdthemas) {
        this.hoofdthemas = hoofdthemas;
    }

    public List<Kaart> getKaarten() {
        return kaarten;
    }

    public void setKaarten(List<Kaart> kaarten) {
        this.kaarten = kaarten;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Deelname> getDeelnames() {
        return deelnames;
    }

    public void setDeelnames(List<Deelname> deelnames) {
        this.deelnames = deelnames;
    }

    public void addDeelname(Deelname deelname){this.deelnames.add(deelname);}
}
