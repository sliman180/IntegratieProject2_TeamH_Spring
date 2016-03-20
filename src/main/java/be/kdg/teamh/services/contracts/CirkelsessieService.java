package be.kdg.teamh.services.contracts;

import be.kdg.teamh.dtos.request.BerichtRequest;
import be.kdg.teamh.dtos.request.CirkelsessieCloneRequest;
import be.kdg.teamh.dtos.request.CirkelsessieRequest;
import be.kdg.teamh.dtos.request.KaartRequest;
import be.kdg.teamh.entities.*;
import be.kdg.teamh.exceptions.gebruiker.GebruikerIsReedsDeelnemer;
import be.kdg.teamh.exceptions.cirkelsessie.CirkelsessieNietGevonden;
import be.kdg.teamh.exceptions.gebruiker.GebruikerNietGevonden;
import be.kdg.teamh.exceptions.subthema.SubthemaNietGevonden;

import java.util.List;

public interface CirkelsessieService
{
    List<Cirkelsessie> all();

    List<Cirkelsessie> actief();

    List<Cirkelsessie> gesloten();

    List<Cirkelsessie> beindigd();

    List<Cirkelsessie> gepland();

    void create(CirkelsessieRequest dto) throws GebruikerNietGevonden, SubthemaNietGevonden;

    Cirkelsessie find(int id) throws CirkelsessieNietGevonden;

    void update(int id, CirkelsessieRequest dto) throws CirkelsessieNietGevonden;

    void delete(int id) throws CirkelsessieNietGevonden;

    void clone(int id, CirkelsessieCloneRequest dto) throws CirkelsessieNietGevonden;

    Subthema findSubthema(int id) throws CirkelsessieNietGevonden;

    List<Deelname> findDeelnames(int id) throws CirkelsessieNietGevonden;

    void addDeelname(int id, int gebruikerId) throws CirkelsessieNietGevonden, GebruikerNietGevonden, GebruikerIsReedsDeelnemer;

    List<Spelkaart> findSpelkaarten(int id) throws CirkelsessieNietGevonden;

    void addSpelkaart(int id, KaartRequest dto) throws CirkelsessieNietGevonden, GebruikerNietGevonden;

    List<Bericht> findBerichten(int id) throws CirkelsessieNietGevonden;

    void addBericht(int id, BerichtRequest dto) throws CirkelsessieNietGevonden, GebruikerNietGevonden;

    Gebruiker findGebruiker(int id) throws CirkelsessieNietGevonden;
}
