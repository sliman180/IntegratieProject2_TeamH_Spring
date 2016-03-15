package be.kdg.teamh.dtos.response;

import be.kdg.teamh.entities.Bericht;
import be.kdg.teamh.entities.Status;
import be.kdg.teamh.entities.Deelname;
import be.kdg.teamh.entities.Spelkaart;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CirkelsessieResponse implements Serializable
{
    private int id;
    private String naam;
    private Status status;
    private int aantalCirkels;
    private int maxAantalKaarten;
    private LocalDateTime startDatum;
    private int subthema;
    private int gebruiker;
    private List<Deelname> deelnames = new ArrayList<>();
    private List<Spelkaart> spelkaarten = new ArrayList<>();
    private List<Bericht> berichten = new ArrayList<>();

    public CirkelsessieResponse()
    {
        //
    }

    public CirkelsessieResponse(int id, String naam, Status status, int aantalCirkels, int maxAantalKaarten, LocalDateTime startDatum, int subthema, int gebruiker)
    {
        this.id = id;
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

    public Status getStatus()
    {
        return status;
    }

    public void setStatus(Status status)
    {
        this.status = status;
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

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public LocalDateTime getStartDatum()
    {
        return startDatum;
    }

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    public void setStartDatum(LocalDateTime startDatum)
    {
        this.startDatum = startDatum;
    }

    public int getSubthema()
    {
        return subthema;
    }

    public void setSubthema(int subthema)
    {
        this.subthema = subthema;
    }

    public int getGebruiker()
    {
        return gebruiker;
    }

    public void setGebruiker(int gebruiker)
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

    public List<Spelkaart> getSpelkaarten()
    {
        return spelkaarten;
    }

    public void setSpelkaarten(List<Spelkaart> spelkaarten)
    {
        this.spelkaarten = spelkaarten;
    }

    public List<Bericht> getBerichten()
    {
        return berichten;
    }

    public void setBerichten(List<Bericht> berichten)
    {
        this.berichten = berichten;
    }
}
