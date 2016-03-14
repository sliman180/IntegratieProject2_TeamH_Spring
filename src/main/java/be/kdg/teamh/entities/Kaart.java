package be.kdg.teamh.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "kaarten")
public class Kaart implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String tekst;

    @NotNull
    private String imageUrl;

    @NotNull
    private boolean commentsToelaatbaar;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "gebruikersnaam")
    private Gebruiker gebruiker;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, scope = Subthema.class, property = "id")
    private Subthema subthema;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Commentaar> commentaren = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private List<Spelkaart> spelkaarten = new ArrayList<>();

    public Kaart() {
        //
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

    public List<Commentaar> getCommentaren() {
        return commentaren;
    }

    public void setCommentaren(List<Commentaar> commentaren) {
        this.commentaren = commentaren;
    }

    public void addComment(Commentaar commentaar) {
        this.commentaren.add(commentaar);
    }

    public List<Spelkaart> getSpelkaarten() {
        return spelkaarten;
    }

    public void setSpelkaarten(List<Spelkaart> spelkaarten) {
        this.spelkaarten = spelkaarten;
    }

    public void addSpelkaart(Spelkaart spelkaart) {
        this.spelkaarten.add(spelkaart);
    }

    public Subthema getSubthema() {
        return subthema;
    }

    public void setSubthema(Subthema subthema) {
        this.subthema = subthema;
    }
}
