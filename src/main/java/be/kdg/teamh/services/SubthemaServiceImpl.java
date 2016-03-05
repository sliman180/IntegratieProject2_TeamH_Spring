package be.kdg.teamh.services;

import be.kdg.teamh.entities.Subthema;
import be.kdg.teamh.exceptions.SubthemaNotFound;
import be.kdg.teamh.repositories.SubthemaRepository;
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

    @Override
    public List<Subthema> all() {
        return repository.findAll();
    }

    @Override
    public void create(Subthema subthema) {
        repository.save(subthema);
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

        repository.save(old);
    }

    @Override
    public void delete(int id) throws SubthemaNotFound {
        Subthema subthema = find(id);

        repository.delete(subthema);
    }
}
