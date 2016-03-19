package be.kdg.teamh.services.contracts;

import be.kdg.teamh.dtos.request.OrganisatieRequest;
import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.entities.Hoofdthema;
import be.kdg.teamh.entities.Organisatie;
import be.kdg.teamh.exceptions.notfound.GebruikerNotFound;
import be.kdg.teamh.exceptions.notfound.OrganisatieNotFound;

import java.util.List;

public interface OrganisatieService
{
    List<Organisatie> all();

    void create(OrganisatieRequest dto) throws GebruikerNotFound;

    Organisatie find(int id) throws OrganisatieNotFound;

    void update(int id, OrganisatieRequest dto) throws OrganisatieNotFound, GebruikerNotFound;

    void delete(int id) throws OrganisatieNotFound;

    List<Hoofdthema> getHoofdthemas(int id) throws OrganisatieNotFound;

}
