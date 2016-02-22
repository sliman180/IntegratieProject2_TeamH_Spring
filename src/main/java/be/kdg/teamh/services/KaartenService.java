package be.kdg.teamh.services;


import be.kdg.teamh.entities.Kaart;

import java.util.List;

public interface KaartenService {

    List<Kaart> all();

    void create(Kaart kaart);

}
