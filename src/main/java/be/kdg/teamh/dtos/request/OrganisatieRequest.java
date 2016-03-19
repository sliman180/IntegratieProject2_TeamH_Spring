package be.kdg.teamh.dtos.request;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class OrganisatieRequest implements Serializable {
    @NotNull
    private String naam;

    @NotNull
    private String beschrijving;

    @NotNull
    private int gebruiker;

    public OrganisatieRequest() {
        //
    }

    public OrganisatieRequest(String naam, String beschrijving, int gebruiker) {
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.gebruiker = gebruiker;
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
}
