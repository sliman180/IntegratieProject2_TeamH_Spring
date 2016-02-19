package be.kdg.teamh.services;

import be.kdg.teamh.entities.Subthema;
import be.kdg.teamh.repositories.SubthemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lollik on 18/02/2016.
 */
@Service
public class SubthemaServiceImpl implements SubthemaService {

    @Autowired
    private SubthemaRepository subthemaRepository;

    @Override
    public void subthemaMaken(Subthema subthema) {
        this.subthemaRepository.save(subthema);
    }

    @Override
    public List<Subthema> subthemasOphalen() {
        return this.subthemaRepository.findAll();
    }

    @Override
    public void subthemaVerwijderen(int id) {
        this.subthemaRepository.delete(id);
    }

    @Override
    public Subthema subthemaOphalen(int id) {
        return this.subthemaRepository.findOne(id);
    }

    @Override
    public void subthemaAnpassen(int id, Subthema subthema) {
        Subthema old = this.subthemaRepository.findOne(id);

        old.setNaam(subthema.getNaam());
        old.setBeschrijving(subthema.getBeschrijving());

        this.subthemaRepository.save(old);
    }
}
