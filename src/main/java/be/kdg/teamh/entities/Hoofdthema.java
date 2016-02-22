package be.kdg.teamh.entities;

import be.kdg.teamh.entities.Subthema;

import javax.persistence.*;
import java.util.List;

@Entity
public class Hoofdthema {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String naam,beschrijving;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Subthema> subthemaList;

    public Hoofdthema() {
    }

    public Hoofdthema(String naam, String beschrijving, List<Subthema> subthemaList) {
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.subthemaList = subthemaList;
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

    public List<Subthema> getSubthemaList() {
        return subthemaList;
    }

    public void setSubthemaList(List<Subthema> subthemaList) {
        this.subthemaList = subthemaList;
    }

    public void aadSubthema(Subthema subthema){this.subthemaList.add(subthema);}
}
