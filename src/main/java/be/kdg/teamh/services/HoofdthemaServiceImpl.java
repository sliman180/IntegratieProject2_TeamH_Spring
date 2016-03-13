package be.kdg.teamh.services;

import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.entities.Hoofdthema;
import be.kdg.teamh.entities.Organisatie;
import be.kdg.teamh.exceptions.GebruikerNotFound;
import be.kdg.teamh.exceptions.HoofdthemaNotFound;
import be.kdg.teamh.exceptions.OrganisatieNotFound;
import be.kdg.teamh.repositories.GebruikerRepository;
import be.kdg.teamh.repositories.HoofdthemaRepository;
import be.kdg.teamh.repositories.OrganisatieRepository;
import be.kdg.teamh.services.contracts.HoofdthemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class HoofdthemaServiceImpl implements HoofdthemaService {
    @Autowired
    private HoofdthemaRepository repository;

    @Autowired
    private GebruikerRepository gebruikerRepository;

    @Autowired
    private OrganisatieRepository organisatieRepository;

    @Override
    public List<Hoofdthema> all() {
        return repository.findAll();
    }

    @Override
    public void create(int userId, Hoofdthema hoofdthema) throws GebruikerNotFound {

        Gebruiker gebruiker = gebruikerRepository.findOne(userId);

        if (gebruiker == null) {
            throw new GebruikerNotFound();
        }

        hoofdthema.setGebruiker(gebruiker);
        Hoofdthema savedHoofdthema = repository.save(hoofdthema);

        gebruiker.addHoofdthema(savedHoofdthema);
        gebruikerRepository.save(gebruiker);
    }

    @Override
    public void create(int userId, int organisatieId, Hoofdthema hoofdthema) throws GebruikerNotFound, OrganisatieNotFound {

        Gebruiker gebruiker = gebruikerRepository.findOne(userId);
        Organisatie organisatie = organisatieRepository.findOne(organisatieId);

        if (gebruiker == null) {
            throw new GebruikerNotFound();
        }

        if (organisatie == null) {
            throw new OrganisatieNotFound();
        }


        hoofdthema.setGebruiker(gebruiker);
        hoofdthema.setOrganisatie(organisatie);
        Hoofdthema savedHoofdthema = repository.save(hoofdthema);

        organisatie.addHoofdthema(savedHoofdthema);
        organisatieRepository.save(organisatie);


        gebruiker.addHoofdthema(savedHoofdthema);
        gebruikerRepository.save(gebruiker);
    }

    @Override
    public Hoofdthema find(int id) throws HoofdthemaNotFound {
        Hoofdthema hoofdthema = repository.findOne(id);

        if (hoofdthema == null) {
            throw new HoofdthemaNotFound();
        }

        return hoofdthema;
    }

    @Override
    public void update(int id, Hoofdthema hoofdthema) throws HoofdthemaNotFound {
        Hoofdthema old = find(id);

        old.setNaam(hoofdthema.getNaam());
        old.setBeschrijving(hoofdthema.getBeschrijving());
        old.setOrganisatie(hoofdthema.getOrganisatie());
        old.setGebruiker(hoofdthema.getGebruiker());

        repository.saveAndFlush(old);
    }

    @Override
    public void delete(int id) throws HoofdthemaNotFound {
        Hoofdthema hoofdthema = find(id);

        repository.delete(hoofdthema);
    }
}
