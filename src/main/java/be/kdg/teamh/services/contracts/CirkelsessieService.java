package be.kdg.teamh.services.contracts;

import be.kdg.teamh.entities.Cirkelsessie;
import be.kdg.teamh.entities.Kaart;
import be.kdg.teamh.exceptions.CirkelsessieNotFound;
import be.kdg.teamh.exceptions.GebruikerNotFound;

import java.util.List;

public interface CirkelsessieService {
    List<Cirkelsessie> all();

    void create(int userId, Cirkelsessie hoofdthema);

    Cirkelsessie find(int id) throws CirkelsessieNotFound;

    void update(int id, Cirkelsessie cirkelsessie) throws CirkelsessieNotFound;

    void delete(int id) throws CirkelsessieNotFound;

    void clone(int id) throws CirkelsessieNotFound;

    void addSpelkaart(int id, int userId, Kaart kaart) throws CirkelsessieNotFound, GebruikerNotFound;


    List<Cirkelsessie> actief();

    List<Cirkelsessie> gepland();
}
