package be.kdg.teamh.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class Commentaar implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String tekst;

    private LocalDateTime datum;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Gebruiker gebruiker;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Kaart kaart;

    public Commentaar()
    {
        //JPA Constructor
    }

    public Commentaar(String tekst, Gebruiker gebruiker)
    {
        this.tekst = tekst;
        this.gebruiker = gebruiker;
    }

    public Kaart getKaart()
    {
        return kaart;
    }

    public void setKaart(Kaart kaart)
    {
        this.kaart = kaart;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getTekst()
    {
        return tekst;
    }

    public void setTekst(String tekst)
    {
        this.tekst = tekst;
    }

    public LocalDateTime getDatum()
    {
        return datum;
    }

    public void setDatum(LocalDateTime datum)
    {
        this.datum = datum;
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
