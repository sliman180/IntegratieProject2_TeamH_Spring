package be.kdg.teamh.services.contracts;

import be.kdg.teamh.entities.Cirkelsessie;
import be.kdg.teamh.entities.Kaart;
import be.kdg.teamh.exceptions.notfound.CirkelsessieNotFound;

import javax.mail.MessagingException;
import java.util.List;

public interface CirkelsessieService
{
    List<Cirkelsessie> all();

    void create(int userId, Cirkelsessie hoofdthema);

    Cirkelsessie find(int id) throws CirkelsessieNotFound;

    void update(int id, Cirkelsessie cirkelsessie) throws CirkelsessieNotFound;

    void delete(int id) throws CirkelsessieNotFound;

    void clone(int id) throws CirkelsessieNotFound;

    void addSpelkaart(int id, Kaart kaart) throws CirkelsessieNotFound;


    List<Cirkelsessie> actief();

    List<Cirkelsessie> gepland();

    void invite(List<String> emails) throws MessagingException;
}
