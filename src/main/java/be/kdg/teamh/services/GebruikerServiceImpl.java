package be.kdg.teamh.services;

import be.kdg.teamh.entities.Cirkelsessie;
import be.kdg.teamh.entities.Deelname;
import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.exceptions.notfound.GebruikerNotFound;
import be.kdg.teamh.exceptions.InvalidCredentials;
import be.kdg.teamh.repositories.GebruikerRepository;
import be.kdg.teamh.services.contracts.GebruikerService;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class GebruikerServiceImpl implements GebruikerService
{
    @Autowired
    private GebruikerRepository repository;

    @Override
    public List<Gebruiker> all()
    {
        return repository.findAll();
    }

    @Override
    public void create(Gebruiker gebruiker)
    {
        gebruiker.setWachtwoord(Hashing.sha256().hashString(gebruiker.getWachtwoord(), StandardCharsets.UTF_8).toString());

        repository.save(gebruiker);
    }

    @Override
    public Gebruiker find(int id) throws GebruikerNotFound
    {
        Gebruiker gebruiker = repository.findOne(id);

        if (gebruiker == null)
        {
            throw new GebruikerNotFound();
        }

        return gebruiker;
    }

    @Override
    public Gebruiker findByLogin(Gebruiker login) throws GebruikerNotFound, InvalidCredentials
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

        return gebruiker;
    }

    @Override
    public void update(int id, Gebruiker gebruiker) throws GebruikerNotFound
    {
        Gebruiker old = find(id);

        old.setGebruikersnaam(gebruiker.getGebruikersnaam());

        if (!gebruiker.getWachtwoord().isEmpty())
        {
            old.setWachtwoord(Hashing.sha256().hashString(gebruiker.getWachtwoord(), StandardCharsets.UTF_8).toString());
        }

        repository.saveAndFlush(old);
    }

    @Override
    public void delete(int id) throws GebruikerNotFound
    {
        Gebruiker gebruiker = find(id);

        repository.delete(gebruiker);
    }

    @Override
    public List<Cirkelsessie> showCirkelsessies(int id) throws GebruikerNotFound
    {
        return find(id).getDeelnames().stream().map(Deelname::getCirkelsessie).collect(Collectors.toList());
    }
}
