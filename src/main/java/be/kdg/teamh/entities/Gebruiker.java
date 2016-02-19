package be.kdg.teamh.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "gebruiker")
public class Gebruiker
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Organisatie> organisaties = new ArrayList<>();

    public Gebruiker()
    {
        // JPA Constructor
    }
}
