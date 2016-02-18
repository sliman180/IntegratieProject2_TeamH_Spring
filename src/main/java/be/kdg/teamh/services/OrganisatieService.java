package be.kdg.teamh.services;

import be.kdg.teamh.entities.Organisatie;

import java.util.List;


public interface OrganisatieService {

    Organisatie addOrganisatie(Organisatie organisatie);

    Organisatie getOrganisatie(int id);

    Organisatie editOrganisatie(Organisatie organisatie);

    void deleteOrganisatie(int id);

    List<Organisatie> readAllOrganisaties();


}
