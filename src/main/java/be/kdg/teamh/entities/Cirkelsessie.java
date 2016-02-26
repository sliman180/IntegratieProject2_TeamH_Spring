package be.kdg.teamh.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Cirkelsessie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    private String naam;

/*  TODO:datum toevoegen, momenteel geeft 2016-02-23 16:34:41.007  WARN 1064 --- [           main] .w.s.m.s.DefaultHandlerExceptionResolver : Failed to read HTTP message: org.springframework.http.converter.HttpMessageNotReadableException: Could not read document: No suitable constructor found for type [simple type, class java.time.LocalDateTime]: can not instantiate from JSON object (missing default constructor or creator, or perhaps need to add/enable type information?)
    stackoverflow: http://stackoverflow.com/questions/27952472/serialize-deserialize-java-8-java-time-with-jackson-json-mapper

    @NotNull
    private LocalDateTime datum;*/

    @NotNull
    private int maxAantalKaarten;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Subthema subthema;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Gebruiker gebruiker;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Deelname> deelnames = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Spelkaart> spelkaarten = new ArrayList<>();

    public Cirkelsessie() {
        // JPA Constructor
    }

    public Cirkelsessie(String naam, /*LocalDateTime datum,*/ Integer maxAantalKaarten, Subthema subthema, Gebruiker gebruiker) {
        this.naam = naam;
//        this.datum = datum;
        this.maxAantalKaarten = maxAantalKaarten;
        this.subthema = subthema;
        this.gebruiker = gebruiker;
    }

    //constructor voor het clonen van een sessie
    public Cirkelsessie(String naam, int maxAantalKaarten, Subthema subthema, Gebruiker gebruiker) {
        this.naam = naam;
        this.maxAantalKaarten = maxAantalKaarten;
        this.subthema = subthema;
        this.gebruiker = gebruiker;
    }

    public int getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

/*    public LocalDateTime getDatum() {
        return datum;
    }

    public void setDatum(LocalDateTime datum) {
        this.datum = datum;
    }*/

    public int getMaxAantalKaarten() {
        return maxAantalKaarten;
    }

    public void setMaxAantalKaarten(int maxAantalKaarten) {
        this.maxAantalKaarten = maxAantalKaarten;
    }

    public Subthema getSubthema() {
        return subthema;
    }

    public void setSubthema(Subthema subthema) {
        this.subthema = subthema;
    }

    public Gebruiker getGebruiker() {
        return gebruiker;
    }

    public void setGebruiker(Gebruiker gebruiker) {
        this.gebruiker = gebruiker;
    }

    public List<Deelname> getDeelnames() {
        return deelnames;
    }

    public void setDeelnames(List<Deelname> deelnames) {
        this.deelnames = deelnames;
    }

    public void addDeelname(Deelname deelname){this.deelnames.add(deelname);}

    public List<Spelkaart> getSpelkaarten() {
        return spelkaarten;
    }

    public void setSpelkaarten(List<Spelkaart> spelkaarten) {
        this.spelkaarten = spelkaarten;
    }

    public void addSpelKaart(Spelkaart spelkaart){
        this.spelkaarten.add(spelkaart);
    }

    public void cloneDeelnames(List<Deelname> deelnames){
        for (Deelname d:deelnames) {
            d.setCirkelSessie(this);
            this.deelnames.add(d);
        }
    }
}
