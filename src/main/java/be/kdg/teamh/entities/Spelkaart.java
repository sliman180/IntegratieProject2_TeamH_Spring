package be.kdg.teamh.entities;

import javax.persistence.*;

/**
 * Created by S on 22-2-2016.
 */
@Entity
@Table(name = "spelkaart")
public class Spelkaart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Cirkelsessie cirkelSessie;


    public Spelkaart() {
    }

    public Cirkelsessie getCirkelSessie() {
        return cirkelSessie;
    }

    public void setCirkelSessie(Cirkelsessie cirkelSessie) {
        this.cirkelSessie = cirkelSessie;
    }
}
