package be.kdg.teamh.dtos.response;

import be.kdg.teamh.entities.Commentaar;
import be.kdg.teamh.entities.Spelkaart;
import be.kdg.teamh.entities.Subthema;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class KaartResponse implements Serializable
{
    private int id;
    private String tekst;
    private String imageUrl;
    private boolean commentsToelaatbaar;
    private int gebruiker;
    private List<Commentaar> commentaren = new ArrayList<>();
    private List<Spelkaart> spelkaarten = new ArrayList<>();
    private List<Subthema> subthemas = new ArrayList<>();

    public KaartResponse()
    {
        //
    }

    public KaartResponse(int id, String tekst, String imageUrl, boolean commentsToelaatbaar, int gebruiker)
    {
        this.id = id;
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

    public int getGebruiker()
    {
        return gebruiker;
    }

    public void setGebruiker(int gebruiker)
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

    public List<Spelkaart> getSpelkaarten()
    {
        return spelkaarten;
    }

    public void setSpelkaarten(List<Spelkaart> spelkaarten)
    {
        this.spelkaarten = spelkaarten;
    }

    public List<Subthema> getSubthemas()
    {
        return subthemas;
    }

    public void setSubthemas(List<Subthema> subthemas)
    {
        this.subthemas = subthemas;
    }
}
