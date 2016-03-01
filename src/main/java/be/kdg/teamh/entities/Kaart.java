package be.kdg.teamh.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "kaarten")
public class Kaart implements Serializable {
    @Id
    @Column(name = "KaartId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String tekst;

    @NotNull
    private String imageUrl;

    @NotNull
    private boolean commentsToelaatbaar;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Gebruiker gebruiker;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Commentaar> commentaars = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Subthema> subthemas = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Spelkaart> spelkaarten = new ArrayList<>();


    public Kaart() {
        //JPA Constructor
    }

    public Kaart(String tekst, String imageUrl, boolean commentsToelaatbaar, Gebruiker gebruiker) {
        this.tekst = tekst;
        this.imageUrl = imageUrl;
        this.commentsToelaatbaar = commentsToelaatbaar;
        this.gebruiker = gebruiker;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isCommentsToelaatbaar() {
        return commentsToelaatbaar;
    }

    public void setCommentsToelaatbaar(boolean commentsToelaatbaar) {
        this.commentsToelaatbaar = commentsToelaatbaar;
    }

    public Gebruiker getGebruiker() {
        return gebruiker;
    }

    public void setGebruiker(Gebruiker gebruiker) {
        this.gebruiker = gebruiker;
    }

    public List<Commentaar> getCommentaars() {
        return commentaars;
    }

    public void setCommentaars(List<Commentaar> commentaars) {
        this.commentaars = commentaars;
    }

    public void addComment(Commentaar commentaar) {
        this.commentaars.add(commentaar);
    }

    public void addSubthema(Subthema subthema) {
        this.subthemas.add(subthema);
    }

    public List<Subthema> getSubthemas() {
        return subthemas;
    }

    public void setSubthemas(List<Subthema> subthemas) {
        this.subthemas = subthemas;
    }

    public void addSpelkaart(Spelkaart spelkaart) {
        this.spelkaarten.add(spelkaart);
    }

    public List<Spelkaart> getSpelkaarten() {
        return spelkaarten;
    }

    public void setSpelkaarten(List<Spelkaart> spelkaarten) {
        this.spelkaarten = spelkaarten;
    }
}
