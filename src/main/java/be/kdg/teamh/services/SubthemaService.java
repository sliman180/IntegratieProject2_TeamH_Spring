package be.kdg.teamh.services;

import be.kdg.teamh.entities.Subthema;

import java.util.List;

/**
 * Created by lollik on 18/02/2016.
 */
public interface SubthemaService {
    void subthemaMaken(Subthema subthema);
    List<Subthema> subthemasOphalen();
    void subthemaVerwijderen(int id);
    Subthema subthemaOphalen(int id);
    void subthemaAnpassen(int id, Subthema subthema);
}
