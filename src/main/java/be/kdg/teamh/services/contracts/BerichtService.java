package be.kdg.teamh.services.contracts;

import be.kdg.teamh.entities.Bericht;
import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.exceptions.bericht.BerichtNietGevonden;

/**
 *
 */
public interface BerichtService
{
    /**
     * Haalt de gebruiker van een bericht op.
     *
     * @param id Code van het {@link Bericht} waarvan de {@link Gebruiker} moet worden opgehaald
     * @return {@link Gebruiker}
     * @throws BerichtNietGevonden
     */
    Gebruiker getGebruiker(int id) throws BerichtNietGevonden;
}
