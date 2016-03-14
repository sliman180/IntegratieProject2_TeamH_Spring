package be.kdg.teamh.services.contracts;

import be.kdg.teamh.entities.Hoofdthema;
import be.kdg.teamh.exceptions.notfound.GebruikerNotFound;
import be.kdg.teamh.exceptions.notfound.HoofdthemaNotFound;
import be.kdg.teamh.exceptions.notfound.OrganisatieNotFound;

import java.util.List;

public interface HoofdthemaService {
    List<Hoofdthema> all();

    void create(int userId, Hoofdthema hoofdthema) throws GebruikerNotFound;

    void create(int userId, int organisatieId, Hoofdthema hoofdthema) throws GebruikerNotFound, OrganisatieNotFound;

    Hoofdthema find(int id) throws HoofdthemaNotFound;

    void update(int id, Hoofdthema hoofdthema) throws HoofdthemaNotFound;

    void delete(int id) throws HoofdthemaNotFound;
}
