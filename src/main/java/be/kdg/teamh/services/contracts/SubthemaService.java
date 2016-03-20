package be.kdg.teamh.services.contracts;

import be.kdg.teamh.dtos.request.KaartRequest;
import be.kdg.teamh.dtos.request.SubthemaRequest;
import be.kdg.teamh.entities.*;
import be.kdg.teamh.exceptions.gebruiker.GebruikerNietGevonden;
import be.kdg.teamh.exceptions.hoofdthema.HoofdthemaNietGevonden;
import be.kdg.teamh.exceptions.subthema.SubthemaNietGevonden;

import java.util.List;

/**
 *
 */
public interface SubthemaService
{
    /**
     * Haalt alle subthemas op.
     *
     * @return Een lijst van {@link Subthema}s
     */
    List<Subthema> all();

    /**
     * Maakt een nieuw subthema aan.
     *
     * @param dto Gegevens om een {@link Subthema} aan te maken
     * @throws HoofdthemaNietGevonden
     * @throws GebruikerNietGevonden
     */
    void create(SubthemaRequest dto) throws HoofdthemaNietGevonden, GebruikerNietGevonden;

    /**
     * Haalt een specifiek subthema op.
     *
     * @param id Code van het {@link Subthema} dat moet worden opgehaald
     * @return {@link Subthema}
     * @throws SubthemaNietGevonden
     */
    Subthema find(int id) throws SubthemaNietGevonden;

    /**
     * Past een bestaand subthema aan.
     *
     * @param id Code van het {@link Subthema} dat moet worden aangepast
     * @param dto Gegevens om het {@link Subthema} aan te passen
     * @throws SubthemaNietGevonden
     * @throws HoofdthemaNietGevonden
     * @throws GebruikerNietGevonden
     */
    void update(int id, SubthemaRequest dto) throws SubthemaNietGevonden, HoofdthemaNietGevonden, GebruikerNietGevonden;

    /**
     * Verwijdert een bestaand subthema.
     *
     * @param id Code van het {@link Subthema} dat moet worden verwijderd
     * @throws SubthemaNietGevonden
     */
    void delete(int id) throws SubthemaNietGevonden;

    /**
     * Haalt de organisatie van een subthema op.
     *
     * @param id Code van het {@link Subthema} waarvan de {@link Organisatie} moet worden opgehaald
     * @return {@link Organisatie}
     * @throws SubthemaNietGevonden
     */
    Organisatie findOrganisatie(int id) throws SubthemaNietGevonden;

    /**
     * Haalt het hoofdthema van een subthema op.
     *
     * @param id Code van het {@link Subthema} waarvan de {@link Hoofdthema} moet worden opgehaald
     * @return {@link Hoofdthema}
     * @throws SubthemaNietGevonden
     */
    Hoofdthema findHoofdthema(int id) throws SubthemaNietGevonden;

    /**
     * Haalt alle cirkelsessies van een subthema op.
     *
     * @param id Code van het {@link Subthema} waarvan de {@link Cirkelsessie}s moeten worden opgehaald
     * @return Een lijst van {@link Cirkelsessie}s
     * @throws SubthemaNietGevonden
     */
    List<Cirkelsessie> getCirkelsessies(int id) throws SubthemaNietGevonden;

    /**
     * Haalt alle kaarten van een subthema op.
     *
     * @param id Code van het {@link Subthema} waarvan de {@link Kaart} moeten worden opgehaald
     * @return Een lijst van {@link Kaart}en
     * @throws SubthemaNietGevonden
     */
    List<Kaart> getKaarten(int id) throws SubthemaNietGevonden;

    /**
     * Voegt een kaart toe aan een subthema.
     *
     * @param id Code van het {@link Subthema} waaraan de {@link Kaart} moet worden toegevoegd
     * @param kaart Gegevens om de {@link Kaart} aan te maken
     * @throws SubthemaNietGevonden
     * @throws GebruikerNietGevonden
     */
    void addKaart(int id, KaartRequest kaart) throws SubthemaNietGevonden, GebruikerNietGevonden;
}
