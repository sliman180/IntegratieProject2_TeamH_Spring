package be.kdg.teamh.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cirkelsessies")
public class Cirkelsessie implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String naam;

    @NotNull
    private Status status;

    @NotNull
    private int aantalCirkels;

    @NotNull
    private int maxAantalKaarten;

    @NotNull
    private LocalDateTime startDatum;

    @ManyToOne
    private Subthema subthema;

    @ManyToOne
    private Gebruiker gebruiker;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "cirkelsessie")
    private List<Deelname> deelnames = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "cirkelsessie")
    private List<Spelkaart> spelkaarten = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "cirkelsessie")
    private List<Bericht> berichten = new ArrayList<>();

    public Cirkelsessie()
    {
        //
    }

    public Cirkelsessie(String naam, int aantalCirkels, int maxAantalKaarten, Status status, LocalDateTime startDatum, Subthema subthema, Gebruiker gebruiker)
    {
        this.naam = naam;
        this.status = status;
        this.aantalCirkels = aantalCirkels;
        this.maxAantalKaarten = maxAantalKaarten;
        this.startDatum = startDatum;
        this.subthema = subthema;
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

    public int getAantalCirkels()
    {
        return aantalCirkels;
    }

    public void setAantalCirkels(int aantalCirkels)
    {
        this.aantalCirkels = aantalCirkels;
    }

    public int getMaxAantalKaarten()
    {
        return maxAantalKaarten;
    }

    public void setMaxAantalKaarten(int maxAantalKaarten)
    {
        this.maxAantalKaarten = maxAantalKaarten;
    }

    public Status getStatus()
    {
        return status;
    }

    public void setStatus(Status status)
    {
        this.status = status;
    }

    public LocalDateTime getStartDatum()
    {
        return startDatum;
    }

    public void setStartDatum(LocalDateTime startDatum)
    {
        this.startDatum = startDatum;
    }

    public Subthema getSubthema()
    {
        return subthema;
    }

    public void setSubthema(Subthema subthema)
    {
        this.subthema = subthema;
    }

    public Gebruiker getGebruiker()
    {
        return gebruiker;
    }

    public void setGebruiker(Gebruiker gebruiker)
    {
        this.gebruiker = gebruiker;
    }

    public List<Deelname> getDeelnames()
    {
        return deelnames;
    }

    public void setDeelnames(List<Deelname> deelnames)
    {
        this.deelnames = deelnames;
    }

    public void addDeelname(Deelname deelname)
    {
        this.deelnames.add(deelname);
    }

    public List<Spelkaart> getSpelkaarten()
    {
        return spelkaarten;
    }

    public void setSpelkaarten(List<Spelkaart> spelkaarten)
    {
        this.spelkaarten = spelkaarten;
    }

    public void addSpelkaart(Spelkaart spelkaart)
    {
        this.spelkaarten.add(spelkaart);
    }

    public List<Bericht> getBerichten()
    {
        return berichten;
    }

    public void setBerichten(List<Bericht> berichten)
    {
        this.berichten = berichten;
    }

    public void addBericht(Bericht bericht)
    {
        this.berichten.add(bericht);
    }
}
