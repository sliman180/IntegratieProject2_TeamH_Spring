package be.kdg.teamh.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "organisaties")
public class Organisatie
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    private String naam;

    @NotNull
    private String beschrijving;

    @ManyToOne(cascade = CascadeType.ALL)
    private Gebruiker organisator;

    public Organisatie()
    {
        // JPA Constructor
    }

    public Organisatie(String naam, String beschrijving, Gebruiker organisator)
    {
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.organisator = organisator;
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
        return organisator;
    }

    public void setOrganisator(Gebruiker organisator)
    {
        this.organisator = organisator;
    }
}
