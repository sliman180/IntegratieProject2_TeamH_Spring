package be.kdg.teamh.services.contracts;

import be.kdg.teamh.entities.Spelkaart;
import be.kdg.teamh.exceptions.SpelkaartMaxPositionReached;
import be.kdg.teamh.exceptions.SpelkaartNotFound;

import java.util.List;

public interface SpelkaartenService
{
    void create(Spelkaart spelkaart);

    void update(int id, Spelkaart kaart) throws SpelkaartNotFound;

    List<Spelkaart> all();

    Spelkaart find(int id) throws SpelkaartNotFound;

    void verschuif(int id,Spelkaart spelkaart) throws SpelkaartNotFound, SpelkaartMaxPositionReached;

    void delete(int id) throws SpelkaartNotFound;
}
