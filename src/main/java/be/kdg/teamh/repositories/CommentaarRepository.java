package be.kdg.teamh.repositories;

import be.kdg.teamh.entities.Commentaar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentaarRepository extends JpaRepository<Commentaar, Integer> {
    //
}
