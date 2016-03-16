package be.kdg.teamh.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "kaarten")
public class Kaart implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String tekst;

    @NotNull
    private String imageUrl;

    @NotNull
    private boolean commentsToelaatbaar;

    @ManyToOne
    @JsonBackReference
    private Gebruiker gebruiker;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "kaart")
    private List<Commentaar> commentaren = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "kaart")
    private List<Spelkaart> spelkaarten = new ArrayList<>();

    @JsonBackReference
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Subthema> subthemas = new ArrayList<>();

    public Kaart()
    {
        //
    }

    public Kaart(String tekst, String imageUrl, boolean commentsToelaatbaar, Gebruiker gebruiker)
    {
        this.tekst = tekst;
        this.imageUrl = imageUrl;
        this.commentsToelaatbaar = commentsToelaatbaar;
        this.gebruiker = gebruiker;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getTekst()
    {
        return tekst;
    }

    public void setTekst(String tekst)
    {
        this.tekst = tekst;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public boolean isCommentsToelaatbaar()
    {
        return commentsToelaatbaar;
    }

    public void setCommentsToelaatbaar(boolean commentsToelaatbaar)
    {
        this.commentsToelaatbaar = commentsToelaatbaar;
    }

    public Gebruiker getGebruiker()
    {
        return gebruiker;
    }

    public void setGebruiker(Gebruiker gebruiker)
    {
        this.gebruiker = gebruiker;
    }

    public List<Commentaar> getCommentaren()
    {
        return commentaren;
    }

    public void setCommentaren(List<Commentaar> commentaren)
    {
        this.commentaren = commentaren;
    }

    public void addCommentaar(Commentaar commentaar)
    {
        this.commentaren.add(commentaar);
    }

    public List<Spelkaart> getSpelkaarten()
    {
        return spelkaarten;
    }

    public void setSpelkaarten(List<Spelkaart> spelkaarten)
    {
        this.spelkaarten = spelkaarten;
    }

    public void addSpelkaart(Spelkaart spelkaart)
    {
        this.spelkaarten.add(spelkaart);
    }

    public List<Subthema> getSubthemas()
    {
        return subthemas;
    }

    public void setSubthemas(List<Subthema> subthemas)
    {
        this.subthemas = subthemas;
    }

    public void addSubthema(Subthema subthema)
    {
        this.subthemas.add(subthema);
    }
}
