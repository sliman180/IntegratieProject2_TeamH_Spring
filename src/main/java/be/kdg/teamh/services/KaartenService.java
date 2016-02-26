package be.kdg.teamh.services;


import be.kdg.teamh.entities.Comment;
import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.entities.Kaart;
import be.kdg.teamh.entities.Subthema;
import be.kdg.teamh.exceptions.CommentsNotAllowed;
import be.kdg.teamh.exceptions.KaartNotFoundException;

import java.io.IOException;
import java.util.List;

public interface KaartenService {

    List<Kaart> all();

    void create(Kaart kaart);

    void importCards(String csvPath, Gebruiker gebruiker) throws IOException;

    Kaart find(int id) throws KaartNotFoundException;

    void update(int id, Kaart kaart) throws KaartNotFoundException;

    void delete(int id) throws KaartNotFoundException;

    void createComment(int id, Comment comment) throws CommentsNotAllowed;

    List<Comment> allComments(int id);

    void addSubthema(int id, Subthema subthema);

    List<Subthema> getSubthemas(int id);


}
