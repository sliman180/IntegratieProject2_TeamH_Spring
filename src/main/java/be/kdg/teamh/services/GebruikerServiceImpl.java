package be.kdg.teamh.services;

import be.kdg.teamh.dtos.request.GebruikerRequest;
import be.kdg.teamh.dtos.request.LoginRequest;
import be.kdg.teamh.dtos.request.RegistratieRequest;
import be.kdg.teamh.entities.*;
import be.kdg.teamh.exceptions.gebruiker.GebruikerBestaatAl;
import be.kdg.teamh.exceptions.gebruiker.OngeldigeGegevens;
import be.kdg.teamh.exceptions.gebruiker.WachtwoordenKomenNietOvereen;
import be.kdg.teamh.exceptions.gebruiker.GebruikerNietGevonden;
import be.kdg.teamh.exceptions.rol.RolNietGevonden;
import be.kdg.teamh.repositories.GebruikerRepository;
import be.kdg.teamh.repositories.RolRepository;
import be.kdg.teamh.services.contracts.GebruikerService;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@Transactional
public class GebruikerServiceImpl implements GebruikerService
{
    private GebruikerRepository repository;
    private RolRepository rollen;

    @Autowired
    public GebruikerServiceImpl(GebruikerRepository repository, RolRepository rollen)
    {
        this.repository = repository;
        this.rollen = rollen;
    }

    @Override
    public List<Gebruiker> all()
    {
        return repository.findAll();
    }

    @Override
    public void create(GebruikerRequest dto) throws RolNietGevonden
    {
        Rol rol = rollen.findByNaam("user");

        if (rol == null)
        {
            throw new RolNietGevonden();
        }

        Gebruiker gebruiker = new Gebruiker();

        gebruiker.setEmail(dto.getEmail());
        gebruiker.setGebruikersnaam(dto.getGebruikersnaam());
        gebruiker.setWachtwoord(Hashing.sha256().hashString(dto.getWachtwoord(), StandardCharsets.UTF_8).toString());
        gebruiker.setVoornaam(dto.getVoornaam());
        gebruiker.setFamilienaam(dto.getFamilienaam());
        gebruiker.setTelefoon(dto.getTelefoon());
        gebruiker.addRol(rol);

        repository.save(gebruiker);
    }

    @Override
    public void register(RegistratieRequest dto) throws RolNietGevonden, WachtwoordenKomenNietOvereen
    {
        if (!dto.getWachtwoord().equalsIgnoreCase(dto.getConfirmatie()))
        {
            throw new WachtwoordenKomenNietOvereen();
        }

        if (repository.findByGebruikersnaam(dto.getGebruikersnaam()) != null)
        {
            throw new GebruikerBestaatAl();
        }

        GebruikerRequest gebruiker = new GebruikerRequest();

        gebruiker.setEmail(dto.getEmail());
        gebruiker.setGebruikersnaam(dto.getGebruikersnaam());
        gebruiker.setWachtwoord(dto.getWachtwoord());

        create(gebruiker);
    }

    @Override
    public Gebruiker find(int id) throws GebruikerNietGevonden
    {
        Gebruiker gebruiker = repository.findOne(id);

        if (gebruiker == null)
        {
            throw new GebruikerNietGevonden();
        }

        return gebruiker;
    }

    @Override
    public Gebruiker findByLogin(LoginRequest login) throws GebruikerNietGevonden, OngeldigeGegevens
    {
        Gebruiker gebruiker = repository.findByGebruikersnaam(login.getGebruikersnaam());

        if (gebruiker == null)
        {
            throw new GebruikerNietGevonden();
        }

        if (!Hashing.sha256().hashString(login.getWachtwoord(), StandardCharsets.UTF_8).toString().equals(gebruiker.getWachtwoord()))
        {
            throw new OngeldigeGegevens();
        }

        return gebruiker;
    }

    @Override
    public void update(int id, GebruikerRequest dto) throws GebruikerNietGevonden
    {
        Gebruiker gebruiker = repository.findOne(id);

        if (gebruiker == null)
        {
            throw new GebruikerNietGevonden();
        }

        gebruiker.setEmail(dto.getEmail());
        gebruiker.setGebruikersnaam(dto.getGebruikersnaam());
        gebruiker.setVoornaam(dto.getVoornaam());
        gebruiker.setFamilienaam(dto.getFamilienaam());
        gebruiker.setTelefoon(dto.getTelefoon());

        if (!dto.getWachtwoord().isEmpty())
        {
            if (!dto.getWachtwoord().equals(gebruiker.getWachtwoord()))
            {
                gebruiker.setWachtwoord(Hashing.sha256().hashString(dto.getWachtwoord(), StandardCharsets.UTF_8).toString());
            }
        }

        repository.saveAndFlush(gebruiker);
    }

    @Override
    public void delete(int id) throws GebruikerNietGevonden
    {
        Gebruiker gebruiker = repository.findOne(id);

        if (gebruiker == null)
        {
            throw new GebruikerNietGevonden();
        }

        repository.delete(gebruiker);
    }

    @Override
    public List<Organisatie> getOrganisaties(int id) throws GebruikerNietGevonden
    {
        Gebruiker gebruiker = repository.findOne(id);

        if (gebruiker == null)
        {
            throw new GebruikerNietGevonden();
        }

        return gebruiker.getOrganisaties();
    }

    @Override
    public List<Cirkelsessie> getCirkelsessies(int id) throws GebruikerNietGevonden
    {
        Gebruiker gebruiker = repository.findOne(id);

        if (gebruiker == null)
        {
            throw new GebruikerNietGevonden();
        }

        return gebruiker.getCirkelsessies();
    }

    @Override
    public List<Deelname> getDeelnames(int id) throws GebruikerNietGevonden
    {
        Gebruiker gebruiker = repository.findOne(id);

        if (gebruiker == null)
        {
            throw new GebruikerNietGevonden();
        }

        return gebruiker.getDeelnames();
    }

    @Override
    public List<Hoofdthema> getHoofdthemas(int id) throws GebruikerNietGevonden
    {
        Gebruiker gebruiker = repository.findOne(id);

        if (gebruiker == null)
        {
            throw new GebruikerNietGevonden();
        }

        return gebruiker.getHoofdthemas();
    }

    @Override
    public List<Subthema> getSubthemas(int id) throws GebruikerNietGevonden
    {
        Gebruiker gebruiker = repository.findOne(id);

        if (gebruiker == null)
        {
            throw new GebruikerNietGevonden();
        }

        return gebruiker.getSubthemas();
    }
}
