package be.kdg.teamh.services;

import be.kdg.teamh.entities.Subthema;

import java.util.List;

/**
 * Created by lollik on 18/02/2016.
 */
public interface SubthemaService {
    Subthema subthemaMaken(Subthema subthema);
    List<Subthema> subthemasOphalen();
}
