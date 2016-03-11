package be.kdg.teamh.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tags")
public class Tag implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Hoofdthema hoofdthema;


    public Tag() {
        //
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Hoofdthema getHoofdthema() {
        return hoofdthema;
    }

    public void setHoofdthema(Hoofdthema hoofdthema) {
        this.hoofdthema = hoofdthema;
    }
}
