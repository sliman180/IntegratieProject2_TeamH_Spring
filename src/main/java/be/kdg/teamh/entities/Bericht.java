package be.kdg.teamh.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Entity
@Table(name = "berichten")
public class Bericht
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String tekst;

    @NotNull
    private Date datum = new Date();

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Chat chat;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Gebruiker gebruiker;

    public Bericht()
    {
        // JPA Constructor
    }

    public Bericht(String tekst, Gebruiker gebruiker)
    {
        this.tekst = tekst;
        this.gebruiker = gebruiker;
    }


    public Chat getChat()
    {
        return chat;
    }

    public void setChat(Chat chat)
    {
        this.chat = chat;
    }

    public Gebruiker getGebruiker()
    {
        return gebruiker;
    }

    public void setGebruiker(Gebruiker gebruiker)
    {
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

    public String getTekst()
    {
        return tekst;
    }

    public void setTekst(String tekst)
    {
        this.tekst = tekst;
    }

    public Date getDatum()
    {
        return datum;
    }

    public void setDatum(Date datum)
    {
        this.datum = datum;
    }
}
