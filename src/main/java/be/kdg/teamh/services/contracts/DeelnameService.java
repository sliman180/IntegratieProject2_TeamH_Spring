package be.kdg.teamh.services.contracts;

import be.kdg.teamh.dtos.request.DeelnameRequest;
import be.kdg.teamh.entities.Cirkelsessie;
import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.exceptions.notfound.CirkelsessieNotFound;
import be.kdg.teamh.exceptions.notfound.DeelnameNotFound;
import be.kdg.teamh.exceptions.notfound.GebruikerNotFound;

public interface DeelnameService
{
    void update(int id, DeelnameRequest dto) throws DeelnameNotFound, CirkelsessieNotFound, GebruikerNotFound;

    void delete(int id) throws DeelnameNotFound;

    Gebruiker getGebruiker(int id) throws DeelnameNotFound;

    Cirkelsessie getCirkelsessie(int id) throws DeelnameNotFound;
}
