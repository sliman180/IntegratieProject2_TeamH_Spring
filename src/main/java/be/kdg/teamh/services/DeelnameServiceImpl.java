package be.kdg.teamh.services;

import be.kdg.teamh.entities.Deelname;
import be.kdg.teamh.exceptions.DeelnameNotFound;
import be.kdg.teamh.repositories.DeelnameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeelnameServiceImpl implements DeelnameService {


    @Autowired
    private DeelnameRepository repository;

    @Override
    public Deelname find(int id) throws DeelnameNotFound {
        Deelname deelname =  repository.findOne(id);

        if(deelname == null){
            throw new DeelnameNotFound();
        }

        return deelname;
    }

    @Override
    public void create(Deelname deelname) {
        repository.save(deelname);
    }

    @Override
    public List<Deelname> all() {
        return repository.findAll();
    }

    @Override
    public void update(int id, Deelname deelname) throws DeelnameNotFound {
        Deelname old = repository.findOne(id);

        if(old == null){
            throw new DeelnameNotFound();
        }

        old.setCirkelsessie(deelname.getCirkelsessie());
        old.setGebruiker(deelname.getGebruiker());
        old.setMedeorganisator(deelname.isMedeorganisator());
        old.setAangemaakteKaarten(deelname.getAangemaakteKaarten());

        repository.save(old);
    }

    @Override
    public void delete(int id) throws DeelnameNotFound {
        Deelname deelname = repository.findOne(id);

        if(deelname == null){
            throw new DeelnameNotFound();
        }

        repository.delete(id);
    }


}
