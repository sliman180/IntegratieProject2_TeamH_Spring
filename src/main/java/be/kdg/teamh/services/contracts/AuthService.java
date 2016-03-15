package be.kdg.teamh.services.contracts;


import be.kdg.teamh.dtos.Token;
import be.kdg.teamh.entities.Gast;
import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.exceptions.GebruikerNotFound;

public interface AuthService
{
    Token generateToken(Gebruiker gebruiker);

    Gebruiker findByToken(String token) throws GebruikerNotFound, GebruikerNotFound;

    Gast findGastByToken(String token) throws GebruikerNotFound;

    boolean isGuest(String token);

    boolean isRegistered(String token);

    boolean isAdmin(String token);
}
