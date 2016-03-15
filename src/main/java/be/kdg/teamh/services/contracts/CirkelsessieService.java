package be.kdg.teamh.services.contracts;

import be.kdg.teamh.dtos.request.BerichtRequest;
import be.kdg.teamh.dtos.request.CirkelsessieRequest;
import be.kdg.teamh.dtos.request.KaartRequest;
import be.kdg.teamh.dtos.response.*;
import be.kdg.teamh.entities.Cirkelsessie;
import be.kdg.teamh.exceptions.AlreadyJoinedCirkelsessie;
import be.kdg.teamh.exceptions.notfound.CirkelsessieNotFound;
import be.kdg.teamh.exceptions.notfound.GebruikerNotFound;
import be.kdg.teamh.exceptions.notfound.SubthemaNotFound;

import java.util.List;

public interface CirkelsessieService
{
    List<CirkelsessieResponse> all();

    List<CirkelsessieResponse> actief();

    List<CirkelsessieResponse> gepland();

    void create(CirkelsessieRequest cirkelsessie) throws GebruikerNotFound, SubthemaNotFound;

    CirkelsessieResponse find(int id) throws CirkelsessieNotFound;

    void update(int id, CirkelsessieRequest cirkelsessie) throws CirkelsessieNotFound;

    void delete(int id) throws CirkelsessieNotFound;

    void clone(int id) throws CirkelsessieNotFound;

    SubthemaResponse getSubthema(int id) throws CirkelsessieNotFound;

    List<DeelnameResponse> getDeelnames(int id) throws CirkelsessieNotFound;

    void addDeelname(int id, int gebruikerId) throws CirkelsessieNotFound, GebruikerNotFound, AlreadyJoinedCirkelsessie;

    List<SpelkaartResponse> getSpelkaarten(int id) throws CirkelsessieNotFound;

    void addSpelkaart(int id, int gebruikerId, KaartRequest kaart) throws CirkelsessieNotFound, GebruikerNotFound;

    List<BerichtResponse> getBerichten(int id) throws CirkelsessieNotFound;

    void addBericht(int id, int gebruikerId, BerichtRequest bericht) throws CirkelsessieNotFound, GebruikerNotFound;
}
