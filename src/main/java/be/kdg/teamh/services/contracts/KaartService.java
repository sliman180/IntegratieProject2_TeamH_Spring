package be.kdg.teamh.services.contracts;

import be.kdg.teamh.dtos.request.CommentaarRequest;
import be.kdg.teamh.dtos.request.KaartRequest;
import be.kdg.teamh.dtos.request.SpelkaartRequest;
import be.kdg.teamh.dtos.request.SubthemaRequest;
import be.kdg.teamh.entities.*;
import be.kdg.teamh.exceptions.kaart.CommentaarNietToegelaten;
import be.kdg.teamh.exceptions.cirkelsessie.CirkelsessieNietGevonden;
import be.kdg.teamh.exceptions.gebruiker.GebruikerNietGevonden;
import be.kdg.teamh.exceptions.hoofdthema.HoofdthemaNietGevonden;
import be.kdg.teamh.exceptions.kaart.KaartNietGevonden;

import java.util.List;

/**
 *
 */
public interface KaartService
{
    /**
     * Haalt alle kaarten op.
     *
     * @return Een lijst van {@link Kaart}en
     */
    List<Kaart> all();

    /**
     * Maakt een nieuwe kaart aan.
     *
     * @param dto Gegevens om een {@link Kaart} aan te maken
     * @throws GebruikerNietGevonden
     */
    void create(KaartRequest dto) throws GebruikerNietGevonden;

    /**
     * Haalt een specifieke kaart op.
     *
     * @param id Code van de {@link Kaart} die moet worden opgehaald
     * @return {@link Kaart}
     * @throws KaartNietGevonden
     */
    Kaart find(int id) throws KaartNietGevonden;

    /**
     * Past een bestaande kaart aan.
     *
     * @param id Code van de {@link Kaart} die moet worden aangepast
     * @param dto Gegevens om de {@link Kaart} aan te passen
     * @throws KaartNietGevonden
     * @throws GebruikerNietGevonden
     */
    void update(int id, KaartRequest dto) throws KaartNietGevonden, GebruikerNietGevonden;

    /**
     * Verwijdert een bestaande kaart.
     *
     * @param id Code van de {@link Kaart} die moet worden verwijderd
     * @throws KaartNietGevonden
     */
    void delete(int id) throws KaartNietGevonden;

    /**
     * Haalt het subthema van een kaart op.
     *
     * @param id Code van de {@link Kaart} waarvan het {@link Subthema} moet worden opgehaald
     * @return {@link Subthema}
     * @throws KaartNietGevonden
     */
    Subthema getSubthema(int id) throws KaartNietGevonden;

    /**
     * Haalt de gebruiker van een kaart op.
     *
     * @param id Code van de {@link Kaart} waarvan de {@link Gebruiker} moet worden opgehaald
     * @return {@link Gebruiker}
     * @throws KaartNietGevonden
     */
    Gebruiker getGebruiker(int id) throws KaartNietGevonden;

    /**
     * Haalt alle commentaren van een kaart op.
     *
     * @param id Code van de {@link Kaart} waarvan de {@link Commentaar}en moeten worden opgehaald
     * @return Een lijst van {@link Commentaar}en
     * @throws KaartNietGevonden
     */
    List<Commentaar> getCommentaren(int id) throws KaartNietGevonden;

    /**
     * Voegt een commentaar toe aan een kaart.
     *
     * @param id Code van de {@link Kaart} waaraan de {@link Commentaar} moet worden toegevoegd
     * @param dto Gegevens om de {@link Commentaar} aan te maken
     * @throws KaartNietGevonden
     * @throws GebruikerNietGevonden
     * @throws CommentaarNietToegelaten
     */
    void addCommentaar(int id, CommentaarRequest dto) throws KaartNietGevonden, GebruikerNietGevonden, CommentaarNietToegelaten;

    /**
     * Haalt alle spelkaarten van een kaart op.
     *
     * @param id Code van de {@link Kaart} waarvan de {@link Spelkaart}en moeten worden opgehaald
     * @return Een lijst van {@link Spelkaart}en
     * @throws KaartNietGevonden
     */
    List<Spelkaart> getSpelkaarten(int id) throws KaartNietGevonden;

    /**
     * Voegt een spelkaart toe aan een kaart.
     *
     * @param id Code van de {@link Kaart} waaraan de {@link Spelkaart} moet worden toegevoegd
     * @param dto Gegevens om de {@link Spelkaart} aan te maken
     * @throws KaartNietGevonden
     * @throws CirkelsessieNietGevonden
     */
    void addSpelkaart(int id, SpelkaartRequest dto) throws KaartNietGevonden, CirkelsessieNietGevonden;
}
