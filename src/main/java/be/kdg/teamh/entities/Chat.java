package be.kdg.teamh.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chats")
public class Chat
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String naam;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Bericht> berichten = new ArrayList<>();

    @OneToOne(cascade = CascadeType.PERSIST)
    private Cirkelsessie cirkelsessie;


    public Chat(String naam, Cirkelsessie cirkelsessie)
    {
        this.naam = naam;
        this.cirkelsessie = cirkelsessie;
    }

    public Chat()
    {

        //JPA
    }


    public Cirkelsessie getCirkelsessie()
    {
        return cirkelsessie;
    }

    public void setCirkelsessie(Cirkelsessie cirkelsessie)
    {
        this.cirkelsessie = cirkelsessie;
    }

    public List<Bericht> getBerichten()
    {
        return berichten;
    }

    public void setBerichten(List<Bericht> berichten)
    {
        this.berichten = berichten;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getNaam()
    {
        return naam;
    }

    public void setNaam(String naam)
    {
        this.naam = naam;
    }

    public void addBericht(Bericht bericht)
    {
        this.berichten.add(bericht);
    }
}
