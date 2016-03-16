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
@Table(name = "deelnames")
public class Deelname implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private int aangemaakteKaarten;

    @NotNull
    private boolean medeorganisator;

    @NotNull
    private LocalDateTime datum;

    @ManyToOne
    @JsonBackReference
    private Cirkelsessie cirkelsessie;

    @ManyToOne
    @JsonBackReference
    private Gebruiker gebruiker;

    public Deelname()
    {
        //
    }

    public Deelname(int aangemaakteKaarten, boolean medeorganisator, LocalDateTime datum, Cirkelsessie cirkelsessie, Gebruiker gebruiker)
    {
        this.aangemaakteKaarten = aangemaakteKaarten;
        this.medeorganisator = medeorganisator;
        this.datum = datum;
        this.cirkelsessie = cirkelsessie;
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

    public int getAangemaakteKaarten()
    {
        return aangemaakteKaarten;
    }

    public void setAangemaakteKaarten(int aangemaakteKaarten)
    {
        this.aangemaakteKaarten = aangemaakteKaarten;
    }

    public boolean isMedeorganisator()
    {
        return medeorganisator;
    }

    public void setMedeorganisator(boolean medeorganisator)
    {
        this.medeorganisator = medeorganisator;
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
