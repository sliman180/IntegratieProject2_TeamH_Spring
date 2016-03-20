package be.kdg.teamh.services.contracts;

import be.kdg.teamh.dtos.request.RolRequest;
import be.kdg.teamh.entities.Rol;
import be.kdg.teamh.exceptions.rol.RolNietGevonden;

import java.util.List;

/**
 *
 */
public interface RolService
{
    /**
     * Haalt alle rollen op.
     *
     * @return Een lijst van {@link Rol}len
     */
    List<Rol> all();

    /**
     * Maakt een nieuwe rol aan.
     *
     * @param dto Gegevens om een nieuwe {link Rol} aan te maken
     */
    void create(RolRequest dto);

    /**
     * Haalt een specifieke rol op.
     *
     * @param id Code van de {@link Rol} die moet worden opgehaald
     * @return {@link Rol}
     * @throws RolNietGevonden
     */
    Rol find(int id) throws RolNietGevonden;

    /**
     * Past een bestaande rol aan.
     *
     * @param id Code van de {@link Rol} die moet worden aangepast
     * @param dto Gegevens om de {@link Rol} aan te passen
     * @throws RolNietGevonden
     */
    void update(int id, RolRequest dto) throws RolNietGevonden;

    /**
     * Verwijdert een bestaande rol.
     *
     * @param id Code van de {@link Rol} die moet worden verwijderd
     * @throws RolNietGevonden
     */
    void delete(int id) throws RolNietGevonden;
}
