package be.kdg.teamh.services.contracts;

import be.kdg.teamh.entities.Spelkaart;
import be.kdg.teamh.exceptions.SpelkaartMaxPositionReached;
import be.kdg.teamh.exceptions.SpelkaartNotFound;

import java.util.List;

public interface SpelkaartenService {
    List<Spelkaart> all();

    void create(Spelkaart spelkaart);

    Spelkaart find(int id) throws SpelkaartNotFound;

    void update(int id, Spelkaart kaart) throws SpelkaartNotFound;

    void delete(int id) throws SpelkaartNotFound;

    void verschuif(int id) throws SpelkaartNotFound, SpelkaartMaxPositionReached;
}
