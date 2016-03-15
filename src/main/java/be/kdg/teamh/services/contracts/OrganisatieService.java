package be.kdg.teamh.services.contracts;

import be.kdg.teamh.dtos.request.OrganisatieRequest;
import be.kdg.teamh.dtos.response.OrganisatieResponse;
import be.kdg.teamh.entities.Organisatie;
import be.kdg.teamh.exceptions.notfound.GebruikerNotFound;
import be.kdg.teamh.exceptions.notfound.OrganisatieNotFound;

import java.util.List;

public interface OrganisatieService
{
    List<OrganisatieResponse> all();

    void create(OrganisatieRequest organisatie) throws GebruikerNotFound;

    OrganisatieResponse find(int id) throws OrganisatieNotFound;

    void update(int id, OrganisatieRequest organisatie) throws OrganisatieNotFound, GebruikerNotFound;

    void delete(int id) throws OrganisatieNotFound;
}
