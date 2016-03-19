package be.kdg.teamh.services.contracts;

import be.kdg.teamh.dtos.request.CommentaarRequest;
import be.kdg.teamh.dtos.request.KaartRequest;
import be.kdg.teamh.dtos.request.SpelkaartRequest;
import be.kdg.teamh.dtos.request.SubthemaRequest;
import be.kdg.teamh.entities.*;
import be.kdg.teamh.exceptions.CommentsNotAllowed;
import be.kdg.teamh.exceptions.notfound.CirkelsessieNotFound;
import be.kdg.teamh.exceptions.notfound.GebruikerNotFound;
import be.kdg.teamh.exceptions.notfound.HoofdthemaNotFound;
import be.kdg.teamh.exceptions.notfound.KaartNotFound;

import java.util.List;

public interface KaartService {
    List<Kaart> all();

    void create(KaartRequest dto) throws GebruikerNotFound;

    Kaart find(int id) throws KaartNotFound;

    void update(int id, KaartRequest dto) throws KaartNotFound, GebruikerNotFound;

    void delete(int id) throws KaartNotFound;

    Subthema getSubthema(int id) throws KaartNotFound;

    void addSubthema(int id, SubthemaRequest dto) throws KaartNotFound, HoofdthemaNotFound;

    List<Commentaar> getCommentaren(int id) throws KaartNotFound;

    void addCommentaar(int id, CommentaarRequest dto) throws KaartNotFound, CommentsNotAllowed, GebruikerNotFound;

    List<Spelkaart> getSpelkaarten(int id) throws KaartNotFound;

    void addSpelkaart(int id, SpelkaartRequest dto) throws KaartNotFound, CirkelsessieNotFound;

    Gebruiker getGebruiker(int id) throws KaartNotFound;
}
