package be.kdg.teamh.services.contracts;

import be.kdg.teamh.dtos.response.LoginResponse;
import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.exceptions.gebruiker.ToegangVerboden;
import be.kdg.teamh.exceptions.gebruiker.GebruikerNietGeregistreerd;
import be.kdg.teamh.exceptions.gebruiker.GebruikerNietGevonden;

/**
 *
 */
public interface AuthService
{
    /**
     * Genereert een JWT voor de gebruiker.
     *
     * @param gebruiker Aangemelde gebruiker
     * @return {@link LoginResponse}
     */
    LoginResponse genereerToken(Gebruiker gebruiker);

    /**
     * Haalt een specifieke gebruiker op basis van de JWT.
     *
     * @param token JWT die behoort tot een gebruiker
     * @return {@link Gebruiker}
     * @throws GebruikerNietGevonden
     */
    Gebruiker zoekGebruikerMetToken(String token) throws GebruikerNietGevonden;

    /**
     * Controleert of een gebruiker is geregistreerd.
     *
     * @param token JWT die behoort tot een gebruiker
     * @throws GebruikerNietGeregistreerd
     */
    void isGeregistreerd(String token) throws GebruikerNietGeregistreerd;

    /**
     * Controleert of een gebruiker eigenaar is van een resource.
     *
     * @param token JWT die behoort tot een gebruiker
     * @param gebruiker Eigenaar van de beveiligde resource
     * @throws ToegangVerboden
     */
    void isEigenaar(String token, Gebruiker gebruiker) throws ToegangVerboden;
}
