package be.kdg.teamh.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

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
    private String gebruikersnaam;

    @NotNull
    private String wachtwoord;

    @NotNull
    private String email;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Hoofdthema> hoofdthemas = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Kaart> kaarten = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Deelname> deelnames = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Commentaar> commentaren = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Organisatie> organisaties = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private List<Bericht> berichten = new ArrayList<>();

    @ManyToMany
    private List<Rol> rollen = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private List<Cirkelsessie> cirkelsessies;

    public Gebruiker()
    {
        //
    }

    public Gebruiker(String gebruikersnaam, String wachtwoord)
    {
        this.gebruikersnaam = gebruikersnaam;
        this.wachtwoord = wachtwoord;
    }

    public Gebruiker(String gebruikersnaam, String wachtwoord, String email) {
        this.gebruikersnaam = gebruikersnaam;
        this.wachtwoord = wachtwoord;
        this.email = email;
    }

    public Gebruiker(String gebruikersnaam, String wachtwoord, List<Rol> rollen)
    {
        this.gebruikersnaam = gebruikersnaam;
        this.wachtwoord = wachtwoord;
        this.rollen = rollen;
    }

    public void addCirkelsessie(Cirkelsessie cirkelsessie) {
        this.cirkelsessies.add(cirkelsessie);
    }
    public int getId()
    {
        return id;
    }

    public void setId(int id) {
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

    public String getWachtwoord()
    {
        return wachtwoord;
    }

    public void setWachtwoord(String wachtwoord)
    {
        this.wachtwoord = wachtwoord;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Hoofdthema> getHoofdthemas()
    {
        return hoofdthemas;
    }

    public void setHoofdthemas(List<Hoofdthema> hoofdthemas)
    {
        this.hoofdthemas = hoofdthemas;
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

    public List<Rol> getRollen()
    {
        return rollen;
    }

    public void setRollen(List<Rol> rollen)
    {
        this.rollen = rollen;
    }

    public List<Cirkelsessie> getCirkelsessies() {
        return cirkelsessies;
    }

    public void setCirkelsessise(List<Cirkelsessie> cirkelsessies) {
        this.cirkelsessies = cirkelsessies;
    }


    public List<Organisatie> getOrganisaties() {
        return organisaties;
    }

    public void setOrganisaties(List<Organisatie> organisaties) {
        this.organisaties = organisaties;
    }

    public void setCirkelsessies(List<Cirkelsessie> cirkelsessies) {
        this.cirkelsessies = cirkelsessies;
    }

    public void addRol(Rol rol){
        rollen.add(rol);
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
