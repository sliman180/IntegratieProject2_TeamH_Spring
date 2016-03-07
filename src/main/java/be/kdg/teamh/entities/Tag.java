package be.kdg.teamh.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tags")
public class Tag implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Hoofdthema hoofdthema;

    public Tag()
    {
        //
    }
}
