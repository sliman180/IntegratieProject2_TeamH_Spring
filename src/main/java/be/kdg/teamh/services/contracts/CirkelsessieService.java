package be.kdg.teamh.services.contracts;

import be.kdg.teamh.entities.Cirkelsessie;
import be.kdg.teamh.entities.Kaart;
import be.kdg.teamh.exceptions.notfound.CirkelsessieNotFound;
import be.kdg.teamh.exceptions.notfound.GebruikerNotFound;
import be.kdg.teamh.exceptions.notfound.SubthemaNotFound;

import java.util.List;

public interface CirkelsessieService {
    List<Cirkelsessie> all();

    void create(int userId, Cirkelsessie hoofdthema);

    void create(int userId, int subthemaId, Cirkelsessie hoofdthema) throws SubthemaNotFound, GebruikerNotFound;

    Cirkelsessie find(int id) throws CirkelsessieNotFound;

    void update(int id, Cirkelsessie cirkelsessie) throws CirkelsessieNotFound;

    void delete(int id) throws CirkelsessieNotFound;

    void clone(int id) throws CirkelsessieNotFound;

    void addSpelkaart(int id, int userId, Kaart kaart) throws CirkelsessieNotFound, GebruikerNotFound;

    List<Cirkelsessie> gesloten();

    List<Cirkelsessie> gestart();

    List<Cirkelsessie> open();

    List<Cirkelsessie> beeindigd();
}
