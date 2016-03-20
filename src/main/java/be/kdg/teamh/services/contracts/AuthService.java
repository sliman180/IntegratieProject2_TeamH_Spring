package be.kdg.teamh.services.contracts;

import be.kdg.teamh.dtos.response.LoginResponse;
import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.exceptions.gebruiker.ToegangVerboden;
import be.kdg.teamh.exceptions.gebruiker.GebruikerNietGeregistreerd;
import be.kdg.teamh.exceptions.gebruiker.GebruikerNietGevonden;

public interface AuthService
{
    LoginResponse generateToken(Gebruiker gebruiker);

    Gebruiker findByToken(String token) throws GebruikerNietGevonden;

    void checkUserIsRegistered(String token) throws GebruikerNietGeregistreerd;

    void checkUserIsAllowed(String token, Gebruiker gebruiker) throws ToegangVerboden;
}
