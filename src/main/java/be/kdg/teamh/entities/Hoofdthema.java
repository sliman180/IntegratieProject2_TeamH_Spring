package be.kdg.teamh.entities;

import javax.persistence.*;

@Entity
@Table(name = "hoofdthemas")
public class Hoofdthema
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String naam;
    private String beschrijving;

    @OneToOne(cascade = CascadeType.ALL)
    private Organisatie organisatie;

    @ManyToOne(cascade = CascadeType.ALL)
    private Gebruiker gebruiker;

    public Hoofdthema()
    {
        //
    }

    public Hoofdthema(int id, String naam, String beschrijving, Organisatie organisatie, Gebruiker gebruiker)
    {
        this.id = id;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.organisatie = organisatie;
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

    public Organisatie getOrganisatie()
    {
        return organisatie;
    }

    public void setOrganisatie(Organisatie organisatie)
    {
        this.organisatie = organisatie;
    }

    public Gebruiker getGebruiker()
    {
        return gebruiker;
    }

    public void setGebruiker(Gebruiker gebruiker)
    {
        this.gebruiker = gebruiker;
    }
}
