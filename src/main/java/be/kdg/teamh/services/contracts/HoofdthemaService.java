package be.kdg.teamh.services.contracts;

import be.kdg.teamh.dtos.request.HoofdthemaRequest;
import be.kdg.teamh.entities.Hoofdthema;
import be.kdg.teamh.entities.Organisatie;
import be.kdg.teamh.entities.Subthema;
import be.kdg.teamh.exceptions.gebruiker.GebruikerNietGevonden;
import be.kdg.teamh.exceptions.hoofdthema.HoofdthemaNietGevonden;
import be.kdg.teamh.exceptions.organisatie.OrganisatieNietGevonden;

import java.util.List;

/**
 *
 */
public interface HoofdthemaService
{
    /**
     * Haalt alle hoofdthemas op.
     *
     * @return Een lijst van {@link Hoofdthema}s
     */
    List<Hoofdthema> all();

    /**
     * Maakt een nieuw hoofdthema aan.
     *
     * @param dto Gegevens om een {@link Hoofdthema} aan te maken.
     * @throws OrganisatieNietGevonden
     * @throws GebruikerNietGevonden
     */
    void create(HoofdthemaRequest dto) throws OrganisatieNietGevonden, GebruikerNietGevonden;

    /**
     * Haalt een specifiek hoofdthema op.
     *
     * @param id Code van het {@link Hoofdthema} dat moet worden opgehaald
     * @return {@link Hoofdthema}
     * @throws HoofdthemaNietGevonden
     */
    Hoofdthema find(int id) throws HoofdthemaNietGevonden;

    /**
     * Past een bestaand hoofdthema aan.
     *
     * @param id Code van het {@link Hoofdthema} dat moet worden aangepast
     * @param dto Gegevens om het {@link Hoofdthema} aan te passen
     * @throws HoofdthemaNietGevonden
     * @throws OrganisatieNietGevonden
     * @throws GebruikerNietGevonden
     */
    void update(int id, HoofdthemaRequest dto) throws HoofdthemaNietGevonden, OrganisatieNietGevonden, GebruikerNietGevonden;

    /**
     * Verwijdert een bestaand hoofdthema.
     *
     * @param id Code van het {@link Hoofdthema} dat moet worden verwijderd
     * @throws HoofdthemaNietGevonden
     */
    void delete(int id) throws HoofdthemaNietGevonden;

    /**
     * Haalt de organisatie van een hoofdthema op.
     *
     * @param id Code van het {@link Hoofdthema} waarvan de {@link Organisatie} moet worden opgehaald
     * @return {@link Organisatie}
     * @throws HoofdthemaNietGevonden
     */
    Organisatie findOrganisatie(int id) throws HoofdthemaNietGevonden;

    /**
     * Haalt alle cirkelsessies van een subthema op.
     *
     * @param id Code van het {@link Hoofdthema} waarvan de {@link Subthema}s moeten worden opgehaald
     * @return Een lijst van {@link Subthema}s
     * @throws HoofdthemaNietGevonden
     */
    List<Subthema> findSubthemas(int id) throws HoofdthemaNietGevonden;
}
