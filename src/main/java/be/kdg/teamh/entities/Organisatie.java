package be.kdg.teamh.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "organisaties")
public class Organisatie
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "organisatieID")
    private int id;

    @NotNull
    private String naam;

    @NotNull
    private String beschrijving;

    @OneToOne(cascade = CascadeType.ALL)
    private Hoofdthema hoofdthema;

    public Organisatie()
    {
    }

    public Organisatie(String naam, String beschrijving) {
        this.naam = naam;
        this.beschrijving = beschrijving;
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
}
