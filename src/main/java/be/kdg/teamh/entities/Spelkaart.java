package be.kdg.teamh.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "spelkaarten")
public class Spelkaart implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private int positie;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Kaart kaart;

    @ManyToOne(cascade = CascadeType.ALL)
    private Cirkelsessie cirkelsessie;

    public Spelkaart()
    {
        //
    }

    public Spelkaart(Kaart kaart, Cirkelsessie cirkelsessie)
    {
        this.positie = 0;
        this.kaart = kaart;
        this.cirkelsessie = cirkelsessie;
    }

    public Spelkaart(Kaart kaart)
    {
        this.positie = 0;
        this.kaart = kaart;
    }


    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public Kaart getKaart()
    {
        return kaart;
    }

    public void setKaart(Kaart kaart)
    {
        this.kaart = kaart;
    }

    public Cirkelsessie getCirkelsessie()
    {
        return cirkelsessie;
    }

    public void setCirkelsessie(Cirkelsessie cirkelsessie)
    {
        this.cirkelsessie = cirkelsessie;
    }

    public int getPositie()
    {
        return positie;
    }

    public void setPositie(int positie)
    {
        this.positie = positie;
    }


}
