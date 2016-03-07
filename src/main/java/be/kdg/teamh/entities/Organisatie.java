package be.kdg.teamh.entities;

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

    @ManyToOne(cascade = CascadeType.MERGE)
    private Gebruiker gebruiker;

    @OneToMany(cascade = CascadeType.ALL)
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

    public void setId(Integer id)
    {
        this.id = id;
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

    public Gebruiker getOrganisator()
    {
        return gebruiker;
    }

    public void setOrganisator(Gebruiker gebruiker)
    {
        this.gebruiker = gebruiker;
    }
}
