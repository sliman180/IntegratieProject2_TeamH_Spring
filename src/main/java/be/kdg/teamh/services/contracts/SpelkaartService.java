package be.kdg.teamh.services.contracts;

import be.kdg.teamh.dtos.request.SpelkaartRequest;
import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.entities.Kaart;
import be.kdg.teamh.entities.Spelkaart;
import be.kdg.teamh.exceptions.IsGeenDeelnemer;
import be.kdg.teamh.exceptions.SpelkaartMaxPositionReached;
import be.kdg.teamh.exceptions.notfound.CirkelsessieNotFound;
import be.kdg.teamh.exceptions.notfound.GebruikerNotFound;
import be.kdg.teamh.exceptions.notfound.KaartNotFound;
import be.kdg.teamh.exceptions.notfound.SpelkaartNotFound;

import java.util.List;

public interface SpelkaartService {
    List<Spelkaart> all();

    void create(SpelkaartRequest dto) throws CirkelsessieNotFound, KaartNotFound;

    Spelkaart find(int id) throws SpelkaartNotFound;

    void update(int id, SpelkaartRequest dto) throws SpelkaartNotFound, CirkelsessieNotFound, KaartNotFound;

    void delete(int id) throws SpelkaartNotFound;

    void verschuif(int id, Gebruiker gebruiker) throws SpelkaartNotFound, SpelkaartMaxPositionReached, IsGeenDeelnemer, GebruikerNotFound;

    Kaart getKaart(int id) throws SpelkaartNotFound;
}
