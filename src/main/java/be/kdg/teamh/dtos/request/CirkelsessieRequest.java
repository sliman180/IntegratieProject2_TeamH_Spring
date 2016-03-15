package be.kdg.teamh.dtos.request;

import be.kdg.teamh.entities.Status;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

public class CirkelsessieRequest implements Serializable
{
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

    @NotNull
    private int subthema;

    @NotNull
    private int gebruiker;

    public CirkelsessieRequest()
    {
        //
    }

    public CirkelsessieRequest(String naam, Status status, int aantalCirkels, int maxAantalKaarten, LocalDateTime startDatum, int subthema, int gebruiker)
    {
        this.naam = naam;
        this.status = status;
        this.aantalCirkels = aantalCirkels;
        this.maxAantalKaarten = maxAantalKaarten;
        this.startDatum = startDatum;
        this.subthema = subthema;
        this.gebruiker = gebruiker;
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
}
