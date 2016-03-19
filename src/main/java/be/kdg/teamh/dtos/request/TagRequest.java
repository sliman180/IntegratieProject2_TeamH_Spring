package be.kdg.teamh.dtos.request;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class TagRequest implements Serializable
{
    @NotNull
    private String naam;

    @NotNull
    private int hoofdthema;

    public TagRequest()
    {
        //
    }

    public TagRequest(String naam, int hoofdthema)
    {
        this.naam = naam;
        this.hoofdthema = hoofdthema;
    }

    public String getNaam()
    {
        return naam;
    }

    public void setNaam(String naam)
    {
        this.naam = naam;
    }

    public int getHoofdthema()
    {
        return hoofdthema;
    }

    public void setHoofdthema(int hoofdthema)
    {
        this.hoofdthema = hoofdthema;
    }
}
