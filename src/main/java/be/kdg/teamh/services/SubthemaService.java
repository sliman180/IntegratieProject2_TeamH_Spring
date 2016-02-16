package be.kdg.teamh.services;

import be.kdg.teamh.entities.Subthema;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SubthemaService {
    void createSubthema(Subthema subthema);

    List<Subthema> findAll();
}
