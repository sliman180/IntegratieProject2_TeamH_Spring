package be.kdg.teamh.services;

import be.kdg.teamh.entities.Comment;
import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.entities.Kaart;
import be.kdg.teamh.entities.Subthema;
import be.kdg.teamh.exceptions.CommentsNotAllowed;
import be.kdg.teamh.exceptions.KaartNotFound;

import java.io.IOException;
import java.util.List;

public interface KaartenService
{
    List<Kaart> all();

    void create(Kaart kaart);

    Kaart find(int id) throws KaartNotFound;

    void update(int id, Kaart kaart) throws KaartNotFound;

    void delete(int id) throws KaartNotFound;

    void importCards(String csvPath, Gebruiker gebruiker) throws IOException;

    void createComment(int id, Comment comment) throws KaartNotFound, CommentsNotAllowed;

    List<Comment> allComments(int id) throws KaartNotFound;

    void addSubthema(int id, Subthema subthema) throws KaartNotFound;

    List<Subthema> getSubthemas(int id) throws KaartNotFound;
}
