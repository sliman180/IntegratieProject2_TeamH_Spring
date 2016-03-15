package be.kdg.teamh.dtos.response;

import java.io.Serializable;

public class TagResponse implements Serializable
{
    private int id;
    private String naam;
    private int hoofdthema;

    public TagResponse()
    {
        //
    }

    public TagResponse(int id, String naam, int hoofdthema)
    {
        this.id = id;
        this.naam = naam;
        this.hoofdthema = hoofdthema;
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

    public int getHoofdthema()
    {
        return hoofdthema;
    }

    public void setHoofdthema(int hoofdthema)
    {
        this.hoofdthema = hoofdthema;
    }
}
