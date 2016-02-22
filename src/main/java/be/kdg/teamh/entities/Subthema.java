package be.kdg.teamh.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by S on 22-2-2016.
 */
@Entity
@Table(name = "subthema")
public class Subthema {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Cirkelsessie> cirkelSessies = new ArrayList<>();

    public Subthema() {
    }

    public List<Cirkelsessie> getCirkelSessies() {
        return cirkelSessies;
    }

    public void setCirkelSessies(List<Cirkelsessie> cirkelSessies) {
        this.cirkelSessies = cirkelSessies;
    }
}
