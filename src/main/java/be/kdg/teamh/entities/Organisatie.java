package be.kdg.teamh.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "organisaties")
public class Organisatie implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Column(unique = true)
    private String naam;

    @NotNull
    private String beschrijving;

    @ManyToOne
    @JsonManagedReference
    private Gebruiker gebruiker;

    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "organisatie")
    private List<Hoofdthema> hoofdthemas = new ArrayList<>();

    public Organisatie()
    {
        //
    }

    public Organisatie(String naam, String beschrijving, Gebruiker gebruiker)
    {
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.gebruiker = gebruiker;
    }

    public int getId()
    {
        return id;
    }

    public String getNaam()
    {
        return naam;
    }

    public void setNaam(String naam)
    {
        this.naam = naam;
    }

    public String getBeschrijving()
    {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving)
    {
        this.beschrijving = beschrijving;
    }

    public Gebruiker getGebruiker()
    {
        return gebruiker;
    }

    public void setGebruiker(Gebruiker gebruiker)
    {
        this.gebruiker = gebruiker;
    }

    public List<Hoofdthema> getHoofdthemas()
    {
        return hoofdthemas;
    }

    public void setHoofdthemas(List<Hoofdthema> hoofdthemas)
    {
        this.hoofdthemas = hoofdthemas;
    }

    public void addHoofdthema(Hoofdthema hoofdthema)
    {
        this.hoofdthemas.add(hoofdthema);
    }
}
