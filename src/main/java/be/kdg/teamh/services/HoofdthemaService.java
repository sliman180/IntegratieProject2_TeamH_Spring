package be.kdg.teamh.services;

import be.kdg.teamh.entities.Hoofdthema;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface HoofdthemaService
{
    List<Hoofdthema> all();
    Hoofdthema create(Hoofdthema hoofdthema);
    Hoofdthema find(int id);
    Hoofdthema update(int id, Hoofdthema hoofdthema);
    void delete(int id);
}
