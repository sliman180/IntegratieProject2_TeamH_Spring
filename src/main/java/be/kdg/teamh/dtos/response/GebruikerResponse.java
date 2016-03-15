package be.kdg.teamh.dtos.response;

import be.kdg.teamh.entities.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GebruikerResponse implements Serializable
{
    private int id;
    private String gebruikersnaam;
    private List<Organisatie> organisaties = new ArrayList<>();
    private List<Hoofdthema> hoofdthemas = new ArrayList<>();
    private List<Subthema> subthemas = new ArrayList<>();
    private List<Cirkelsessie> cirkelsessies = new ArrayList<>();
    private List<Kaart> kaarten = new ArrayList<>();
    private List<Deelname> deelnames = new ArrayList<>();
    private List<Commentaar> commentaren = new ArrayList<>();
    private List<Bericht> berichten = new ArrayList<>();
    private List<Rol> rollen = new ArrayList<>();

    public GebruikerResponse()
    {
        //
    }

    public GebruikerResponse(int id, String gebruikersnaam)
    {
        this.id = id;
        this.gebruikersnaam = gebruikersnaam;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getGebruikersnaam()
    {
        return gebruikersnaam;
    }

    public void setGebruikersnaam(String gebruikersnaam)
    {
        this.gebruikersnaam = gebruikersnaam;
    }

    public List<Organisatie> getOrganisaties()
    {
        return organisaties;
    }

    public void setOrganisaties(List<Organisatie> organisaties)
    {
        this.organisaties = organisaties;
    }

    public List<Hoofdthema> getHoofdthemas()
    {
        return hoofdthemas;
    }

    public void setHoofdthemas(List<Hoofdthema> hoofdthemas)
    {
        this.hoofdthemas = hoofdthemas;
    }

    public List<Subthema> getSubthemas()
    {
        return subthemas;
    }

    public void setSubthemas(List<Subthema> subthemas)
    {
        this.subthemas = subthemas;
    }

    public List<Cirkelsessie> getCirkelsessies()
    {
        return cirkelsessies;
    }

    public void setCirkelsessies(List<Cirkelsessie> cirkelsessies)
    {
        this.cirkelsessies = cirkelsessies;
    }

    public List<Kaart> getKaarten()
    {
        return kaarten;
    }

    public void setKaarten(List<Kaart> kaarten)
    {
        this.kaarten = kaarten;
    }

    public List<Deelname> getDeelnames()
    {
        return deelnames;
    }

    public void setDeelnames(List<Deelname> deelnames)
    {
        this.deelnames = deelnames;
    }

    public List<Commentaar> getCommentaren()
    {
        return commentaren;
    }

    public void setCommentaren(List<Commentaar> commentaren)
    {
        this.commentaren = commentaren;
    }

    public List<Bericht> getBerichten()
    {
        return berichten;
    }

    public void setBerichten(List<Bericht> berichten)
    {
        this.berichten = berichten;
    }

    @JsonBackReference
    public List<Rol> getRollen()
    {
        return rollen;
    }

    public void setRollen(List<Rol> rollen)
    {
        this.rollen = rollen;
    }
}
