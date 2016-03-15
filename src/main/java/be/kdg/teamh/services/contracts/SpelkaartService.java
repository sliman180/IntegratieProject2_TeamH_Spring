package be.kdg.teamh.services.contracts;

import be.kdg.teamh.dtos.request.SpelkaartRequest;
import be.kdg.teamh.dtos.response.SpelkaartResponse;
import be.kdg.teamh.exceptions.SpelkaartMaxPositionReached;
import be.kdg.teamh.exceptions.notfound.CirkelsessieNotFound;
import be.kdg.teamh.exceptions.notfound.KaartNotFound;
import be.kdg.teamh.exceptions.notfound.SpelkaartNotFound;

import java.util.List;

public interface SpelkaartService
{
    List<SpelkaartResponse> all();

    void create(SpelkaartRequest dto) throws CirkelsessieNotFound, KaartNotFound;

    SpelkaartResponse find(int id) throws SpelkaartNotFound;

    void update(int id, SpelkaartRequest dto) throws SpelkaartNotFound, CirkelsessieNotFound, KaartNotFound;

    void delete(int id) throws SpelkaartNotFound;

    void verschuif(int id) throws SpelkaartNotFound, SpelkaartMaxPositionReached;
}
