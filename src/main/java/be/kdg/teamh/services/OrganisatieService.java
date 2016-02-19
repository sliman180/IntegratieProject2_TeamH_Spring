package be.kdg.teamh.services;

import be.kdg.teamh.entities.Organisatie;

import java.util.List;


public interface OrganisatieService {

    void addOrganisatie(Organisatie organisatie);

    Organisatie getOrganisatie(int id);

    void editOrganisatie(int id, Organisatie organisatie);

    void deleteOrganisatie(int id);

    List<Organisatie> readAllOrganisaties();


}
