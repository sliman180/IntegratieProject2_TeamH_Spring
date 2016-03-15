package be.kdg.teamh.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "commentaren")
public class Commentaar implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String tekst;

    @NotNull
    private LocalDateTime datum;

    @ManyToOne
    private Gebruiker gebruiker;

    @ManyToOne
    private Kaart kaart;

    public Commentaar()
    {
        //
    }

    public Commentaar(String tekst, LocalDateTime datum, Gebruiker gebruiker, Kaart kaart)
    {
        this.tekst = tekst;
        this.datum = datum;
        this.gebruiker = gebruiker;
        this.kaart = kaart;
    }

    public int getId()
    {
        return id;
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

    public Kaart getKaart()
    {
        return kaart;
    }

    public void setKaart(Kaart kaart)
    {
        this.kaart = kaart;
    }
}
