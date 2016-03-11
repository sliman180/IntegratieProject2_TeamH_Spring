package be.kdg.teamh.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hoofdthemas")
public class Hoofdthema implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Column(unique = true)
    private String naam;

    @NotNull
    private String beschrijving;

    @ManyToOne(cascade = CascadeType.ALL)
    private Organisatie organisatie;

    @ManyToOne(cascade = CascadeType.ALL)
    private Gebruiker gebruiker;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private List<Subthema> subthemas = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private List<Tag> tags = new ArrayList<>();

    private Hoofdthema() {
        //
    }

    public Hoofdthema(String naam, String beschrijving, Organisatie organisatie, Gebruiker gebruiker) {
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.organisatie = organisatie;
        this.gebruiker = gebruiker;
    }

    public List<Subthema> getSubthemas() {
        return subthemas;
    }

    public void setSubthemas(List<Subthema> subthemas) {
        this.subthemas = subthemas;
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

    public Organisatie getOrganisatie() {
        return organisatie;
    }

    public void setOrganisatie(Organisatie organisatie) {
        this.organisatie = organisatie;
    }

    public Gebruiker getGebruiker() {
        return gebruiker;
    }

    public void setGebruiker(Gebruiker gebruiker) {
        this.gebruiker = gebruiker;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
