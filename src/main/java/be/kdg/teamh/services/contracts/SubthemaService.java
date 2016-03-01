package be.kdg.teamh.services.contracts;

import be.kdg.teamh.entities.Subthema;
import be.kdg.teamh.exceptions.SubthemaNotFound;

import java.util.List;

public interface SubthemaService
{
    List<Subthema> all();

    void create(Subthema subthema);

    Subthema find(int id) throws SubthemaNotFound;

    void update(int id, Subthema subthema) throws SubthemaNotFound;

    void delete(int id) throws SubthemaNotFound;
}
