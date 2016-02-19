package be.kdg.teamh.entities;

import javax.persistence.*;

@Entity
@Table(name = "tags")
public class Tag
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Hoofdthema hoofdthema;

    public Tag()
    {
        // JPA constructor
    }
}
