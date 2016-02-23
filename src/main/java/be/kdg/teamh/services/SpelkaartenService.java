package be.kdg.teamh.services;

import be.kdg.teamh.entities.Spelkaart;
import be.kdg.teamh.exceptions.KaartNotFoundException;

public interface SpelkaartenService {


    void create(Spelkaart spelkaart);

    void update(int id, Spelkaart kaart) throws KaartNotFoundException;

    Spelkaart find(int id) throws KaartNotFoundException;

    void verschuif(int id) throws KaartNotFoundException;

    void delete(int id) throws KaartNotFoundException;
}
