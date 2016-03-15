package be.kdg.teamh.services;

import be.kdg.teamh.entities.Cirkelsessie;
import be.kdg.teamh.entities.Deelname;
import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.exceptions.AlreadyJoinedCirkelsessie;
import be.kdg.teamh.exceptions.CirkelsessieNotFound;
import be.kdg.teamh.exceptions.DeelnameNotFound;
import be.kdg.teamh.exceptions.GebruikerNotFound;
import be.kdg.teamh.repositories.CirkelsessieRepository;
import be.kdg.teamh.repositories.DeelnameRepository;
import be.kdg.teamh.repositories.GebruikerRepository;
import be.kdg.teamh.services.contracts.CirkelsessieService;
import be.kdg.teamh.services.contracts.DeelnameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DeelnameServiceImpl implements DeelnameService {
    @Autowired
    private DeelnameRepository repository;

    @Autowired
    private GebruikerRepository gebruikerRepository;

    @Autowired
    private CirkelsessieRepository cirkelsessieRepository;

    @Autowired
    private CirkelsessieService cirkelsessieService;

    @Override
    public List<Deelname> all() {
        return repository.findAll();
    }

    @Override
    public void create(int id, int userId) throws DeelnameNotFound, GebruikerNotFound, CirkelsessieNotFound, AlreadyJoinedCirkelsessie {

        Cirkelsessie cirkelsessie = cirkelsessieRepository.findOne(id);
        Gebruiker gebruiker = gebruikerRepository.findOne(userId);


        for (Deelname deelname : repository.findAll()) {
            if (deelname.getGebruiker().getId() == gebruiker.getId() && deelname.getCirkelsessie().getId() == cirkelsessie.getId()) {
                throw new AlreadyJoinedCirkelsessie();

            }
        }

        if (cirkelsessie == null) {
            throw new CirkelsessieNotFound();
        }

        if (gebruiker == null) {
            throw new GebruikerNotFound();
        }

        //deelname
        Deelname deelname = new Deelname(0, false);
        deelname.setCirkelsessie(cirkelsessie);
        deelname.setGebruiker(gebruiker);
        Deelname savedDeelname = repository.save(deelname);

        gebruiker.addDeelname(savedDeelname);
        cirkelsessie.addDeelname(savedDeelname);

        //gebruiker
        gebruikerRepository.saveAndFlush(gebruiker);

        //cirkelsessie
        cirkelsessieRepository.saveAndFlush(cirkelsessie);


    }

    @Override
    public Deelname find(int id) throws DeelnameNotFound {
        Deelname deelname = repository.findOne(id);

        if (deelname == null) {
            throw new DeelnameNotFound();
        }

        return deelname;
    }

    @Override
    public Deelname findByCirkelsessie(int id) throws DeelnameNotFound, CirkelsessieNotFound {
        Deelname deelname = repository.findByCirkelsessie(cirkelsessieService.find(id));

        if (deelname == null) {
            throw new DeelnameNotFound();
        }

        return deelname;
    }

    @Override
    public void update(int id, Deelname deelname) throws DeelnameNotFound {
        Deelname old = find(id);

        old.setCirkelsessie(deelname.getCirkelsessie());
        old.setGebruiker(deelname.getGebruiker());
        old.setMedeorganisator(deelname.isMedeorganisator());
        old.setAangemaakteKaarten(deelname.getAangemaakteKaarten());

        repository.saveAndFlush(old);
    }

    @Override
    public void delete(int id) throws DeelnameNotFound {
        Deelname deelname = find(id);

        if (deelname == null) {
            throw new DeelnameNotFound();
        }
        repository.delete(id);
    }
}
