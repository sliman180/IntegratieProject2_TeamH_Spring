package be.kdg.teamh.services;

import be.kdg.teamh.entities.Hoofdthema;

import java.util.List;

public interface HoofdthemaService
{
    List<Hoofdthema> all();
    void create(Hoofdthema hoofdthema);
    void update(int id, Hoofdthema hoofdthema);
}
