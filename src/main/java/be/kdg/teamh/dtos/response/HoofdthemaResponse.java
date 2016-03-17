package be.kdg.teamh.dtos.response;

import be.kdg.teamh.entities.Subthema;
import be.kdg.teamh.entities.Tag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HoofdthemaResponse implements Serializable {
    private int id;
    private String naam;
    private String beschrijving;
    private int organisatie;
    private int gebruiker;
    private List<Subthema> subthemas = new ArrayList<>();
    private List<Tag> tags = new ArrayList<>();

    public HoofdthemaResponse() {
        //
    }

    public HoofdthemaResponse(int id, String naam, String beschrijving, int organisatie, int gebruiker) {
        this.id = id;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.organisatie = organisatie;
        this.gebruiker = gebruiker;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public int getOrganisatie() {
        return organisatie;
    }

    public void setOrganisatie(int organisatie) {
        this.organisatie = organisatie;
    }

    public int getGebruiker() {
        return gebruiker;
    }

    public void setGebruiker(int gebruiker) {
        this.gebruiker = gebruiker;
    }

    public List<Subthema> getSubthemas() {
        return subthemas;
    }

    public void setSubthemas(List<Subthema> subthemas) {
        this.subthemas = subthemas;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
