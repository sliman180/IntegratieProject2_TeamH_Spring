package be.kdg.teamh.entities;

import javax.persistence.*;

@Entity
public class Organisatie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String naam;
    private String beschrijving;

    @ManyToOne
    private Gebruiker organisator;

    public Organisatie(String naam, String beschrijving, Gebruiker organisator) {
    }

    public int getId() {
        return id;
    }
}
