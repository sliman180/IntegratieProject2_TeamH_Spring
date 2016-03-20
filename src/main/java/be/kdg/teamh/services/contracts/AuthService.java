package be.kdg.teamh.services.contracts;

import be.kdg.teamh.dtos.response.LoginResponse;
import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.exceptions.Forbidden;
import be.kdg.teamh.exceptions.Unauthorized;
import be.kdg.teamh.exceptions.notfound.GebruikerNotFound;

public interface AuthService
{
    LoginResponse generateToken(Gebruiker gebruiker);

    Gebruiker findByToken(String token) throws GebruikerNotFound;

    void checkUserIsRegistered(String token) throws Unauthorized;

    void checkUserIsAllowed(String token, Gebruiker gebruiker) throws Forbidden;
}
