package be.kdg.teamh.services;

import be.kdg.teamh.entities.Cirkelsessie;
import be.kdg.teamh.entities.Deelname;
import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.exceptions.GebruikerNotFound;
import be.kdg.teamh.repositories.GebruikerRepository;
import be.kdg.teamh.services.contracts.GebruikerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GebruikerServiceImpl implements GebruikerService {
    @Autowired
    private GebruikerRepository repository;

    @Override
    public List<Gebruiker> all() {
        return repository.findAll();
    }

    @Override
    public void create(Gebruiker gebruiker) {
        repository.save(gebruiker);
    }

    @Override
    public Gebruiker find(int id) throws GebruikerNotFound {
        Gebruiker gebruiker = repository.findOne(id);

        if (gebruiker == null) {
            throw new GebruikerNotFound();
        }

        return gebruiker;
    }

    @Override
    public void update(int id, Gebruiker gebruiker) throws GebruikerNotFound {
        Gebruiker old = find(id);

        gebruiker.setGebruikersnaam(gebruiker.getGebruikersnaam());
        gebruiker.setWachtwoord(gebruiker.getWachtwoord());

        repository.save(old);
    }

    @Override
    public void delete(int id) throws GebruikerNotFound {
        Gebruiker gebruiker = find(id);

        repository.delete(gebruiker);
    }

    @Override
    public List<Cirkelsessie> showCirkelsessies(int id) throws GebruikerNotFound {
        return find(id).getDeelnames().stream().map(Deelname::getCirkelsessie).collect(Collectors.toList());
    }
}
