package be.kdg.teamh.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

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
    @JsonBackReference
    private Gebruiker gebruiker;

    @ManyToOne
    @JsonBackReference
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

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public LocalDateTime getDatum()
    {
        return datum;
    }

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
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
