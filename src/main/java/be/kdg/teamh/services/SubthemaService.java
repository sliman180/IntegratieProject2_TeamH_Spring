package be.kdg.teamh.services;

import be.kdg.teamh.entities.Subthema;
import be.kdg.teamh.exceptions.SubthemaNotFoundException;

import java.util.List;

public interface SubthemaService
{
    List<Subthema> all();
    void create(Subthema subthema);
    Subthema find(int id) throws SubthemaNotFoundException;
    void update(int id, Subthema subthema) throws SubthemaNotFoundException;
    void delete(int id) throws SubthemaNotFoundException;
}
