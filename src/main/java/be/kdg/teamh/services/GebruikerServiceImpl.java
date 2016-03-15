package be.kdg.teamh.services;

import be.kdg.teamh.dtos.request.GebruikerRequest;
import be.kdg.teamh.dtos.request.LoginRequest;
import be.kdg.teamh.dtos.request.RegistratieRequest;
import be.kdg.teamh.dtos.response.CirkelsessieResponse;
import be.kdg.teamh.dtos.response.DeelnameResponse;
import be.kdg.teamh.dtos.response.GebruikerResponse;
import be.kdg.teamh.dtos.response.OrganisatieResponse;
import be.kdg.teamh.entities.*;
import be.kdg.teamh.exceptions.InvalidCredentials;
import be.kdg.teamh.exceptions.PasswordsDoNotMatch;
import be.kdg.teamh.exceptions.notfound.GebruikerNotFound;
import be.kdg.teamh.exceptions.notfound.RolNotFound;
import be.kdg.teamh.repositories.GebruikerRepository;
import be.kdg.teamh.repositories.RolRepository;
import be.kdg.teamh.services.contracts.GebruikerService;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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
    public List<GebruikerResponse> all()
    {
        List<Gebruiker> gebruikers = repository.findAll();
        List<GebruikerResponse> dtos = new ArrayList<>();

        for (Gebruiker gebruiker : gebruikers)
        {
            GebruikerResponse dto = new GebruikerResponse();

            dto.setId(gebruiker.getId());
            dto.setGebruikersnaam(gebruiker.getGebruikersnaam());
            dto.setOrganisaties(gebruiker.getOrganisaties());
            dto.setHoofdthemas(gebruiker.getHoofdthemas());
            dto.setCirkelsessies(gebruiker.getCirkelsessies());
            dto.setKaarten(gebruiker.getKaarten());
            dto.setCommentaren(gebruiker.getCommentaren());
            dto.setBerichten(gebruiker.getBerichten());
            dto.setRollen(gebruiker.getRollen());

            dtos.add(dto);
        }

        return dtos;
    }

    @Override
    public void create(GebruikerRequest dto) throws RolNotFound
    {
        Rol rol = rollen.findOne(1);

        if (rol == null)
        {
            throw new RolNotFound();
        }

        Gebruiker gebruiker = new Gebruiker();

        gebruiker.setGebruikersnaam(dto.getGebruikersnaam());
        gebruiker.setWachtwoord(Hashing.sha256().hashString(dto.getWachtwoord(), StandardCharsets.UTF_8).toString());
        gebruiker.addRol(rol);

        repository.save(gebruiker);
    }

    @Override
    public void register(RegistratieRequest dto) throws RolNotFound, PasswordsDoNotMatch
    {
        if (!dto.getWachtwoord().equalsIgnoreCase(dto.getConfirmatie()))
        {
            throw new PasswordsDoNotMatch();
        }

        GebruikerRequest gebruiker = new GebruikerRequest();

        gebruiker.setGebruikersnaam(dto.getGebruikersnaam());
        gebruiker.setWachtwoord(dto.getWachtwoord());

        create(gebruiker);
    }

    @Override
    public GebruikerResponse find(int id) throws GebruikerNotFound
    {
        Gebruiker gebruiker = repository.findOne(id);

        if (gebruiker == null)
        {
            throw new GebruikerNotFound();
        }

        GebruikerResponse dto = new GebruikerResponse();

        dto.setId(gebruiker.getId());
        dto.setGebruikersnaam(gebruiker.getGebruikersnaam());
        dto.setOrganisaties(gebruiker.getOrganisaties());
        dto.setHoofdthemas(gebruiker.getHoofdthemas());
        dto.setCirkelsessies(gebruiker.getCirkelsessies());
        dto.setKaarten(gebruiker.getKaarten());
        dto.setCommentaren(gebruiker.getCommentaren());
        dto.setBerichten(gebruiker.getBerichten());
        dto.setRollen(gebruiker.getRollen());

        return dto;
    }

    @Override
    public GebruikerResponse findByLogin(LoginRequest login) throws GebruikerNotFound, InvalidCredentials
    {
        Gebruiker gebruiker = repository.findByGebruikersnaam(login.getGebruikersnaam());

        if (gebruiker == null)
        {
            throw new GebruikerNotFound();
        }

        if (!Hashing.sha256().hashString(login.getWachtwoord(), StandardCharsets.UTF_8).toString().equals(gebruiker.getWachtwoord()))
        {
            throw new InvalidCredentials();
        }

        GebruikerResponse dto = new GebruikerResponse();

        dto.setId(gebruiker.getId());
        dto.setGebruikersnaam(gebruiker.getGebruikersnaam());
        dto.setOrganisaties(gebruiker.getOrganisaties());
        dto.setHoofdthemas(gebruiker.getHoofdthemas());
        dto.setCirkelsessies(gebruiker.getCirkelsessies());
        dto.setKaarten(gebruiker.getKaarten());
        dto.setCommentaren(gebruiker.getCommentaren());
        dto.setBerichten(gebruiker.getBerichten());
        dto.setRollen(gebruiker.getRollen());

        return dto;
    }

    @Override
    public void update(int id, GebruikerRequest dto) throws GebruikerNotFound
    {
        Gebruiker gebruiker = repository.findOne(id);

        if (gebruiker == null)
        {
            throw new GebruikerNotFound();
        }

        gebruiker.setGebruikersnaam(dto.getGebruikersnaam());

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
    public void update(int id, Gebruiker gebruiker) throws GebruikerNotFound
    {
        GebruikerRequest dto = new GebruikerRequest();

        dto.setGebruikersnaam(gebruiker.getGebruikersnaam());
        dto.setWachtwoord(gebruiker.getWachtwoord());

        update(id, dto);
    }

    @Override
    public void delete(int id) throws GebruikerNotFound
    {
        Gebruiker gebruiker = repository.findOne(id);

        if (gebruiker == null)
        {
            throw new GebruikerNotFound();
        }

        repository.delete(gebruiker);
    }

    @Override
    public List<OrganisatieResponse> findOrganisaties(int id) throws GebruikerNotFound
    {
        Gebruiker gebruiker = repository.findOne(id);

        if (gebruiker == null)
        {
            throw new GebruikerNotFound();
        }

        List<Organisatie> organisaties = gebruiker.getOrganisaties();
        List<OrganisatieResponse> dtos = new ArrayList<>();

        for (Organisatie organisatie : organisaties)
        {
            OrganisatieResponse dto = new OrganisatieResponse();

            dto.setId(organisatie.getId());
            dto.setNaam(organisatie.getNaam());
            dto.setBeschrijving(organisatie.getBeschrijving());
            dto.setGebruiker(organisatie.getGebruiker().getId());
            dto.setHoofdthemas(organisatie.getHoofdthemas());

            dtos.add(dto);
        }

        return dtos;
    }

    @Override
    public List<CirkelsessieResponse> findCirkelsessies(int id) throws GebruikerNotFound
    {
        Gebruiker gebruiker = repository.findOne(id);

        if (gebruiker == null)
        {
            throw new GebruikerNotFound();
        }

        List<Cirkelsessie> cirkelsessies = gebruiker.getCirkelsessies();
        List<CirkelsessieResponse> dtos = new ArrayList<>();

        for (Cirkelsessie cirkelsessie : cirkelsessies)
        {
            CirkelsessieResponse dto = new CirkelsessieResponse();

            dto.setId(cirkelsessie.getId());
            dto.setNaam(cirkelsessie.getNaam());
            dto.setAantalCirkels(cirkelsessie.getAantalCirkels());
            dto.setMaxAantalKaarten(cirkelsessie.getMaxAantalKaarten());
            dto.setGesloten(cirkelsessie.isGesloten());
            dto.setStartDatum(cirkelsessie.getStartDatum());
            dto.setGebruiker(cirkelsessie.getGebruiker().getId());
            dto.setSubthema(cirkelsessie.getSubthema().getId());
            dto.setDeelnames(cirkelsessie.getDeelnames());
            dto.setSpelkaarten(cirkelsessie.getSpelkaarten());
            dto.setBerichten(cirkelsessie.getBerichten());

            dtos.add(dto);
        }

        return dtos;
    }

    @Override
    public List<DeelnameResponse> findDeelnames(int id) throws GebruikerNotFound
    {
        Gebruiker gebruiker = repository.findOne(id);

        if (gebruiker == null)
        {
            throw new GebruikerNotFound();
        }

        List<Deelname> deelnames = gebruiker.getDeelnames();
        List<DeelnameResponse> dtos = new ArrayList<>();

        for (Deelname deelname : deelnames)
        {
            DeelnameResponse dto = new DeelnameResponse();

            dto.setId(deelname.getId());
            dto.setAangemaakteKaarten(deelname.getAangemaakteKaarten());
            dto.setMedeorganisator(deelname.isMedeorganisator());
            dto.setCirkelsessie(deelname.getCirkelsessie().getId());
            dto.setGebruiker(deelname.getGebruiker().getId());

            dtos.add(dto);
        }

        return dtos;
    }
}
