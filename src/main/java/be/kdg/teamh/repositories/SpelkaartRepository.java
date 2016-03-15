package be.kdg.teamh.repositories;

import be.kdg.teamh.entities.Spelkaart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpelkaartRepository extends JpaRepository<Spelkaart, Integer>
{
    //
}
