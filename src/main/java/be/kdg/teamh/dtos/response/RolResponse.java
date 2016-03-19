package be.kdg.teamh.dtos.response;

import be.kdg.teamh.entities.Gebruiker;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RolResponse implements Serializable {
    private int id;
    private String naam;
    private List<Gebruiker> gebruikers = new ArrayList<>();

    public RolResponse() {
        //
    }

    public RolResponse(int id, String naam) {
        this.id = id;
        this.naam = naam;
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

    @JsonManagedReference
    public List<Gebruiker> getGebruikers() {
        return gebruikers;
    }

    public void setGebruikers(List<Gebruiker> gebruikers) {
        this.gebruikers = gebruikers;
    }
}
