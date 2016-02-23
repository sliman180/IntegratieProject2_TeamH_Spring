package be.kdg.teamh.entities;

import javax.persistence.*;

@Entity
public class SubthemaKaart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Subthema subthema;

    public SubthemaKaart() {
        // JPA Constructor
    }

    public SubthemaKaart(Subthema subthema) {
        this.subthema = subthema;
    }

    public int getId() {
        return id;
    }

    public Subthema getSubthema() {
        return subthema;
    }

    public void setSubthema(Subthema subthema) {
        this.subthema = subthema;
    }
}
