package be.kdg.teamh.services;

import be.kdg.teamh.entities.Organisatie;
import org.springframework.stereotype.Service;

@Service
public interface OrganisatieService {

    void addOrganisatie(Organisatie organisatie);

    Organisatie getOrganisatie(int id);
}
