package be.kdg.teamh.services.contracts;

import be.kdg.teamh.dtos.request.BerichtRequest;
import be.kdg.teamh.dtos.request.CirkelsessieCloneRequest;
import be.kdg.teamh.dtos.request.CirkelsessieRequest;
import be.kdg.teamh.dtos.request.KaartRequest;
import be.kdg.teamh.entities.*;
import be.kdg.teamh.exceptions.AlreadyJoinedCirkelsessie;
import be.kdg.teamh.exceptions.notfound.CirkelsessieNotFound;
import be.kdg.teamh.exceptions.notfound.GebruikerNotFound;
import be.kdg.teamh.exceptions.notfound.SubthemaNotFound;

import java.util.List;

public interface CirkelsessieService
{
    List<Cirkelsessie> all();

    List<Cirkelsessie> actief();

    List<Cirkelsessie> gesloten();

    List<Cirkelsessie> beindigd();

    List<Cirkelsessie> gepland();

    void create(CirkelsessieRequest dto) throws GebruikerNotFound, SubthemaNotFound;

    Cirkelsessie find(int id) throws CirkelsessieNotFound;

    void update(int id, CirkelsessieRequest dto) throws CirkelsessieNotFound;

    void delete(int id) throws CirkelsessieNotFound;

    void clone(int id, CirkelsessieCloneRequest dto) throws CirkelsessieNotFound;

    Subthema findSubthema(int id) throws CirkelsessieNotFound;

    List<Deelname> findDeelnames(int id) throws CirkelsessieNotFound;

    void addDeelname(int id, int gebruikerId) throws CirkelsessieNotFound, GebruikerNotFound, AlreadyJoinedCirkelsessie;

    List<Spelkaart> findSpelkaarten(int id) throws CirkelsessieNotFound;

    void addSpelkaart(int id, KaartRequest dto) throws CirkelsessieNotFound, GebruikerNotFound;

    List<Bericht> findBerichten(int id) throws CirkelsessieNotFound;

    void addBericht(int id, BerichtRequest dto) throws CirkelsessieNotFound, GebruikerNotFound;

    Gebruiker findGebruiker(int id) throws CirkelsessieNotFound;
}
