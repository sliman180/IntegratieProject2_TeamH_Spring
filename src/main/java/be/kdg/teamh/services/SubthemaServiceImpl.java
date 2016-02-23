package be.kdg.teamh.services;

import be.kdg.teamh.entities.Subthema;
import be.kdg.teamh.exceptions.SubthemaNotFoundException;
import be.kdg.teamh.repositories.SubthemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubthemaServiceImpl implements SubthemaService
{
    @Autowired
    private SubthemaRepository repository;

    @Override
    public List<Subthema> all()
    {
        return repository.findAll();
    }

    @Override
    public void create(Subthema subthema)
    {
        repository.save(subthema);
    }

    @Override
    public Subthema find(int id) throws SubthemaNotFoundException
    {
        Subthema subthema = repository.findOne(id);

        if (subthema == null)
        {
            throw new SubthemaNotFoundException();
        }

        return subthema;
    }

    @Override
    public void update(int id, Subthema subthema) throws SubthemaNotFoundException
    {
        Subthema old = repository.findOne(id);

        if (subthema == null)
        {
            throw new SubthemaNotFoundException();
        }

        old.setNaam(subthema.getNaam());
        old.setBeschrijving(subthema.getBeschrijving());

        repository.save(old);
    }

    @Override
    public void delete(int id) throws SubthemaNotFoundException
    {
        Subthema subthema = repository.findOne(id);

        if (subthema == null)
        {
            throw new SubthemaNotFoundException();
        }

        repository.delete(subthema);
    }
}
