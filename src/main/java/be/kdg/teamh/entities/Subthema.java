package be.kdg.teamh.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Subthema
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    private String naam;

    @NotNull
    private String beschrijving;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Hoofdthema hoofdthema;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Cirkelsessie> cirkelsessies;

    @OneToMany(cascade = CascadeType.ALL)
    private List<SubthemaKaart> subthemaKaarten;

    public Subthema()
    {
        // JPA Constructor
    }

    public Subthema(String naam, String beschrijving, Hoofdthema hoofdthema, List<Cirkelsessie> cirkelsessies, List<SubthemaKaart> subthemaKaarten)
    {
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.hoofdthema = hoofdthema;
        this.cirkelsessies = cirkelsessies;
        this.subthemaKaarten = subthemaKaarten;
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

    public String getBeschrijving()
    {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving)
    {
        this.beschrijving = beschrijving;
    }

    public Hoofdthema getHoofdthema()
    {
        return hoofdthema;
    }

    public void setHoofdthema(Hoofdthema hoofdthema)
    {
        this.hoofdthema = hoofdthema;
    }

    public List<Cirkelsessie> getCirkelsessieList()
    {
        return cirkelsessies;
    }

    public void setCirkelsessieList(List<Cirkelsessie> cirkelsessies)
    {
        this.cirkelsessies = cirkelsessies;
    }

    public List<SubthemaKaart> getSubthemaKaarten()
    {
        return subthemaKaarten;
    }

    public void setSubthemaKaarten(List<SubthemaKaart> subthemaKaarten)
    {
        this.subthemaKaarten = subthemaKaarten;
    }
}
