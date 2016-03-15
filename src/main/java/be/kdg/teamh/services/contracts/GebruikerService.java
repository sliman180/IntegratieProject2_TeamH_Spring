package be.kdg.teamh.services.contracts;

import be.kdg.teamh.dtos.request.GebruikerRequest;
import be.kdg.teamh.dtos.request.LoginRequest;
import be.kdg.teamh.dtos.request.RegistratieRequest;
import be.kdg.teamh.dtos.response.CirkelsessieResponse;
import be.kdg.teamh.dtos.response.DeelnameResponse;
import be.kdg.teamh.dtos.response.GebruikerResponse;
import be.kdg.teamh.dtos.response.OrganisatieResponse;
import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.exceptions.InvalidCredentials;
import be.kdg.teamh.exceptions.PasswordsDoNotMatch;
import be.kdg.teamh.exceptions.notfound.GebruikerNotFound;
import be.kdg.teamh.exceptions.notfound.RolNotFound;

import java.util.List;

public interface GebruikerService
{
    List<GebruikerResponse> all();

    void create(GebruikerRequest gebruiker) throws RolNotFound;

    void register(RegistratieRequest registratie) throws RolNotFound, PasswordsDoNotMatch;

    GebruikerResponse find(int id) throws GebruikerNotFound;

    GebruikerResponse findByLogin(LoginRequest login) throws GebruikerNotFound, InvalidCredentials;

    void update(int id, GebruikerRequest gebruiker) throws GebruikerNotFound;

    void update(int id, Gebruiker gebruiker) throws GebruikerNotFound;

    void delete(int id) throws GebruikerNotFound;

    List<OrganisatieResponse> findOrganisaties(int id) throws GebruikerNotFound;

    List<CirkelsessieResponse> findCirkelsessies(int id) throws GebruikerNotFound;

    List<DeelnameResponse> findDeelnames(int id) throws GebruikerNotFound;
}
