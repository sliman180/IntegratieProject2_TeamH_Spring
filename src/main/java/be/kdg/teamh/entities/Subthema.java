package be.kdg.teamh.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "subthemas")
public class Subthema implements Serializable
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
    private Hoofdthema hoofdthema;

    @ManyToOne
    private Gebruiker gebruiker;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "subthema")
    private List<Cirkelsessie> cirkelsessies = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "subthemas")
    private List<Kaart> kaarten = new ArrayList<>();

    public Subthema()
    {
        //
    }

    public Subthema(String naam, String beschrijving, Hoofdthema hoofdthema, Gebruiker gebruiker)
    {
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.hoofdthema = hoofdthema;
        this.gebruiker = gebruiker;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
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

    public Hoofdthema getHoofdthema()
    {
        return hoofdthema;
    }

    public void setHoofdthema(Hoofdthema hoofdthema)
    {
        this.hoofdthema = hoofdthema;
    }

    public Gebruiker getGebruiker()
    {
        return gebruiker;
    }

    public void setGebruiker(Gebruiker gebruiker)
    {
        this.gebruiker = gebruiker;
    }

    public List<Cirkelsessie> getCirkelsessies()
    {
        return cirkelsessies;
    }

    public void setCirkelsessies(List<Cirkelsessie> cirkelsessies)
    {
        this.cirkelsessies = cirkelsessies;
    }

    public void addCirkelsessie(Cirkelsessie cirkelsessie)
    {
        this.cirkelsessies.add(cirkelsessie);
    }

    public List<Kaart> getKaarten()
    {
        return kaarten;
    }

    public void setKaarten(List<Kaart> kaarten)
    {
        this.kaarten = kaarten;
    }

    public void addKaart(Kaart kaart)
    {
        this.kaarten.add(kaart);
    }
}
