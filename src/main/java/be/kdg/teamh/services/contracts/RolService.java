package be.kdg.teamh.services.contracts;

import be.kdg.teamh.entities.Rol;
import be.kdg.teamh.exceptions.notfound.RolNotFound;

import java.util.List;

public interface RolService {
    List<Rol> all();

    void create(Rol rol);

    Rol find(int id) throws RolNotFound;

    void update(int id, Rol rol) throws RolNotFound;

    void delete(int id) throws RolNotFound;
}
