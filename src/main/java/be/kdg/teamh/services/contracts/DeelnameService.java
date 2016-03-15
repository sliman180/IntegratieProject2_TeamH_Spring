package be.kdg.teamh.services.contracts;

import be.kdg.teamh.entities.Deelname;
import be.kdg.teamh.exceptions.AlreadyJoinedCirkelsessie;
import be.kdg.teamh.exceptions.CirkelsessieNotFound;
import be.kdg.teamh.exceptions.DeelnameNotFound;
import be.kdg.teamh.exceptions.GebruikerNotFound;

import java.util.List;

public interface DeelnameService {
    List<Deelname> all();

    void create(int id, int userId) throws DeelnameNotFound, GebruikerNotFound, CirkelsessieNotFound, AlreadyJoinedCirkelsessie;

    Deelname find(int id) throws DeelnameNotFound;

    Deelname findByCirkelsessie(int id) throws DeelnameNotFound, CirkelsessieNotFound;

    void update(int id, Deelname deelname) throws DeelnameNotFound;

    void delete(int id) throws DeelnameNotFound;
}
