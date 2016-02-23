package be.kdg.teamh.entities;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "kaarten")
public class Kaart {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    private String tekst;

    @NotNull
    private String imageUrl;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Gebruiker gebruiker;

    private boolean commentsToelaatbaar;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();


    public Kaart() {

        //JPA
    }

    public Kaart(String tekst, String imageUrl, boolean commentsToelaatbaar, Gebruiker gebruiker) {
        this.tekst = tekst;
        this.imageUrl = imageUrl;
        this.commentsToelaatbaar = commentsToelaatbaar;
        this.gebruiker = gebruiker;

    }

    public Gebruiker getGebruiker() {
        return gebruiker;
    }

    public void setGebruiker(Gebruiker gebruiker) {
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }
}
