package be.kdg.teamh.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Gebruiker {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;



    public Gebruiker(String naam, String voornaam, String s, String wachtwoord) {

    }
}
