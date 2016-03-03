package be.kdg.teamh.services.contracts;

import be.kdg.teamh.entities.Deelname;
import be.kdg.teamh.exceptions.DeelnameNotFound;

import java.util.List;

public interface DeelnameService
{
    List<Deelname> all();

    void create(Deelname deelname);

    Deelname find(int id) throws DeelnameNotFound;

    void update(int id, Deelname deelname) throws DeelnameNotFound;

    void delete(int id) throws DeelnameNotFound;
}
