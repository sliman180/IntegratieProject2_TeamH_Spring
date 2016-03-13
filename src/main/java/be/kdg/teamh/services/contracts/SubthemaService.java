package be.kdg.teamh.services.contracts;

import be.kdg.teamh.entities.Kaart;
import be.kdg.teamh.entities.Subthema;
import be.kdg.teamh.exceptions.GebruikerNotFound;
import be.kdg.teamh.exceptions.HoofdthemaNotFound;
import be.kdg.teamh.exceptions.IsForbidden;
import be.kdg.teamh.exceptions.SubthemaNotFound;

import java.util.List;

public interface SubthemaService {
    List<Subthema> all();

    void create(int userId, int hoofdthemaId, Subthema subthema) throws GebruikerNotFound, HoofdthemaNotFound;

    void create(int userId, Subthema subthema) throws GebruikerNotFound;

    Subthema find(int id) throws SubthemaNotFound;

    void update(int id, Subthema subthema) throws SubthemaNotFound;

    void delete(int id) throws SubthemaNotFound;

    void addKaart(int subthemaId, int userId, Kaart kaart) throws GebruikerNotFound, SubthemaNotFound;

    List<Kaart> getKaarten(int userId, int subthemaId) throws SubthemaNotFound, IsForbidden;
}
