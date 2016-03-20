package be.kdg.teamh.services.contracts;

import be.kdg.teamh.dtos.request.DeelnameRequest;
import be.kdg.teamh.entities.Cirkelsessie;
import be.kdg.teamh.entities.Deelname;
import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.exceptions.cirkelsessie.CirkelsessieNietGevonden;
import be.kdg.teamh.exceptions.deelname.DeelnameNietGevonden;
import be.kdg.teamh.exceptions.gebruiker.GebruikerNietGevonden;

/**
 *
 */
public interface DeelnameService
{
    /**
     * Haalt een specifieke deelname op.
     *
     * @param id Code van de {@link Deelname} die moet worden opgehaald
     * @return {@link Deelname}
     * @throws DeelnameNietGevonden
     */
    Deelname find(int id) throws DeelnameNietGevonden;

    /**
     * Past een bestaande deelname aan.
     *
     * @param id Code van de {@link Deelname} die moet worden aangepast
     * @param dto Gegevens om de {@link Deelname} aan te passen
     * @throws DeelnameNietGevonden
     * @throws CirkelsessieNietGevonden
     * @throws GebruikerNietGevonden
     */
    void update(int id, DeelnameRequest dto) throws DeelnameNietGevonden, CirkelsessieNietGevonden, GebruikerNietGevonden;

    /**
     * Verwijdert een bestaand subthema.
     *
     * @param id Code van de {@link Deelname} die moet worden verwijderd
     * @throws DeelnameNietGevonden
     */
    void delete(int id) throws DeelnameNietGevonden;

    /**
     * Haalt de gebruiker van een deelname op.
     *
     * @param id Code van de {@link Deelname} waarvan de {@link Gebruiker} moet worden opgehaald
     * @return {@link Gebruiker}
     * @throws DeelnameNietGevonden
     */
    Gebruiker getGebruiker(int id) throws DeelnameNietGevonden;

    /**
     * Haalt de cirkelsessie van een deelname op.
     *
     * @param id Code van de {@link Deelname} waarvan de {@link Cirkelsessie} moet worden opgehaald
     * @return {@link Cirkelsessie}
     * @throws DeelnameNietGevonden
     */
    Cirkelsessie getCirkelsessie(int id) throws DeelnameNietGevonden;
}
