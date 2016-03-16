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
    @JsonBackReference
    private Cirkelsessie cirkelsessie;

    @ManyToOne
    @JsonBackReference
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
