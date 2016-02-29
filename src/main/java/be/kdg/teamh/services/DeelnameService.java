package be.kdg.teamh.services;

import be.kdg.teamh.entities.Deelname;
import be.kdg.teamh.exceptions.DeelnameNotFound;

public interface DeelnameService {
    Deelname find(int id) throws DeelnameNotFound;
    void create(Deelname deelname);
}
