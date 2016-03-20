package be.kdg.teamh.services.contracts;

import be.kdg.teamh.dtos.request.GebruikerRequest;
import be.kdg.teamh.dtos.request.LoginRequest;
import be.kdg.teamh.dtos.request.RegistratieRequest;
import be.kdg.teamh.entities.*;
import be.kdg.teamh.exceptions.gebruiker.OngeldigeGegevens;
import be.kdg.teamh.exceptions.gebruiker.WachtwoordenKomenNietOvereen;
import be.kdg.teamh.exceptions.gebruiker.GebruikerNietGevonden;
import be.kdg.teamh.exceptions.rol.RolNietGevonden;

import java.util.List;

public interface GebruikerService
{
    List<Gebruiker> all();

    void create(GebruikerRequest dto) throws RolNietGevonden;

    void register(RegistratieRequest dto) throws RolNietGevonden, WachtwoordenKomenNietOvereen;

    Gebruiker find(int id) throws GebruikerNietGevonden;

    Gebruiker findByLogin(LoginRequest login) throws GebruikerNietGevonden, OngeldigeGegevens;

    void update(int id, GebruikerRequest dto) throws GebruikerNietGevonden;

    void delete(int id) throws GebruikerNietGevonden;

    List<Organisatie> getOrganisaties(int id) throws GebruikerNietGevonden;

    List<Cirkelsessie> getCirkelsessies(int id) throws GebruikerNietGevonden;

    List<Deelname> getDeelnames(int id) throws GebruikerNietGevonden;

    List<Hoofdthema> getHoofdthemas(int id) throws GebruikerNietGevonden;

    List<Subthema> getSubthemas(int id) throws GebruikerNietGevonden;
}
