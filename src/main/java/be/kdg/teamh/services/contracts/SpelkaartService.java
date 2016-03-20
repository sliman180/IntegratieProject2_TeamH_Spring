package be.kdg.teamh.services.contracts;

import be.kdg.teamh.dtos.request.SpelkaartRequest;
import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.entities.Kaart;
import be.kdg.teamh.entities.Spelkaart;
import be.kdg.teamh.exceptions.gebruiker.GebruikerIsGeenDeelnemer;
import be.kdg.teamh.exceptions.spelkaart.MaximumPositieBereikt;
import be.kdg.teamh.exceptions.cirkelsessie.CirkelsessieNietGevonden;
import be.kdg.teamh.exceptions.gebruiker.GebruikerNietGevonden;
import be.kdg.teamh.exceptions.kaart.KaartNietGevonden;
import be.kdg.teamh.exceptions.spelkaart.SpelkaartNietGevonden;

import java.util.List;

/**
 *
 */
public interface SpelkaartService
{
    /**
     * Haalt alle spelkaarten op.
     *
     * @return Een lijst van {@link Spelkaart}en
     */
    List<Spelkaart> all();

    /**
     * Maakt een nieuwe spelkaart aan.
     *
     * @param dto Gegevens om een {@link Spelkaart} aan te maken
     * @throws CirkelsessieNietGevonden
     * @throws KaartNietGevonden
     */
    void create(SpelkaartRequest dto) throws CirkelsessieNietGevonden, KaartNietGevonden;

    /**
     * Haalt een specifieke spelkaart op.
     *
     * @param id Code van de {@link Spelkaart} die moet worden opgehaald
     * @return {@link Spelkaart}
     * @throws SpelkaartNietGevonden
     */
    Spelkaart find(int id) throws SpelkaartNietGevonden;

    /**
     * Past een bestaande spelkaart aan.
     *
     * @param id Code van de {@link Spelkaart} die moet worden aangepast
     * @param dto Gegevens om de {@link Spelkaart} aan te passen
     * @throws SpelkaartNietGevonden
     * @throws CirkelsessieNietGevonden
     * @throws KaartNietGevonden
     */
    void update(int id, SpelkaartRequest dto) throws SpelkaartNietGevonden, CirkelsessieNietGevonden, KaartNietGevonden;

    /**
     * Verwijdert een bestaande spelkaart.
     *
     * @param id Code van de {@link Spelkaart} die moet worden verwijderd
     * @throws SpelkaartNietGevonden
     */
    void delete(int id) throws SpelkaartNietGevonden;

    /**
     * Verschuift een kaart één positie.
     *
     * @param id Code van de {@link Spelkaart} die moet worden verschoven
     * @param gebruiker Gebruiker die kaart verschuift
     * @throws SpelkaartNietGevonden
     * @throws MaximumPositieBereikt
     * @throws GebruikerIsGeenDeelnemer
     * @throws GebruikerNietGevonden
     */
    void verschuif(int id, Gebruiker gebruiker) throws SpelkaartNietGevonden, MaximumPositieBereikt, GebruikerIsGeenDeelnemer, GebruikerNietGevonden;

    /**
     * Haalt de kaart van een spelkaart op.
     *
     * @param id Code van de {@link Spelkaart} waarvan de {@link Kaart} moet worden opgehaald
     * @return {@link Kaart}
     * @throws SpelkaartNietGevonden
     */
    Kaart getKaart(int id) throws SpelkaartNietGevonden;
}
