package be.kdg.teamh.dtos.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.io.Serializable;
import java.time.LocalDateTime;

public class BerichtResponse implements Serializable
{
    private int id;
    private String tekst;
    private LocalDateTime datum;
    private int cirkelsessie;
    private int gebruiker;

    public BerichtResponse()
    {
        //
    }

    public BerichtResponse(int id, String tekst, LocalDateTime datum, int cirkelsessie, int gebruiker)
    {
        this.id = id;
        this.tekst = tekst;
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

    public int getCirkelsessie()
    {
        return cirkelsessie;
    }

    public void setCirkelsessie(int cirkelsessie)
    {
        this.cirkelsessie = cirkelsessie;
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
