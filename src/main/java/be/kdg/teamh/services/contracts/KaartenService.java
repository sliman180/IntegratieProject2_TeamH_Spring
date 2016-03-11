package be.kdg.teamh.services.contracts;

import be.kdg.teamh.entities.Commentaar;
import be.kdg.teamh.entities.Kaart;
import be.kdg.teamh.entities.Spelkaart;
import be.kdg.teamh.entities.Subthema;
import be.kdg.teamh.exceptions.CommentsNotAllowed;
import be.kdg.teamh.exceptions.GebruikerNotFound;
import be.kdg.teamh.exceptions.KaartNotFound;

import java.util.List;

public interface KaartenService {
    List<Kaart> all();

    void create(int userId, Kaart kaart) throws GebruikerNotFound;

    Kaart find(int id) throws KaartNotFound;

    void update(int id, Kaart kaart) throws KaartNotFound;

    void delete(int id) throws KaartNotFound;

    List<Subthema> getSubthemas(int id) throws KaartNotFound;

    void addSubthema(int id, Subthema subthema) throws KaartNotFound;

    List<Commentaar> allComments(int id) throws KaartNotFound;

    void createComment(int id, Commentaar comment) throws KaartNotFound, CommentsNotAllowed;

    List<Spelkaart> getSpelkaarten(int id) throws KaartNotFound;

    void addSpelkaart(int id, Spelkaart spelkaart) throws KaartNotFound;
}
