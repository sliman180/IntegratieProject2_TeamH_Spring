package be.kdg.teamh.services;

import be.kdg.teamh.entities.Organisatie;
import be.kdg.teamh.repositories.OrganisatieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganisatieServiceImpl implements OrganisatieService {

    @Autowired
    OrganisatieRepository organisatieRepository;

    @Override
    public Organisatie addOrganisatie(Organisatie organisatie) {
        return organisatieRepository.save(organisatie);
    }

    @Override
    public Organisatie getOrganisatie(int id) {
        try {
            return organisatieRepository.findOne(id);
        } catch (IndexOutOfBoundsException ie) {
            return null;
        }
    }

    @Override
    public Organisatie editOrganisatie(Organisatie organisatie) {
        return organisatieRepository.save(organisatie);
    }

    @Override
    public void deleteOrganisatie(int id) {
        organisatieRepository.delete(id);
    }

    @Override
    public List<Organisatie> readAllOrganisaties() {
        return organisatieRepository.findAll();
    }
}
