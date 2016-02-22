package be.kdg.teamh.services;

import be.kdg.teamh.entities.Subthema;
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
        return this.repository.findAll();
    }

    @Override
    public void create(Subthema subthema)
    {
        this.repository.save(subthema);
    }

    @Override
    public Subthema find(int id)
    {
        return this.repository.findOne(id);
    }

    @Override
    public void update(int id, Subthema subthema)
    {
        Subthema old = this.repository.findOne(id);

        old.setNaam(subthema.getNaam());
        old.setBeschrijving(subthema.getBeschrijving());

        this.repository.save(old);
    }

    @Override
    public void delete(int id)
    {
        this.repository.delete(id);
    }
}
