package be.kdg.teamh.services;

import be.kdg.teamh.entities.Hoofdthema;
import be.kdg.teamh.exceptions.HoofdthemaNotFoundException;

import java.util.List;

public interface HoofdthemaService
{
    List<Hoofdthema> all();
    void create(Hoofdthema hoofdthema);
    Hoofdthema find(int id) throws HoofdthemaNotFoundException;
    void update(int id, Hoofdthema hoofdthema) throws HoofdthemaNotFoundException;
    void delete(int id) throws HoofdthemaNotFoundException;
}
