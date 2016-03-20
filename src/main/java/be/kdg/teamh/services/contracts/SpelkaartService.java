package be.kdg.teamh.services.contracts;

import be.kdg.teamh.dtos.request.SpelkaartRequest;
import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.entities.Kaart;
import be.kdg.teamh.entities.Spelkaart;
import be.kdg.teamh.exceptions.gebruiker.GebruikerIsGeenDeelnemer;
import be.kdg.teamh.exceptions.spelkaart.MaximumPositieBereikt;
import be.kdg.teamh.exceptions.cirkelsessie.CirkelsessieNietGevonden;
import be.kdg.teamh.exceptions.gebruiker.GebruikerNietGevonden;
import be.kdg.teamh.exceptions.kaart.KaartNietGevonden;
import be.kdg.teamh.exceptions.spelkaart.SpelkaartNietGevonden;

import java.util.List;

public interface SpelkaartService
{
    List<Spelkaart> all();

    void create(SpelkaartRequest dto) throws CirkelsessieNietGevonden, KaartNietGevonden;

    Spelkaart find(int id) throws SpelkaartNietGevonden;

    void update(int id, SpelkaartRequest dto) throws SpelkaartNietGevonden, CirkelsessieNietGevonden, KaartNietGevonden;

    void delete(int id) throws SpelkaartNietGevonden;

    void verschuif(int id, Gebruiker gebruiker) throws SpelkaartNietGevonden, MaximumPositieBereikt, GebruikerIsGeenDeelnemer, GebruikerNietGevonden;

    Kaart getKaart(int id) throws SpelkaartNietGevonden;
}
