package be.kdg.teamh.services.contracts;

import be.kdg.teamh.dtos.response.LoginResponse;
import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.exceptions.notfound.GebruikerNotFound;

public interface AuthService
{
    LoginResponse generateToken(Gebruiker gebruiker);

    Gebruiker findByToken(String token) throws GebruikerNotFound;

    void checkUserIsRegistered(String token);

    void checkUserIsAllowed(String token, Gebruiker gebruiker);
}
