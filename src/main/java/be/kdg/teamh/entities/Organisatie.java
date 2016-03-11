package be.kdg.teamh.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "organisaties")
public class Organisatie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Column(unique = true)
    private String naam;

    @NotNull
    private String beschrijving;

    @ManyToOne(cascade = CascadeType.ALL)
    private Gebruiker gebruiker;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private List<Hoofdthema> hoofdthemas = new ArrayList<>();

    public Organisatie() {
        //
    }

    public Organisatie(String naam, String beschrijving, Gebruiker gebruiker) {
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.gebruiker = gebruiker;
    }


    public List<Hoofdthema> getHoofdthemas() {
        return hoofdthemas;
    }

    public void setHoofdthemas(List<Hoofdthema> hoofdthemas) {
        this.hoofdthemas = hoofdthemas;
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

    public Gebruiker getOrganisator() {
        return gebruiker;
    }

    public void setOrganisator(Gebruiker gebruiker) {
        this.gebruiker = gebruiker;
    }
}
