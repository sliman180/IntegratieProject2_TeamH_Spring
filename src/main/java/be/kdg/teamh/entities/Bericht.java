package be.kdg.teamh.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "berichten")
public class Bericht implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String tekst;

    @NotNull
    private LocalDateTime datum;

    @ManyToOne
    private Cirkelsessie cirkelsessie;

    @ManyToOne
    private Gebruiker gebruiker;

    public Bericht()
    {
        //
    }

    public Bericht(String tekst, LocalDateTime datum, Cirkelsessie cirkelsessie, Gebruiker gebruiker)
    {
        this.tekst = tekst;
        this.datum = datum;
        this.cirkelsessie = cirkelsessie;
        this.gebruiker = gebruiker;
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

    public Cirkelsessie getCirkelsessie()
    {
        return cirkelsessie;
    }

    public void setCirkelsessie(Cirkelsessie cirkelsessie)
    {
        this.cirkelsessie = cirkelsessie;
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
