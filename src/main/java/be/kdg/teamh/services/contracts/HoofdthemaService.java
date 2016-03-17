package be.kdg.teamh.services.contracts;

import be.kdg.teamh.dtos.request.HoofdthemaRequest;
import be.kdg.teamh.entities.Hoofdthema;
import be.kdg.teamh.entities.Organisatie;
import be.kdg.teamh.exceptions.notfound.GebruikerNotFound;
import be.kdg.teamh.exceptions.notfound.HoofdthemaNotFound;
import be.kdg.teamh.exceptions.notfound.OrganisatieNotFound;

import java.util.List;

public interface HoofdthemaService {
    List<Hoofdthema> all();

    void create(HoofdthemaRequest dto) throws OrganisatieNotFound, GebruikerNotFound;

    Hoofdthema find(int id) throws HoofdthemaNotFound;

    void update(int id, HoofdthemaRequest dto) throws HoofdthemaNotFound, OrganisatieNotFound, GebruikerNotFound;

    void delete(int id) throws HoofdthemaNotFound;

    Organisatie findOrganisatie(int id) throws HoofdthemaNotFound;
}
