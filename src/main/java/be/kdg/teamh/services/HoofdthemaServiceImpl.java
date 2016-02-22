package be.kdg.teamh.services;

import be.kdg.teamh.entities.Hoofdthema;
import be.kdg.teamh.exceptions.HoofdthemaNotFoundException;
import be.kdg.teamh.repositories.HoofdthemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HoofdthemaServiceImpl implements HoofdthemaService
{
    @Autowired
    private HoofdthemaRepository repository;

    @Override
    public List<Hoofdthema> all()
    {
        return repository.findAll();
    }

    @Override
    public void create(Hoofdthema hoofdthema)
    {
        repository.save(hoofdthema);
    }

    @Override
    public Hoofdthema find(int id) throws HoofdthemaNotFoundException
    {
        Hoofdthema hoofdthema = repository.findOne(id);

        if (hoofdthema == null)
        {
            throw new HoofdthemaNotFoundException();
        }

        return hoofdthema;
    }

    @Override
    public void update(int id, Hoofdthema hoofdthema) throws HoofdthemaNotFoundException
    {
        Hoofdthema old = repository.findOne(id);

        if (old == null)
        {
            throw new HoofdthemaNotFoundException();
        }

        old.setNaam(hoofdthema.getNaam());
        old.setBeschrijving(hoofdthema.getBeschrijving());
        old.setOrganisatie(hoofdthema.getOrganisatie());
        old.setGebruiker(hoofdthema.getGebruiker());

        repository.save(old);
    }

    @Override
    public void delete(int id) throws HoofdthemaNotFoundException
    {
        Hoofdthema hoofdthema = repository.findOne(id);

        if (hoofdthema == null)
        {
            throw new HoofdthemaNotFoundException();
        }

        repository.delete(hoofdthema);
    }
}
