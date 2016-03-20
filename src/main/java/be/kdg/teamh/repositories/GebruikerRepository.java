package be.kdg.teamh.repositories;

import be.kdg.teamh.entities.Gebruiker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 */
public interface GebruikerRepository extends JpaRepository<Gebruiker, Integer>
{
    /**
     * Zoekt een gebruiker op basis van de gebruikersnaam.
     *
     * @param gebruikersnaam Gebruikersnaam van de gebruiker
     * @return {@link Gebruiker}
     */
    Gebruiker findByGebruikersnaam(String gebruikersnaam);

    /**
     * Zoekt een gebruiker op basis van de email.
     *
     * @param email Email van de gebruiker
     * @return {@link Gebruiker}
     */
    Gebruiker findByEmail(String email);
}
