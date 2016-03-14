package be.kdg.teamh.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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

    @NotNull
    private boolean isGesloten;

    @NotNull
    private Date startDatum;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Subthema subthema;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private Gebruiker gebruiker;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Deelname> deelnames = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Spelkaart> spelkaarten = new ArrayList<>();

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private Chat chat;

    public Cirkelsessie()
    {
        //
    }

    public Cirkelsessie(String naam, int aantalCirkels, int maxAantalKaarten, boolean isGesloten, Date startDatum)
    {
        this.naam = naam;
        this.maxAantalKaarten = maxAantalKaarten;
        this.aantalCirkels = aantalCirkels;
        this.isGesloten = isGesloten;
        this.startDatum = startDatum;
        this.chat = new Chat("Chat: " + naam);
    }

    public Cirkelsessie(String naam, int aantalCirkels, int maxAantalKaarten, Subthema subthema, Gebruiker gebruiker)
    {
        this.naam = naam;
        this.aantalCirkels = aantalCirkels;
        this.maxAantalKaarten = maxAantalKaarten;
        this.subthema = subthema;
        this.gebruiker = gebruiker;
        this.chat = new Chat("Chat: " + naam);
    }

    public Cirkelsessie(String naam, int aantalCirkels, int maxAantalKaarten, boolean isGesloten, Date startDatum, Subthema subthema, Gebruiker gebruiker)
    {
        this.naam = naam;
        this.aantalCirkels = aantalCirkels;
        this.maxAantalKaarten = maxAantalKaarten;
        this.isGesloten = isGesloten;
        this.startDatum = startDatum;
        this.subthema = subthema;
        this.gebruiker = gebruiker;
        this.chat = new Chat("Chat: " + naam);
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

    public boolean isGesloten()
    {
        return isGesloten;
    }

    public void setGesloten(boolean gesloten)
    {
        isGesloten = gesloten;
    }

    public int getAantalCirkels()
    {
        return aantalCirkels;
    }

    public void setAantalCirkels(int aantalCirkels)
    {
        this.aantalCirkels = aantalCirkels;
    }

    public Date getStartDatum()
    {
        return startDatum;
    }

    public void setStartDatum(Date startDatum)
    {
        this.startDatum = startDatum;
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

    public Chat getChat()
    {
        return chat;
    }

    public void setChat(Chat chat)
    {
        this.chat = chat;
    }


}
