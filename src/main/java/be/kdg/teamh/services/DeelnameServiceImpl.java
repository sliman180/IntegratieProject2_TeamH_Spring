package be.kdg.teamh.services;

import be.kdg.teamh.entities.Deelname;
import be.kdg.teamh.exceptions.DeelnameNotFound;
import be.kdg.teamh.repositories.DeelnameRepository;
import be.kdg.teamh.services.contracts.DeelnameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeelnameServiceImpl implements DeelnameService {
    @Autowired
    private DeelnameRepository repository;

    @Override
    public List<Deelname> all() {
        return repository.findAll();
    }

    @Override
    public void create(Deelname deelname) {
        repository.save(deelname);
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
    public void update(int id, Deelname deelname) throws DeelnameNotFound {
        Deelname old = find(id);

        old.setCirkelsessie(deelname.getCirkelsessie());
        old.setGebruiker(deelname.getGebruiker());
        old.setMedeorganisator(deelname.isMedeorganisator());
        old.setAangemaakteKaarten(deelname.getAangemaakteKaarten());

        repository.save(old);
    }

    @Override
    public void delete(int id) throws DeelnameNotFound {
        Deelname deelname = find(id);

        repository.delete(id);
    }
}
