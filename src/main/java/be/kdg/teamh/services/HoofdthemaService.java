package be.kdg.teamh.services;

import be.kdg.teamh.entities.Hoofdthema;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface HoofdthemaService
{
    List<Hoofdthema> all();
    Hoofdthema find(int id);
    void create(Hoofdthema hoofdthema);
    void update(int id, Hoofdthema hoofdthema);
    void delete(int id);
}
