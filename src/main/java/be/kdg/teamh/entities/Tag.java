package be.kdg.teamh.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "tags")
public class Tag implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String naam;

    @ManyToOne
    private Hoofdthema hoofdthema;

    public Tag()
    {
        //
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

    public Hoofdthema getHoofdthema()
    {
        return hoofdthema;
    }

    public void setHoofdthema(Hoofdthema hoofdthema)
    {
        this.hoofdthema = hoofdthema;
    }
}
