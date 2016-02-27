package be.kdg.teamh.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "spelkaarten")
public class Spelkaart implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Cirkelsessie cirkelSessie;

    public Spelkaart()
    {
    }

    public Cirkelsessie getCirkelSessie()
    {
        return cirkelSessie;
    }

    public void setCirkelSessie(Cirkelsessie cirkelSessie)
    {
        this.cirkelSessie = cirkelSessie;
    }
}
