package be.kdg.teamh.services.contracts;

import be.kdg.teamh.dtos.request.RolRequest;
import be.kdg.teamh.entities.Rol;
import be.kdg.teamh.exceptions.rol.RolNietGevonden;

import java.util.List;

public interface RolService
{
    List<Rol> all();

    void create(RolRequest dto);

    Rol find(int id) throws RolNietGevonden;

    void update(int id, RolRequest dto) throws RolNietGevonden;

    void delete(int id) throws RolNietGevonden;
}
