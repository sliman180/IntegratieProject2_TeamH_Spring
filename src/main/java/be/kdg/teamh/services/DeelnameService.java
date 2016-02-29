package be.kdg.teamh.services;

import be.kdg.teamh.entities.Deelname;
import be.kdg.teamh.exceptions.DeelnameNotFound;

import java.util.List;

public interface DeelnameService {
    Deelname find(int id) throws DeelnameNotFound;
    void create(Deelname deelname);
    List<Deelname> all();
    void update(int id, Deelname deelname) throws DeelnameNotFound;
    void delete(int id) throws DeelnameNotFound;
}
