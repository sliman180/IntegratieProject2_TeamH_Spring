package be.kdg.teamh.services.contracts;

import be.kdg.teamh.dtos.request.CommentaarRequest;
import be.kdg.teamh.dtos.request.KaartRequest;
import be.kdg.teamh.dtos.request.SpelkaartRequest;
import be.kdg.teamh.dtos.request.SubthemaRequest;
import be.kdg.teamh.dtos.response.CommentaarResponse;
import be.kdg.teamh.dtos.response.KaartResponse;
import be.kdg.teamh.dtos.response.SpelkaartResponse;
import be.kdg.teamh.dtos.response.SubthemaResponse;
import be.kdg.teamh.exceptions.CommentsNotAllowed;
import be.kdg.teamh.exceptions.notfound.CirkelsessieNotFound;
import be.kdg.teamh.exceptions.notfound.GebruikerNotFound;
import be.kdg.teamh.exceptions.notfound.HoofdthemaNotFound;
import be.kdg.teamh.exceptions.notfound.KaartNotFound;

import java.util.List;

public interface KaartenService
{
    List<KaartResponse> all();

    void create(KaartRequest kaart) throws GebruikerNotFound;

    KaartResponse find(int id) throws KaartNotFound;

    void update(int id, KaartRequest kaart) throws KaartNotFound, GebruikerNotFound;

    void delete(int id) throws KaartNotFound;

    List<SubthemaResponse> getSubthemas(int id) throws KaartNotFound;

    void addSubthema(int id, SubthemaRequest subthema) throws KaartNotFound, HoofdthemaNotFound;

    List<CommentaarResponse> getCommentaren(int id) throws KaartNotFound;

    void addCommentaar(int id, CommentaarRequest comment) throws KaartNotFound, CommentsNotAllowed, GebruikerNotFound;

    List<SpelkaartResponse> getSpelkaarten(int id) throws KaartNotFound;

    void addSpelkaart(int id, SpelkaartRequest spelkaart) throws KaartNotFound, CirkelsessieNotFound;
}
