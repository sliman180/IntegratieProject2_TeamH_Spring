package be.kdg.teamh.services;

import be.kdg.teamh.entities.Organisatie;
import be.kdg.teamh.repositories.OrganisatieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganisatieServiceImpl implements OrganisatieService
{
    @Autowired
    OrganisatieRepository repository;

    public List<Organisatie> all()
    {
        return repository.findAll();
    }

    @Override
    public void create(Organisatie organisatie)
    {
        repository.save(organisatie);
    }

    @Override
    public Organisatie find(int id)
    {
        return repository.findOne(id);
    }

    @Override
    public void update(int id, Organisatie organisatie)
    {
        Organisatie old = repository.findOne(id);

        old.setNaam(organisatie.getNaam());
        old.setBeschrijving(organisatie.getBeschrijving());
        old.setOrganisator(organisatie.getOrganisator());

        repository.save(old);
    }

    @Override
    public void delete(int id)
    {
        repository.delete(id);
    }
}
