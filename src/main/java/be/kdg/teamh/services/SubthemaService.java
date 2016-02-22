package be.kdg.teamh.services;

import be.kdg.teamh.entities.Subthema;

import java.util.List;

public interface SubthemaService
{
    void create(Subthema subthema);
    List<Subthema> all();
    void delete(int id);
    Subthema find(int id);
    void update(int id, Subthema subthema);
}
