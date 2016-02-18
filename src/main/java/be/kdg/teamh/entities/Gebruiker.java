package be.kdg.teamh.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Entity
@Table(name = "gebruikers")
public class Gebruiker
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "gebruikerId")
    private int id;

    private String naam;

    private String voornaam;





    @OneToMany(cascade = CascadeType.ALL)
    private List<Hoofdthema> hoofdthemas = new ArrayList<>();

    public Gebruiker()
    {
    }
}
