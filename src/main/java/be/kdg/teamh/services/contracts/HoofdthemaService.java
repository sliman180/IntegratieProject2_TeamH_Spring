package be.kdg.teamh.services.contracts;

import be.kdg.teamh.entities.Hoofdthema;
import be.kdg.teamh.exceptions.notfound.HoofdthemaNotFound;

import java.util.List;

public interface HoofdthemaService
{
    List<Hoofdthema> all();

    void create(Hoofdthema hoofdthema);

    Hoofdthema find(int id) throws HoofdthemaNotFound;

    void update(int id, Hoofdthema hoofdthema) throws HoofdthemaNotFound;

    void delete(int id) throws HoofdthemaNotFound;
}
