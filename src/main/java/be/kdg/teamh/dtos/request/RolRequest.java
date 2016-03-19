package be.kdg.teamh.dtos.request;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class RolRequest implements Serializable
{
    @NotNull
    private String naam;

    public RolRequest()
    {
        //
    }

    public RolRequest(String naam)
    {
        this.naam = naam;
    }

    public String getNaam()
    {
        return naam;
    }

    public void setNaam(String naam)
    {
        this.naam = naam;
    }
}
