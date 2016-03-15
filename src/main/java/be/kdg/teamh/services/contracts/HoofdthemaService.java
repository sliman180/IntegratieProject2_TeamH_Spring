package be.kdg.teamh.services.contracts;

import be.kdg.teamh.dtos.request.HoofdthemaRequest;
import be.kdg.teamh.dtos.response.HoofdthemaResponse;
import be.kdg.teamh.entities.Hoofdthema;
import be.kdg.teamh.exceptions.notfound.GebruikerNotFound;
import be.kdg.teamh.exceptions.notfound.HoofdthemaNotFound;
import be.kdg.teamh.exceptions.notfound.OrganisatieNotFound;

import java.util.List;

public interface HoofdthemaService
{
    List<HoofdthemaResponse> all();

    void create(HoofdthemaRequest hoofdthema) throws OrganisatieNotFound, GebruikerNotFound;

    HoofdthemaResponse find(int id) throws HoofdthemaNotFound;

    void update(int id, HoofdthemaRequest hoofdthema) throws HoofdthemaNotFound, OrganisatieNotFound, GebruikerNotFound;

    void delete(int id) throws HoofdthemaNotFound;
}
