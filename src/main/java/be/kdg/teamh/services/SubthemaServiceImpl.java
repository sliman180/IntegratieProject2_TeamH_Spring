package be.kdg.teamh.services;

import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.entities.Hoofdthema;
import be.kdg.teamh.entities.Kaart;
import be.kdg.teamh.entities.Subthema;
import be.kdg.teamh.exceptions.GebruikerNotFound;
import be.kdg.teamh.exceptions.HoofdthemaNotFound;
import be.kdg.teamh.exceptions.IsForbidden;
import be.kdg.teamh.exceptions.SubthemaNotFound;
import be.kdg.teamh.repositories.*;
import be.kdg.teamh.services.contracts.SubthemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SubthemaServiceImpl implements SubthemaService {
    @Autowired
    private SubthemaRepository repository;

    @Autowired
    private HoofdthemaRepository hoofdthemaRepository;

    @Autowired
    private GebruikerRepository gebruikerRepository;

    @Autowired
    private KaartenRepository kaartenRepository;

    @Autowired
    private SpelkaartenRepository spelkaartenRepository;


    @Override
    public List<Subthema> all() {
        return repository.findAll();
    }

    @Override
    public void create(int userId, Subthema subthema) throws GebruikerNotFound {

        Gebruiker gebruiker = gebruikerRepository.findOne(userId);

        if (gebruiker == null) {
            throw new GebruikerNotFound();
        }

        subthema.setGebruiker(gebruiker);
        Subthema savedSubthema = repository.save(subthema);

        gebruiker.addSubthema(savedSubthema);
        gebruikerRepository.save(gebruiker);
    }

    @Override
    public void create(int userId, int hoofdthemaId, Subthema subthema) throws GebruikerNotFound, HoofdthemaNotFound {

        Gebruiker gebruiker = gebruikerRepository.findOne(userId);
        Hoofdthema hoofdthema = hoofdthemaRepository.findOne(hoofdthemaId);

        if (gebruiker == null) {
            throw new GebruikerNotFound();
        }

        if (hoofdthema == null) {
            throw new HoofdthemaNotFound();
        }


        subthema.setGebruiker(gebruiker);
        subthema.setHoofdthema(hoofdthema);
        Subthema savedSubthema = repository.save(subthema);

        hoofdthema.addSubthema(savedSubthema);
        hoofdthemaRepository.save(hoofdthema);


        gebruiker.addSubthema(savedSubthema);
        gebruikerRepository.save(gebruiker);
    }

    @Override
    public Subthema find(int id) throws SubthemaNotFound {
        Subthema subthema = repository.findOne(id);

        if (subthema == null) {
            throw new SubthemaNotFound();
        }

        return subthema;
    }

    @Override
    public void update(int id, Subthema subthema) throws SubthemaNotFound {
        Subthema old = find(id);

        old.setNaam(subthema.getNaam());
        old.setBeschrijving(subthema.getBeschrijving());

        repository.saveAndFlush(old);
    }

    @Override
    public void delete(int id) throws SubthemaNotFound {
        Subthema subthema = find(id);

        repository.delete(subthema);
    }

    @Override
    public void addKaart(int subthemaId, int userId, Kaart kaart) throws GebruikerNotFound, SubthemaNotFound {
        Subthema subthema = repository.findOne(subthemaId);
        Gebruiker gebruiker = gebruikerRepository.findOne(userId);

        if (subthema == null) {
            throw new SubthemaNotFound();
        }

        if (gebruiker == null) {
            throw new GebruikerNotFound();
        }

        //kaart
        kaart.setGebruiker(gebruiker);
        kaart.setSubthema(subthema);
        Kaart savedKaart = kaartenRepository.save(kaart);

        //cirkelsessie
        subthema.addKaart(savedKaart);
        repository.saveAndFlush(subthema);

        //gebruiker
        gebruiker.addKaart(savedKaart);
        gebruikerRepository.save(gebruiker);
    }

    @Override
    public List<Kaart> getKaarten(int userId, int subthemaId) throws SubthemaNotFound, IsForbidden {

        Subthema subthema = find(subthemaId);

        if (subthema.getGebruiker().getId() != userId) {

            throw new IsForbidden();
        }

        return subthema.getKaarten();

    }
}
