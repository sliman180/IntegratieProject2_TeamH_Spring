package be.kdg.teamh.repositories;

import be.kdg.teamh.entities.Kaart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KaartRepository extends JpaRepository<Kaart, Integer>
{
    //
}
