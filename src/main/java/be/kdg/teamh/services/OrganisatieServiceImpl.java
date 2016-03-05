package be.kdg.teamh.services;

import be.kdg.teamh.entities.Organisatie;
import be.kdg.teamh.exceptions.OrganisatieNotFound;
import be.kdg.teamh.repositories.OrganisatieRepository;
import be.kdg.teamh.services.contracts.OrganisatieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrganisatieServiceImpl implements OrganisatieService {
    @Autowired
    OrganisatieRepository repository;

    public List<Organisatie> all() {
        return repository.findAll();
    }

    @Override
    public void create(Organisatie organisatie) {
        repository.save(organisatie);
    }

    @Override
    public Organisatie find(int id) throws OrganisatieNotFound {
        Organisatie organisatie = repository.findOne(id);

        if (organisatie == null) {
            throw new OrganisatieNotFound();
        }

        return organisatie;
    }

    @Override
    public void update(int id, Organisatie organisatie) throws OrganisatieNotFound {
        Organisatie old = find(id);

        old.setNaam(organisatie.getNaam());
        old.setBeschrijving(organisatie.getBeschrijving());
        old.setOrganisator(organisatie.getOrganisator());

        repository.save(old);
    }

    @Override
    public void delete(int id) throws OrganisatieNotFound {
        Organisatie organisatie = find(id);

        repository.delete(organisatie);
    }
}
