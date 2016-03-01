package be.kdg.teamh.repositories;

import be.kdg.teamh.entities.Cirkelsessie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CirkelsessieRepository extends JpaRepository<Cirkelsessie, Integer>
{

}
