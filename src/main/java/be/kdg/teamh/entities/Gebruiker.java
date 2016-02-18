package be.kdg.teamh.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "gebruiker")
public class Gebruiker {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    private String naam;

    @NotNull
    private String voornaam;

    @NotNull
    private String wachtwoord;

    @NotNull
    private String email;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Organisatie> organisaties = new ArrayList<>();

    public Gebruiker() {

    }


    public Gebruiker(String naam, String voornaam, String email, String wachtwoord) {

        this.naam = naam;
        this.voornaam = voornaam;
        this.email = email;
        this.wachtwoord = wachtwoord;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }

    public void setWachtwoord(String wachtwoord) {
        this.wachtwoord = wachtwoord;
    }

    public List<Organisatie> getOrganisaties() {
        return organisaties;
    }

    public void setOrganisaties(List<Organisatie> organisaties) {
        this.organisaties = organisaties;
    }
}
