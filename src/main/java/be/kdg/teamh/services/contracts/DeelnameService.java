package be.kdg.teamh.services.contracts;

import be.kdg.teamh.entities.Deelname;
import be.kdg.teamh.exceptions.AlreadyJoinedCirkelsessie;
import be.kdg.teamh.exceptions.notfound.CirkelsessieNotFound;
import be.kdg.teamh.exceptions.notfound.DeelnameNotFound;
import be.kdg.teamh.exceptions.notfound.GebruikerNotFound;

import java.util.List;

public interface DeelnameService {
    List<Deelname> all();

    void create(int id, int userId) throws DeelnameNotFound, GebruikerNotFound, CirkelsessieNotFound, AlreadyJoinedCirkelsessie;

    Deelname find(int id) throws DeelnameNotFound;

    void update(int id, Deelname deelname) throws DeelnameNotFound;

    void delete(int id) throws DeelnameNotFound;
}
