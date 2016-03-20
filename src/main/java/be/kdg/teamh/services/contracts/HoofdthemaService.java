package be.kdg.teamh.services.contracts;

import be.kdg.teamh.dtos.request.HoofdthemaRequest;
import be.kdg.teamh.entities.Hoofdthema;
import be.kdg.teamh.entities.Organisatie;
import be.kdg.teamh.entities.Subthema;
import be.kdg.teamh.exceptions.gebruiker.GebruikerNietGevonden;
import be.kdg.teamh.exceptions.hoofdthema.HoofdthemaNietGevonden;
import be.kdg.teamh.exceptions.organisatie.OrganisatieNietGevonden;

import java.util.List;

public interface HoofdthemaService
{
    List<Hoofdthema> all();

    void create(HoofdthemaRequest dto) throws OrganisatieNietGevonden, GebruikerNietGevonden;

    Hoofdthema find(int id) throws HoofdthemaNietGevonden;

    void update(int id, HoofdthemaRequest dto) throws HoofdthemaNietGevonden, OrganisatieNietGevonden, GebruikerNietGevonden;

    void delete(int id) throws HoofdthemaNietGevonden;

    Organisatie findOrganisatie(int id) throws HoofdthemaNietGevonden;

    List<Subthema> findSubthemas(int id) throws HoofdthemaNietGevonden;
}
