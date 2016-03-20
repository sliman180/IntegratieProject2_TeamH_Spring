package be.kdg.teamh.repositories;

import be.kdg.teamh.entities.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 */
public interface RolRepository extends JpaRepository<Rol, Integer>
{
    /**
     * Zoekt een rol op basis van de naam.
     *
     * @param naam Naam van de rol
     * @return {@link Rol}
     */
    Rol findByNaam(String naam);
}
