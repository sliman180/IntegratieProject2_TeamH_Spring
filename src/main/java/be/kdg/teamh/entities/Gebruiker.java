package be.kdg.teamh.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "gebruikers")
public class Gebruiker implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String gebruikersnaam;

    @NotNull
    private String wachtwoord;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Hoofdthema> hoofdthemas = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Kaart> kaarten = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany
    private List<Rol> rollen = new ArrayList<>();

    public Gebruiker()
    {
        // JPA Constructor
    }

    public Gebruiker(String gebruikersnaam, String wachtwoord)
    {
        this.gebruikersnaam = gebruikersnaam;
        this.wachtwoord = wachtwoord;
    }

    public Gebruiker(String gebruikersnaam, String wachtwoord, List<Rol> rollen)
    {
        this.gebruikersnaam = gebruikersnaam;
        this.wachtwoord = wachtwoord;
        this.rollen = rollen;
    }

    public String getGebruikersnaam()
    {
        return gebruikersnaam;
    }

    public String getWachtwoord()
    {
        return wachtwoord;
    }

    public void setWachtwoord(String wachtwoord)
    {
        this.wachtwoord = wachtwoord;
    }

    public List<Rol> getRollen()
    {
        return rollen;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }

        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        Gebruiker gebruiker = (Gebruiker) o;

        return gebruikersnaam.equals(gebruiker.gebruikersnaam) && wachtwoord.equals(gebruiker.wachtwoord);

    }

    @Override
    public int hashCode()
    {
        int result = gebruikersnaam.hashCode();
        result = 31 * result + wachtwoord.hashCode();
        return result;
    }
}
