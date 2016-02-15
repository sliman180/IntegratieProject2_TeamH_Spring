package be.kdg.teamh.entities;

import javax.persistence.*;

@Entity
@Table(name = "organisaties")
public class Organisatie
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    private Hoofdthema hoofdthema;

    public Organisatie()
    {
    }
}
