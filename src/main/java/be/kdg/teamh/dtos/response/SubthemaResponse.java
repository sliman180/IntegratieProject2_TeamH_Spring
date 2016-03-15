package be.kdg.teamh.dtos.response;

import be.kdg.teamh.entities.Cirkelsessie;
import be.kdg.teamh.entities.Kaart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SubthemaResponse implements Serializable
{
    private int id;
    private String naam;
    private String beschrijving;
    private int hoofdthema;
    private List<Cirkelsessie> cirkelsessies = new ArrayList<>();
    private List<Kaart> kaarten = new ArrayList<>();

    public SubthemaResponse()
    {
        //
    }

    public SubthemaResponse(int id, String naam, String beschrijving, int hoofdthema)
    {
        this.id = id;
        this.naam = naam;
        this.beschrijving = beschrijving;
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

    public List<Cirkelsessie> getCirkelsessies()
    {
        return cirkelsessies;
    }

    public void setCirkelsessies(List<Cirkelsessie> cirkelsessies)
    {
        this.cirkelsessies = cirkelsessies;
    }

    public List<Kaart> getKaarten()
    {
        return kaarten;
    }

    public void setKaarten(List<Kaart> kaarten)
    {
        this.kaarten = kaarten;
    }
}
