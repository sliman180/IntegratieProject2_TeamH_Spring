package be.kdg.teamh.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rollen")
public class Rol implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String naam;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "rollen")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private List<Gebruiker> gebruikers = new ArrayList<>();

    public Rol()
    {
        //
    }

    public Rol(String naam)
    {
        this.naam = naam;
    }

    public int getId()
    {
        return id;
    }

    public String getNaam()
    {
        return naam;
    }

    public void setNaam(String naam)
    {
        this.naam = naam;
    }

    public List<Gebruiker> getGebruikers()
    {
        return gebruikers;
    }

    public void setGebruikers(List<Gebruiker> gebruikers)
    {
        this.gebruikers = gebruikers;
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
