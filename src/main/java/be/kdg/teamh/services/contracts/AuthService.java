package be.kdg.teamh.services.contracts;

import be.kdg.teamh.dtos.response.GebruikerResponse;
import be.kdg.teamh.dtos.response.LoginResponse;
import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.exceptions.notfound.GebruikerNotFound;

public interface AuthService
{
    LoginResponse generateToken(GebruikerResponse gebruiker);

    GebruikerResponse findByToken(String token) throws GebruikerNotFound;

    boolean isRegistered(String token);

    boolean isAdmin(String token);
}
