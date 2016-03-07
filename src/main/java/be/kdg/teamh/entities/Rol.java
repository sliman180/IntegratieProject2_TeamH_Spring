package be.kdg.teamh.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "rollen")
public class Rol
{
    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String naam;

    private String beschrijving;

    @ManyToMany
    private List<Gebruiker> gebruikers;

    public Rol()
    {
        //
    }

    public Rol(String naam, String beschrijving)
    {
        this.naam = naam;
        this.beschrijving = beschrijving;
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

        Rol rol = (Rol) o;

        return naam.equals(rol.naam);
    }

    @Override
    public int hashCode()
    {
        return naam.hashCode();
    }
}
