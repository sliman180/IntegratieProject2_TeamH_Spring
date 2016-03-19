package be.kdg.teamh.services.contracts;

import be.kdg.teamh.dtos.request.GebruikerRequest;
import be.kdg.teamh.dtos.request.LoginRequest;
import be.kdg.teamh.dtos.request.RegistratieRequest;
import be.kdg.teamh.entities.*;
import be.kdg.teamh.exceptions.InvalidCredentials;
import be.kdg.teamh.exceptions.PasswordsDoNotMatch;
import be.kdg.teamh.exceptions.notfound.GebruikerNotFound;
import be.kdg.teamh.exceptions.notfound.RolNotFound;

import java.util.List;

public interface GebruikerService
{
    List<Gebruiker> all();

    void create(GebruikerRequest dto) throws RolNotFound;

    void register(RegistratieRequest dto) throws RolNotFound, PasswordsDoNotMatch;

    Gebruiker find(int id) throws GebruikerNotFound;

    Gebruiker findByLogin(LoginRequest login) throws GebruikerNotFound, InvalidCredentials;

    void update(int id, GebruikerRequest dto) throws GebruikerNotFound;

    void delete(int id) throws GebruikerNotFound;

    List<Organisatie> getOrganisaties(int id) throws GebruikerNotFound;

    List<Cirkelsessie> getCirkelsessies(int id) throws GebruikerNotFound;

    List<Deelname> getDeelnames(int id) throws GebruikerNotFound;

    List<Hoofdthema> getHoofdthemas(int id) throws GebruikerNotFound;

    List<Subthema> getSubthemas(int id) throws GebruikerNotFound;
}
