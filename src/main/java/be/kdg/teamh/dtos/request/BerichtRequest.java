package be.kdg.teamh.dtos.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

public class BerichtRequest implements Serializable
{
    @NotNull
    private String tekst;

    @NotNull
    private LocalDateTime datum;

    @NotNull
    private int cirkelsessie;

    @NotNull
    private int gebruiker;

    public BerichtRequest()
    {
        //
    }

    public BerichtRequest(String tekst, LocalDateTime datum, int cirkelsessie, int gebruiker)
    {
        this.tekst = tekst;
        this.datum = datum;
        this.cirkelsessie = cirkelsessie;
        this.gebruiker = gebruiker;
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
