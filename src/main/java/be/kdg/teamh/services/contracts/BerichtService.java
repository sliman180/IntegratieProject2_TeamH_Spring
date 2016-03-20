package be.kdg.teamh.services.contracts;

import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.exceptions.bericht.BerichtNietGevonden;

public interface BerichtService
{
    Gebruiker getGebruiker(int id) throws BerichtNietGevonden;
}
