package be.kdg.teamh.dtos.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

public class DeelnameRequest implements Serializable
{
    @NotNull
    private int aangemaakteKaarten;

    @NotNull
    private boolean medeorganisator;

    @NotNull
    private LocalDateTime datum;

    @NotNull
    private int cirkelsessie;

    @NotNull
    private int gebruiker;

    public DeelnameRequest()
    {
        //
    }

    public DeelnameRequest(int aangemaakteKaarten, boolean medeorganisator, LocalDateTime datum, int cirkelsessie, int gebruiker)
    {
        this.aangemaakteKaarten = aangemaakteKaarten;
        this.medeorganisator = medeorganisator;
        this.datum = datum;
        this.cirkelsessie = cirkelsessie;
        this.gebruiker = gebruiker;
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
