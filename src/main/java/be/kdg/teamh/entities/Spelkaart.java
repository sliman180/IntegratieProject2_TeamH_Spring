package be.kdg.teamh.entities;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "spelkaarten")
public class Spelkaart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "KaartId",nullable = true)
    private Kaart kaart;

    @ManyToOne(cascade = CascadeType.ALL)
    private Cirkelsessie cirkelsessie;

    @NotNull
    private int positie;

    public Spelkaart() {
        //JPA
    }

    public Spelkaart(Kaart kaart, Cirkelsessie cirkelsessie) {
        this.kaart = kaart;
        this.positie = 0;
        this.cirkelsessie = cirkelsessie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Kaart getKaart() {
        return kaart;
    }

    public void setKaart(Kaart kaart) {
        this.kaart = kaart;
    }

    public Cirkelsessie getCirkelsessie() {
        return cirkelsessie;
    }

    public void setCirkelsessie(Cirkelsessie cirkelsessie) {
        this.cirkelsessie = cirkelsessie;
    }

    public int getPositie() {
        return positie;
    }

    public void setPositie(int positie) {
        this.positie = positie;
    }

}