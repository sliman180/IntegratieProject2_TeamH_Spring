package be.kdg.teamh.services;

import be.kdg.teamh.entities.Cirkelsessie;
import be.kdg.teamh.entities.Deelname;
import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.exceptions.GebruikerNotFound;
import be.kdg.teamh.repositories.GebruikerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GebruikerServiceImpl implements GebruikerService {

    @Autowired
    private GebruikerRepository repository;

    @Override
    public void create(Gebruiker gebruiker) {
        repository.save(gebruiker);
    }

    @Override
    public List<Cirkelsessie> showCirkelsessies(int id) throws GebruikerNotFound {
        Gebruiker gebruiker = repository.findOne(id);
        List<Cirkelsessie> cirkelsessies = new ArrayList<>();

        if (gebruiker == null) {
            throw new GebruikerNotFound();
        }

        for (Deelname d : gebruiker.getDeelnames()) {
            cirkelsessies.add(d.getCirkelsessie());
        }

        return cirkelsessies;
    }
}
