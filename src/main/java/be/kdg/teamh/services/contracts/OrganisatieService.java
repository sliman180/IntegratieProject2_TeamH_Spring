package be.kdg.teamh.services.contracts;

import be.kdg.teamh.dtos.request.OrganisatieRequest;
import be.kdg.teamh.entities.Hoofdthema;
import be.kdg.teamh.entities.Organisatie;
import be.kdg.teamh.exceptions.gebruiker.GebruikerNietGevonden;
import be.kdg.teamh.exceptions.organisatie.OrganisatieNietGevonden;

import java.util.List;

/**
 *
 */
public interface OrganisatieService
{
    /**
     * Haalt alle organisaties op.
     *
     * @return Een lijst van {@link Organisatie}s
     */
    List<Organisatie> all();

    /**
     * Maakt een nieuwe organisatie aan.
     *
     * @param dto Gegevens om een {@link Organisatie} aan te maken
     * @throws GebruikerNietGevonden
     */
    void create(OrganisatieRequest dto) throws GebruikerNietGevonden;

    /**
     * Haalt een specifieke organisatie op.
     *
     * @param id Code van de {@link Organisatie} dat moet worden opgehaald
     * @return {@link Organisatie}
     * @throws OrganisatieNietGevonden
     */
    Organisatie find(int id) throws OrganisatieNietGevonden;

    /**
     * Past een bestaande organisatie aan.
     *
     * @param id Code van de {@link Organisatie} die moet worden aangepast
     * @param dto Gegevens om de {@link Organisatie} aan te passen
     * @throws OrganisatieNietGevonden
     * @throws GebruikerNietGevonden
     */
    void update(int id, OrganisatieRequest dto) throws OrganisatieNietGevonden, GebruikerNietGevonden;

    /**
     * Verwijdert een bestaand organisatie.
     *
     * @param id Code van de {@link Organisatie} dat moet worden verwijderd
     * @throws OrganisatieNietGevonden
     */
    void delete(int id) throws OrganisatieNietGevonden;

    /**
     * Haalt alle hoofdthemas van een organisatie op.
     *
     * @param id Code van de {@link Organisatie} waarvan de {@link Hoofdthema}s moeten worden opgehaald
     * @return Een lijst van {@link Hoofdthema}s
     * @throws OrganisatieNietGevonden
     */
    List<Hoofdthema> findHoofdthemas(int id) throws OrganisatieNietGevonden;
}
