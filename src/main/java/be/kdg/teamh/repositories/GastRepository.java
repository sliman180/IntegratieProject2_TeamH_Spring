package be.kdg.teamh.repositories;

import be.kdg.teamh.entities.Gast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GastRepository extends JpaRepository<Gast, Integer> {

}
