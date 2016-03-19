package be.kdg.teamh.dtos.response;

import be.kdg.teamh.entities.Hoofdthema;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrganisatieResponse implements Serializable {
    private int id;
    private String naam;
    private String beschrijving;
    private int gebruiker;
    private List<Hoofdthema> hoofdthemas = new ArrayList<>();

    public OrganisatieResponse() {
        //
    }

    public OrganisatieResponse(int id, String naam, String beschrijving, int gebruiker) {
        this.id = id;
        this.naam = naam;
        this.beschrijving = beschrijving;
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

    public int getGebruiker() {
        return gebruiker;
    }

    public void setGebruiker(int gebruiker) {
        this.gebruiker = gebruiker;
    }

    public List<Hoofdthema> getHoofdthemas() {
        return hoofdthemas;
    }

    public void setHoofdthemas(List<Hoofdthema> hoofdthemas) {
        this.hoofdthemas = hoofdthemas;
    }
}
