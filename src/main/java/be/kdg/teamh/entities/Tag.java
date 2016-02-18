package be.kdg.teamh.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tagId")
    private int id;

    @NotNull
    private String tekst;

    @NotNull
    private String beschrijving;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Hoofdthema hoofdthema;

    public Tag() {
    }

    public Tag(String beschrijving, String tekst,Hoofdthema hoofdthema) {
        this.beschrijving = beschrijving;
        this.tekst = tekst;
        this.hoofdthema = hoofdthema;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public Hoofdthema getHoofdthema() {
        return hoofdthema;
    }

    public void setHoofdthema(Hoofdthema hoofdthema) {
        this.hoofdthema = hoofdthema;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }
}
