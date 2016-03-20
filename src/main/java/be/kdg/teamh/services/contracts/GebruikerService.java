package be.kdg.teamh.services.contracts;

import be.kdg.teamh.dtos.request.GebruikerRequest;
import be.kdg.teamh.dtos.request.LoginRequest;
import be.kdg.teamh.dtos.request.RegistratieRequest;
import be.kdg.teamh.entities.*;
import be.kdg.teamh.exceptions.gebruiker.OngeldigeGegevens;
import be.kdg.teamh.exceptions.gebruiker.WachtwoordenKomenNietOvereen;
import be.kdg.teamh.exceptions.gebruiker.GebruikerNietGevonden;
import be.kdg.teamh.exceptions.rol.RolNietGevonden;

import java.util.List;

/**
 *
 */
public interface GebruikerService
{
    /**
     * Haalt alle gebruikers op.
     *
     * @return Een lijst van {@link Gebruiker}s
     */
    List<Gebruiker> all();

    /**
     * Maakt een nieuwe gebruiker aan.
     *
     * @param dto Gegevens om een {@link Gebruiker} aan te maken
     * @throws RolNietGevonden
     */
    void create(GebruikerRequest dto) throws RolNietGevonden;

    /**
     * Registreert een nieuwe gebruiker.
     * Deze methode kijkt na of de wachtwoorden overeen komen en roept daarna de methode {@link #create} op.
     *
     * @param dto Gegevens om een {@link Gebruiker} aan te maken
     * @throws RolNietGevonden
     * @throws WachtwoordenKomenNietOvereen
     */
    void register(RegistratieRequest dto) throws RolNietGevonden, WachtwoordenKomenNietOvereen;

    /**
     * Haalt een specifieke gebruiker op.
     *
     * @param id Code van de {@link Gebruiker} die moet worden opgehaald
     * @return {@link Gebruiker}
     * @throws GebruikerNietGevonden
     */
    Gebruiker find(int id) throws GebruikerNietGevonden;

    /**
     * Haalt een specifieke gebruiker op op basis van zijn aanmeldingsgegevens.
     *
     * @param login Gegevens waarmee gebruiker zich aanmeldt
     * @return {@link Gebruiker}
     * @throws GebruikerNietGevonden
     * @throws OngeldigeGegevens
     */
    Gebruiker findByLogin(LoginRequest login) throws GebruikerNietGevonden, OngeldigeGegevens;

    /**
     * Past een bestaande gebruiker aan.
     *
     * @param id Code van de {@link Gebruiker} die moet worden aangepast
     * @param dto Gegevens om de {@link Gebruiker} aan te passen
     * @throws GebruikerNietGevonden
     */
    void update(int id, GebruikerRequest dto) throws GebruikerNietGevonden;

    /**
     * Verwijdert een bestaande gebruiker.
     *
     * @param id Code van de {@link Gebruiker} die moet worden verwijderd
     * @throws GebruikerNietGevonden
     */
    void delete(int id) throws GebruikerNietGevonden;

    /**
     * Haalt alle organisaties van een gebruiker op.
     *
     * @param id Code van de {@link Gebruiker} waarvan de {@link Organisatie}s moeten worden opgehaald
     * @return Een lijst van {@link Organisatie}s
     * @throws GebruikerNietGevonden
     */
    List<Organisatie> getOrganisaties(int id) throws GebruikerNietGevonden;

    /**
     * Haalt alle hoofdthemas van een gebruiker op.
     *
     * @param id Code van de {@link Gebruiker} waarvan de {@link Hoofdthema}s moeten worden opgehaald
     * @return Een lijst van {@link Hoofdthema}s
     * @throws GebruikerNietGevonden
     */
    List<Hoofdthema> getHoofdthemas(int id) throws GebruikerNietGevonden;

    /**
     * Haalt alle subthemas van een gebruiker op.
     *
     * @param id Code van de {@link Gebruiker} waarvan de {@link Subthema}s moeten worden opgehaald
     * @return Een lijst van {@link Subthema}s
     * @throws GebruikerNietGevonden
     */
    List<Subthema> getSubthemas(int id) throws GebruikerNietGevonden;

    /**
     * Haalt alle cirkelsessies van een gebruiker op.
     *
     * @param id Code van de {@link Gebruiker} waarvan de {@link Cirkelsessie}s moeten worden opgehaald
     * @return Een lijst van {@link Cirkelsessie}s
     * @throws GebruikerNietGevonden
     */
    List<Cirkelsessie> getCirkelsessies(int id) throws GebruikerNietGevonden;

    /**
     * Haalt alle deelnames van een gebruiker op.
     *
     * @param id Code van de {@link Gebruiker} waarvan de {@link Deelname}s moeten worden opgehaald
     * @return Een lijst van {@link Deelname}s
     * @throws GebruikerNietGevonden
     */
    List<Deelname> getDeelnames(int id) throws GebruikerNietGevonden;
}
