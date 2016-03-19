package be.kdg.teamh.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "commentaren")
public class Commentaar implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String tekst;

    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime datum;

    @ManyToOne
    @JsonManagedReference
    private Gebruiker gebruiker;

    @ManyToOne
    @JsonManagedReference
    private Kaart kaart;

    public Commentaar() {
        //
    }

    public Commentaar(String tekst, DateTime datum, Gebruiker gebruiker, Kaart kaart) {
        this.tekst = tekst;
        this.datum = datum;
        this.gebruiker = gebruiker;
        this.kaart = kaart;
    }

    public int getId() {
        return id;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public DateTime getDatum() {
        return datum;
    }

    public void setDatum(DateTime datum) {
        this.datum = datum;
    }

    public Gebruiker getGebruiker() {
        return gebruiker;
    }

    public void setGebruiker(Gebruiker gebruiker) {
        this.gebruiker = gebruiker;
    }

    public Kaart getKaart() {
        return kaart;
    }

    public void setKaart(Kaart kaart) {
        this.kaart = kaart;
    }
}
