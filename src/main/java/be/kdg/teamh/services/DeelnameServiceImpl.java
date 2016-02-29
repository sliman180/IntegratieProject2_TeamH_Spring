package be.kdg.teamh.services;

import be.kdg.teamh.entities.Deelname;
import be.kdg.teamh.exceptions.DeelnameNotFound;
import be.kdg.teamh.repositories.DeelnameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
