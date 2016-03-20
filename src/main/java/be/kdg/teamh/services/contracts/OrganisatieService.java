package be.kdg.teamh.services.contracts;

import be.kdg.teamh.dtos.request.OrganisatieRequest;
import be.kdg.teamh.entities.Hoofdthema;
import be.kdg.teamh.entities.Organisatie;
import be.kdg.teamh.exceptions.gebruiker.GebruikerNietGevonden;
import be.kdg.teamh.exceptions.organisatie.OrganisatieNietGevonden;

import java.util.List;

public interface OrganisatieService
{
    List<Organisatie> all();

    void create(OrganisatieRequest dto) throws GebruikerNietGevonden;

    Organisatie find(int id) throws OrganisatieNietGevonden;

    void update(int id, OrganisatieRequest dto) throws OrganisatieNietGevonden, GebruikerNietGevonden;

    void delete(int id) throws OrganisatieNietGevonden;

    List<Hoofdthema> findHoofdthemas(int id) throws OrganisatieNietGevonden;

}
