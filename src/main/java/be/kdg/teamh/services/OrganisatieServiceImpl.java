package be.kdg.teamh.services;

import be.kdg.teamh.entities.Organisatie;
import be.kdg.teamh.repositories.OrganisatieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrganisatieServiceImpl implements OrganisatieService {

    @Autowired
    OrganisatieRepository organisatieRepository;

    public void addOrganisatie(Organisatie organisatie) {
        organisatieRepository.save(organisatie);

    }

    public Organisatie getOrganisatie(int id) {
        try {
            return organisatieRepository.findOne(id);
        } catch (IndexOutOfBoundsException ie) {
            return null;
        }
        /*
        if(organisaties.size()<1) {
            return null;
        }else{
            return organisaties.get(id);
        }
        */
    }
}
