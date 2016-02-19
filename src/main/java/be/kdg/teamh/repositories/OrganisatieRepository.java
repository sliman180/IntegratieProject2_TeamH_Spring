package be.kdg.teamh.repositories;

import be.kdg.teamh.entities.Organisatie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganisatieRepository extends JpaRepository<Organisatie, Integer>
{

}
