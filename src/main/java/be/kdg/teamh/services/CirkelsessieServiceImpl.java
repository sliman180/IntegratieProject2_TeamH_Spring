package be.kdg.teamh.services;

import be.kdg.teamh.entities.Cirkelsessie;
import be.kdg.teamh.exceptions.CirkelsessieNotFound;
import be.kdg.teamh.repositories.CirkelsessieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CirkelsessieServiceImpl implements CirkelsessieService {

    @Autowired
    private CirkelsessieRepository repository;

    @Override
    public List<Cirkelsessie> all() {
        return repository.findAll();
    }

    @Override
    public void create(Cirkelsessie cirkelsessie) {
        repository.save(cirkelsessie);
    }

    @Override
    public Cirkelsessie find(int id) throws CirkelsessieNotFound {
        Cirkelsessie cirkelsessie = repository.findOne(id);

        if (cirkelsessie == null) {
            throw new CirkelsessieNotFound();
        }

        return cirkelsessie;
    }

    @Override
    public void update(int id, Cirkelsessie cirkelsessie) throws CirkelsessieNotFound {
        Cirkelsessie old = repository.findOne(id);

        if (old == null) {
            throw new CirkelsessieNotFound();
        }

        old.setNaam(cirkelsessie.getNaam());
        old.setGebruiker(cirkelsessie.getGebruiker());
        old.setMaxAantalKaarten(cirkelsessie.getMaxAantalKaarten());
        old.setAantalCirkels(cirkelsessie.getAantalCirkels());

        repository.save(old);
    }


    @Override
    public void delete(int id) throws CirkelsessieNotFound {
        Cirkelsessie cirkelsessie = repository.findOne(id);

        if (cirkelsessie == null) {
            throw new CirkelsessieNotFound();
        }

        repository.delete(cirkelsessie);
    }

    @Override
    public void clone(int id) {
        Cirkelsessie old = repository.findOne(id);
        Cirkelsessie clone = new Cirkelsessie(old.getNaam(),old.getMaxAantalKaarten(),old.getAantalCirkels(),old.getSubthema(),old.getGebruiker());
        clone.cloneDeelnames(old.getDeelnames());
        repository.save(clone);
    }


}
