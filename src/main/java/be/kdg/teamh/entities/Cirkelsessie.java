package be.kdg.teamh.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
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
    private int aantalCirkels;

    @NotNull
    private int maxAantalKaarten;

    // @NotNull
    // private LocalDateTime datum;
    // TODO: http://stackoverflow.com/questions/27952472/serialize-deserialize-java-8-java-time-with-jackson-json-mapper

    @ManyToOne(cascade = CascadeType.ALL)
    private Subthema subthema;

    @ManyToOne(cascade = CascadeType.ALL)
    private Gebruiker gebruiker;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Deelname> deelnames = new ArrayList<>();


    @OneToMany(cascade = CascadeType.ALL)
    private List<Spelkaart> spelkaarten = new ArrayList<>();


    public Cirkelsessie()
    {
        // JPA Constructor
    }

    public Cirkelsessie(String naam, Integer maxAantalKaarten, int aantalCirkels, Subthema subthema, Gebruiker gebruiker)
    {
        this.naam = naam;
        this.maxAantalKaarten = maxAantalKaarten;
        this.subthema = subthema;
        this.gebruiker = gebruiker;
        this.aantalCirkels = aantalCirkels;
    }


    public Cirkelsessie(String naam, int maxAantalKaarten, int aantalCirkels)
    {
        this.naam = naam;
        this.maxAantalKaarten = maxAantalKaarten;
        this.aantalCirkels = aantalCirkels;
    }


    public int getAantalCirkels()
    {
        return aantalCirkels;
    }

    public void setAantalCirkels(int aantalCirkels)
    {
        this.aantalCirkels = aantalCirkels;
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

    public int getMaxAantalKaarten()
    {
        return maxAantalKaarten;
    }

    public void setMaxAantalKaarten(int maxAantalKaarten)
    {
        this.maxAantalKaarten = maxAantalKaarten;
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
        spelkaarten.add(spelkaart);
    }

    public void cloneDeelnames(List<Deelname> deelnames)
    {
        for (Deelname deelname : deelnames)
        {
            deelname.setCirkelsessie(this);
            this.deelnames.add(deelname);
        }
    }
}
