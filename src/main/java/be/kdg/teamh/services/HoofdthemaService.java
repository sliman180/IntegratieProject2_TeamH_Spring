package be.kdg.teamh.services;

import be.kdg.teamh.entities.Hoofdthema;
import be.kdg.teamh.exceptions.HoofdthemaNotFound;

import java.util.List;

public interface HoofdthemaService
{
    List<Hoofdthema> all();
    void create(Hoofdthema hoofdthema);
    Hoofdthema find(int id) throws HoofdthemaNotFound;
    void update(int id, Hoofdthema hoofdthema);
    void delete(int id);
}
