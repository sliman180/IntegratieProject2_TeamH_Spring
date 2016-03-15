package be.kdg.teamh.dtos.request;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class SubthemaRequest implements Serializable
{
    @NotNull
    private String naam;

    @NotNull
    private String beschrijving;

    @NotNull
    private int hoofdthema;

    public SubthemaRequest()
    {
        //
    }

    public SubthemaRequest(String naam, String beschrijving, int hoofdthema)
    {
        this.naam = naam;
        this.beschrijving = beschrijving;
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

    public String getBeschrijving()
    {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving)
    {
        this.beschrijving = beschrijving;
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
