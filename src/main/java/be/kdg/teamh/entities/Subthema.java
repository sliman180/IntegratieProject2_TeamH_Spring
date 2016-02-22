package be.kdg.teamh.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Subthema {

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
    private List<Cirkelsessie> cirkelsessieList;
    @OneToMany(cascade = CascadeType.ALL)
    private List<SubthemaKaart> subthemaKaartList;

    public Subthema() {
    }

    public Subthema(String naam, String beschrijving, Hoofdthema hoofdthema, List<Cirkelsessie> cirkelsessieList, List<SubthemaKaart> subthemaKaartList) {
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.hoofdthema = hoofdthema;
        this.cirkelsessieList = cirkelsessieList;
        this.subthemaKaartList = subthemaKaartList;
    }

    public int getId() {
        return id;
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

    public Hoofdthema getHoofdthema() {
        return hoofdthema;
    }

    public void setHoofdthema(Hoofdthema hoofdthema) {
        this.hoofdthema = hoofdthema;
    }

    public List<Cirkelsessie> getCirkelsessieList() {
        return cirkelsessieList;
    }

    public void setCirkelsessieList(List<Cirkelsessie> cirkelsessieList) {
        this.cirkelsessieList = cirkelsessieList;
    }

    public List<SubthemaKaart> getSubthemaKaartList() {
        return subthemaKaartList;
    }

    public void setSubthemaKaartList(List<SubthemaKaart> subthemaKaartList) {
        this.subthemaKaartList = subthemaKaartList;
    }
}
