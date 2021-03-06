package be.kdg.teamh.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "gebruikers")
public class Gebruiker implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Column(unique = true)
    private String email;

    @NotNull
    @Column(unique = true)
    private String gebruikersnaam;

    @NotNull
    private String wachtwoord;

    private String voornaam;
    private String familienaam;
    private String telefoon;

    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "gebruiker")
    private List<Organisatie> organisaties = new ArrayList<>();

    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "gebruiker")
    private List<Hoofdthema> hoofdthemas = new ArrayList<>();

    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "gebruiker")
    private List<Cirkelsessie> cirkelsessies = new ArrayList<>();

    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "gebruiker")
    private List<Subthema> subthemas = new ArrayList<>();

    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "gebruiker")
    private List<Kaart> kaarten = new ArrayList<>();

    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "gebruiker")
    private List<Deelname> deelnames = new ArrayList<>();

    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "gebruiker")
    private List<Commentaar> commentaren = new ArrayList<>();

    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "gebruiker")
    private List<Bericht> berichten = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Rol> rollen = new ArrayList<>();

    public Gebruiker()
    {
        //
    }

    public Gebruiker(String email, String gebruikersnaam, String wachtwoord)
    {
        this.email = email;
        this.gebruikersnaam = gebruikersnaam;
        this.wachtwoord = wachtwoord;
    }

    public Gebruiker(String email, String gebruikersnaam, String wachtwoord, List<Rol> rollen)
    {
        this.email = email;
        this.gebruikersnaam = gebruikersnaam;
        this.wachtwoord = wachtwoord;
        this.rollen = rollen;
    }

    public int getId()
    {
        return id;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getGebruikersnaam()
    {
        return gebruikersnaam;
    }

    public void setGebruikersnaam(String gebruikersnaam)
    {
        this.gebruikersnaam = gebruikersnaam;
    }

    @JsonIgnore
    public String getWachtwoord()
    {
        return wachtwoord;
    }

    public void setWachtwoord(String wachtwoord)
    {
        this.wachtwoord = wachtwoord;
    }

    public String getVoornaam()
    {
        return voornaam;
    }

    public void setVoornaam(String voornaam)
    {
        this.voornaam = voornaam;
    }

    public String getFamilienaam()
    {
        return familienaam;
    }

    public void setFamilienaam(String familienaam)
    {
        this.familienaam = familienaam;
    }

    public String getTelefoon()
    {
        return telefoon;
    }

    public void setTelefoon(String telefoon)
    {
        this.telefoon = telefoon;
    }

    public List<Organisatie> getOrganisaties()
    {
        return organisaties;
    }

    public void setOrganisaties(List<Organisatie> organisaties)
    {
        this.organisaties = organisaties;
    }

    public void addOrganisatie(Organisatie organisatie)
    {
        this.organisaties.add(organisatie);
    }

    public List<Hoofdthema> getHoofdthemas()
    {
        return hoofdthemas;
    }

    public void setHoofdthemas(List<Hoofdthema> hoofdthemas)
    {
        this.hoofdthemas = hoofdthemas;
    }

    public void addHoofdthema(Hoofdthema hoofdthema)
    {
        this.hoofdthemas.add(hoofdthema);
    }

    public List<Cirkelsessie> getCirkelsessies()
    {
        return cirkelsessies;
    }

    public void setCirkelsessies(List<Cirkelsessie> cirkelsessies)
    {
        this.cirkelsessies = cirkelsessies;
    }

    public void addCirkelsessie(Cirkelsessie cirkelsessie)
    {
        this.cirkelsessies.add(cirkelsessie);
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

    public List<Kaart> getKaarten()
    {
        return kaarten;
    }

    public void setKaarten(List<Kaart> kaarten)
    {
        this.kaarten = kaarten;
    }

    public void addKaart(Kaart kaart)
    {
        this.kaarten.add(kaart);
    }

    public List<Deelname> getDeelnames()
    {
        return deelnames;
    }

    public void setDeelnames(List<Deelname> deelnames)
    {
        this.deelnames = deelnames;
    }

    public void addDeelname(Deelname deelname)
    {
        this.deelnames.add(deelname);
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

    public List<Bericht> getBerichten()
    {
        return berichten;
    }

    public void setBerichten(List<Bericht> berichten)
    {
        this.berichten = berichten;
    }

    public void addBericht(Bericht bericht)
    {
        this.berichten.add(bericht);
    }

    public List<Rol> getRollen()
    {
        return rollen;
    }

    public void setRollen(List<Rol> rollen)
    {
        this.rollen = rollen;
    }

    public void addRol(Rol rol)
    {
        this.rollen.add(rol);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }

        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        Gebruiker gebruiker = (Gebruiker) o;

        return gebruikersnaam.equals(gebruiker.gebruikersnaam) && wachtwoord.equals(gebruiker.wachtwoord);
    }

    @Override
    public int hashCode()
    {
        int result = gebruikersnaam.hashCode();

        result = 31 * result + wachtwoord.hashCode();

        return result;
    }
}
