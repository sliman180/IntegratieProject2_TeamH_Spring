package be.kdg.teamh.services.contracts;

import be.kdg.teamh.dtos.request.DeelnameRequest;
import be.kdg.teamh.exceptions.notfound.CirkelsessieNotFound;
import be.kdg.teamh.exceptions.notfound.DeelnameNotFound;
import be.kdg.teamh.exceptions.notfound.GebruikerNotFound;

public interface DeelnameService
{
    void update(int id, DeelnameRequest deelname) throws DeelnameNotFound, CirkelsessieNotFound, GebruikerNotFound;

    void delete(int id) throws DeelnameNotFound;
}
