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
    public Subthema subthemaMaken(Subthema subthema) {
        return this.subthemaRepository.save(subthema);
    }

    @Override
    public List<Subthema> subthemasOphalen() {
        return this.subthemaRepository.findAll();
    }
}
