package be.kdg.teamh.services;

import be.kdg.teamh.entities.Spelkaart;
import be.kdg.teamh.exceptions.KaartMaxPositieReached;
import be.kdg.teamh.exceptions.KaartNotFoundException;

import java.util.List;

public interface SpelkaartenService {


    void create(Spelkaart spelkaart);


    void update(int id, Spelkaart kaart) throws KaartNotFoundException;

    List<Spelkaart> all();

    Spelkaart find(int id) throws KaartNotFoundException;

    void verschuif(int id) throws KaartNotFoundException, KaartMaxPositieReached;

    void delete(int id) throws KaartNotFoundException;
}
