package be.kdg.teamh.services.contracts;

import be.kdg.teamh.dtos.request.BerichtRequest;
import be.kdg.teamh.dtos.request.CirkelsessieCloneRequest;
import be.kdg.teamh.dtos.request.CirkelsessieRequest;
import be.kdg.teamh.dtos.request.KaartRequest;
import be.kdg.teamh.entities.*;
import be.kdg.teamh.exceptions.deelname.DeelnameNietGevonden;
import be.kdg.teamh.exceptions.gebruiker.GebruikerIsReedsDeelnemer;
import be.kdg.teamh.exceptions.cirkelsessie.CirkelsessieNietGevonden;
import be.kdg.teamh.exceptions.gebruiker.GebruikerNietGevonden;
import be.kdg.teamh.exceptions.subthema.SubthemaNietGevonden;

import java.util.List;

/**
 *
 */
public interface CirkelsessieService
{
    /**
     * Haalt alle cirkelsessies op.
     *
     * @return Een lijst van {@link Cirkelsessie}s
     */
    List<Cirkelsessie> all();

    /**
     * Haalt alle actieve cirkelsessies op.
     *
     * @return Een lijst van {@link Cirkelsessie}s
     */
    List<Cirkelsessie> actief();

    /**
     * Haalt alle gesloten cirkelsessies op.
     *
     * @return Een lijst van {@link Cirkelsessie}s
     */
    List<Cirkelsessie> gesloten();

    /**
     * Haalt alle beindigde cirkelsessies op.
     *
     * @return Een lijst van {@link Cirkelsessie}s
     */
    List<Cirkelsessie> beindigd();

    /**
     * Haalt alle geplande cirkelsessies op.
     *
     * @return Een lijst van {@link Cirkelsessie}s
     */
    List<Cirkelsessie> gepland();

    /**
     * Maakt een nieuw cirkelsessie aan.
     *
     * @param dto Gegevens om een {@link Cirkelsessie} aan te maken
     * @throws GebruikerNietGevonden
     * @throws SubthemaNietGevonden
     */
    void create(CirkelsessieRequest dto) throws GebruikerNietGevonden, SubthemaNietGevonden;

    /**
     * Haalt een specifieke cirkelsessie op.
     *
     * @param id Code van de {@link Cirkelsessie} die moet worden opgehaald
     * @return {@link Cirkelsessie}
     * @throws CirkelsessieNietGevonden
     */
    Cirkelsessie find(int id) throws CirkelsessieNietGevonden;

    /**
     * Past een bestaande cirkelsessie aan.
     *
     * @param id Code van de {@link Cirkelsessie} die moet worden aangepast
     * @param dto Gegevens om het {@link Cirkelsessie} aan te passen
     * @throws CirkelsessieNietGevonden
     */
    void update(int id, CirkelsessieRequest dto) throws CirkelsessieNietGevonden;

    /**
     * Verwijdert een bestaande cirkelsessie.
     *
     * @param id Code van de {@link Cirkelsessie} die moet worden verwijderd
     * @throws CirkelsessieNietGevonden
     */
    void delete(int id) throws CirkelsessieNietGevonden;

    /**
     * Kloont een bestaande cirkelsessie zodat de deelnemers van de oude cirkelsessie
     * niet opnieuw moeten deelnemen aan de nieuwe cirkelsessie.
     *
     * @param id Code van de {@link Cirkelsessie} die moet worden gekloond
     * @param dto Gegevens om de gekloonde {@link Cirkelsessie} aan te maken
     * @throws CirkelsessieNietGevonden
     */
    void clone(int id, CirkelsessieCloneRequest dto) throws CirkelsessieNietGevonden;

    /**
     * Haalt het subthema van een cirkelsessie op.
     *
     * @param id Code van de {@link Cirkelsessie} waarvan het {@link Subthema} moet worden opgehaald
     * @return {@link Subthema}
     * @throws CirkelsessieNietGevonden
     */
    Subthema findSubthema(int id) throws CirkelsessieNietGevonden;

    /**
     * Haalt de gebruiker van een cirkelsessie op.
     *
     * @param id Code van de {@link Cirkelsessie} waarvan de {@link Gebruiker} moet worden opgehaald
     * @return {@link Gebruiker}
     * @throws CirkelsessieNietGevonden
     */
    Gebruiker findGebruiker(int id) throws CirkelsessieNietGevonden;

    /**
     * Haalt alle deelnames van een cirkelsessie op.
     *
     * @param id Code van de {@link Cirkelsessie} waarvan de {@link Deelname}s moeten worden opgehaald
     * @return Een lijst van {@link Deelname}s
     * @throws CirkelsessieNietGevonden
     */
    List<Deelname> findDeelnames(int id) throws CirkelsessieNietGevonden;

    /**
     * Voegt een deelname toe aan een cirkelsessie.
     *
     * @param id Code van de {@link Cirkelsessie} waaraan de {@link Deelname} moet worden toegevoegd
     * @param gebruikerId Code van de {@link Gebruiker} waaraan de {@link Deelname} moet worden gelinkt
     * @throws CirkelsessieNietGevonden
     * @throws GebruikerNietGevonden
     * @throws GebruikerIsReedsDeelnemer
     */
    void addDeelname(int id, int gebruikerId) throws CirkelsessieNietGevonden, GebruikerNietGevonden, GebruikerIsReedsDeelnemer;

    /**
     * Haalt alle spelkaarten van een cirkelsessie op.
     *
     * @param id Code van de {@link Cirkelsessie} waarvan de {@link Spelkaart}en moeten worden opgehaald
     * @return Een lijst van {@link Spelkaart}en
     * @throws CirkelsessieNietGevonden
     */
    List<Spelkaart> findSpelkaarten(int id) throws CirkelsessieNietGevonden;

    /**
     * Voegt een spelkaart toe aan een cirkelsessie.
     *
     * @param id Code van de {@link Cirkelsessie} waaraan de {@link Spelkaart} moet worden toegevoegd
     * @param dto Gegevens om de {@link Spelkaart} aan te maken
     * @throws CirkelsessieNietGevonden
     * @throws GebruikerNietGevonden
     */
    void addSpelkaart(int id, KaartRequest dto) throws CirkelsessieNietGevonden, GebruikerNietGevonden;

    /**
     * Haalt alle berichten van een cirkelsessie op.
     *
     * @param id Code van de {@link Cirkelsessie} waarvan de {@link Bericht}en moeten worden opgehaald
     * @return Een lijst van {@link Bericht}en
     * @throws CirkelsessieNietGevonden
     */
    List<Bericht> findBerichten(int id) throws CirkelsessieNietGevonden;

    /**
     * Voegt een bericht toe aan een cirkelsessie.
     *
     * @param id Code van de {@link Cirkelsessie} waaraan het {@link Bericht} moet worden toegevoegd
     * @param dto Gegevens om het {@link Bericht} aan te maken
     * @throws CirkelsessieNietGevonden
     * @throws GebruikerNietGevonden
     */
    void addBericht(int id, BerichtRequest dto) throws CirkelsessieNietGevonden, GebruikerNietGevonden;

    /**
     * Kijkt na of een gebruiker een medeorganisator is van een cirkelsessie.
     *
     * @param id Code van de {@link Deelname} waarmee de huidige cirkelsessie kan opgezocht worden
     * @param gebruiker {@link Gebruiker} waarvan moet nagekeken worden of hij medeorganisator is
     * @return true als de gebruiker een medeorganisator is, false als hij het niet is
     * @throws DeelnameNietGevonden
     */
    boolean isMedeOrganisator(int id, int gebruiker)  throws DeelnameNietGevonden;
}
