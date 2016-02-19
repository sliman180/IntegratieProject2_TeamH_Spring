package be.kdg.teamh.entities;

import javax.persistence.*;
import java.util.List;

/**
 * Created by lollik on 18/02/2016.
 */
@Entity
public class Hoofdthema {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String naam, beschrijving;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Subthema> subthemaList;

    public Hoofdthema() {
    }

    public Hoofdthema(String naam, String beschrijving) {
        this.naam = naam;
        this.beschrijving = beschrijving;
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
}
