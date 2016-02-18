package be.kdg.teamh.services;

import be.kdg.teamh.entities.Hoofdthema;
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
    public Hoofdthema create(Hoofdthema hoofdthema)
    {
        return repository.save(hoofdthema);
    }

    @Override
    public Hoofdthema find(int id)
    {
        return repository.findOne(id);
    }

    @Override
    public Hoofdthema update(int id, Hoofdthema hoofdthema)
    {
        Hoofdthema old = repository.findOne(id);

        old.setNaam(hoofdthema.getNaam());
        old.setBeschrijving(hoofdthema.getBeschrijving());
        old.setOrganisatie(hoofdthema.getOrganisatie());
        old.setGebruiker(hoofdthema.getGebruiker());

        return repository.save(old);
    }

    @Override
    public void delete(int id)
    {
        repository.delete(id);
    }
}
