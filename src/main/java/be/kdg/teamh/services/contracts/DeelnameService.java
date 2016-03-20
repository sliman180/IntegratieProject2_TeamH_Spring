package be.kdg.teamh.services.contracts;

import be.kdg.teamh.dtos.request.DeelnameRequest;
import be.kdg.teamh.entities.Cirkelsessie;
import be.kdg.teamh.entities.Deelname;
import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.exceptions.cirkelsessie.CirkelsessieNietGevonden;
import be.kdg.teamh.exceptions.deelname.DeelnameNietGevonden;
import be.kdg.teamh.exceptions.gebruiker.GebruikerNietGevonden;

public interface DeelnameService
{
    Deelname find(int id) throws DeelnameNietGevonden;

    void update(int id, DeelnameRequest dto) throws DeelnameNietGevonden, CirkelsessieNietGevonden, GebruikerNietGevonden;

    void delete(int id) throws DeelnameNietGevonden;

    Gebruiker getGebruiker(int id) throws DeelnameNietGevonden;

    Cirkelsessie getCirkelsessie(int id) throws DeelnameNietGevonden;
}
