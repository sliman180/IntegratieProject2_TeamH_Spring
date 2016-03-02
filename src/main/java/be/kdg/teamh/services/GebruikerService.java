package be.kdg.teamh.services;

import be.kdg.teamh.entities.Cirkelsessie;
import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.exceptions.GebruikerNotFound;

import java.util.List;

public interface GebruikerService {
    void create(Gebruiker gebruiker);

    List<Cirkelsessie> showCirkelsessies(int id) throws GebruikerNotFound;
}
