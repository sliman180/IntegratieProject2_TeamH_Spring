package be.kdg.teamh.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "gebruikers")
public class Gebruiker
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Hoofdthema> hoofdthemas = new ArrayList<>();

    public Gebruiker() {
    }
}