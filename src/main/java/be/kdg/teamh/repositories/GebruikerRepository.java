package be.kdg.teamh.repositories;

import be.kdg.teamh.entities.Gebruiker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GebruikerRepository extends JpaRepository<Gebruiker, Integer> {
}
