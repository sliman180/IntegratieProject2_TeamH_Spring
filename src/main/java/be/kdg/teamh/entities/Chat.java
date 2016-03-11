package be.kdg.teamh.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chats")

public class Chat implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String naam;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Bericht> berichten = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, scope = Cirkelsessie.class, property = "id")
    private Cirkelsessie cirkelsessie;

    public Chat() {
        //
    }

    public Chat(String naam) {
        this.naam = naam;
    }

    public Chat(String naam, Cirkelsessie cirkelsessie) {
        this.naam = naam;
        this.cirkelsessie = cirkelsessie;
    }

    public Cirkelsessie getCirkelsessie() {
        return cirkelsessie;
    }

    public void setCirkelsessie(Cirkelsessie cirkelsessie) {
        this.cirkelsessie = cirkelsessie;
    }

    public List<Bericht> getBerichten() {
        return berichten;
    }

    public void setBerichten(List<Bericht> berichten) {
        this.berichten = berichten;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public void addBericht(Bericht bericht) {
        this.berichten.add(bericht);
    }


}
