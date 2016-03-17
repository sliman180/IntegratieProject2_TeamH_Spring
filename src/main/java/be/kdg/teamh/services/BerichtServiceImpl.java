package be.kdg.teamh.services;

import be.kdg.teamh.entities.Bericht;
import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.exceptions.notfound.BerichtNotFound;
import be.kdg.teamh.repositories.BerichtRepository;
import be.kdg.teamh.services.contracts.BerichtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BerichtServiceImpl implements BerichtService {

    private BerichtRepository repository;

    @Autowired
    public BerichtServiceImpl(BerichtRepository repository) {
        this.repository = repository;
    }


    @Override
    public Gebruiker getGebruiker(int id) throws BerichtNotFound {
        Bericht bericht = repository.findOne(id);

        if (bericht == null) {
            throw new BerichtNotFound();
        }

        return bericht.getGebruiker();
    }
}
