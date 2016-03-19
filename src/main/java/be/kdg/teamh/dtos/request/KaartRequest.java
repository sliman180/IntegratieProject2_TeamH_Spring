package be.kdg.teamh.dtos.request;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class KaartRequest implements Serializable {
    @NotNull
    private String tekst;

    @NotNull
    private String imageUrl;

    @NotNull
    private boolean commentsToelaatbaar;

    @NotNull
    private int gebruiker;

    public KaartRequest() {
        //
    }

    public KaartRequest(String tekst, String imageUrl, boolean commentsToelaatbaar, int gebruiker) {
        this.tekst = tekst;
        this.imageUrl = imageUrl;
        this.commentsToelaatbaar = commentsToelaatbaar;
        this.gebruiker = gebruiker;
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

    public int getGebruiker() {
        return gebruiker;
    }

    public void setGebruiker(int gebruiker) {
        this.gebruiker = gebruiker;
    }
}
