package be.kdg.teamh.services.contracts;

import be.kdg.teamh.dtos.request.CommentaarRequest;
import be.kdg.teamh.dtos.request.KaartRequest;
import be.kdg.teamh.dtos.request.SpelkaartRequest;
import be.kdg.teamh.dtos.request.SubthemaRequest;
import be.kdg.teamh.entities.*;
import be.kdg.teamh.exceptions.kaart.CommentaarNietToegelaten;
import be.kdg.teamh.exceptions.cirkelsessie.CirkelsessieNietGevonden;
import be.kdg.teamh.exceptions.gebruiker.GebruikerNietGevonden;
import be.kdg.teamh.exceptions.hoofdthema.HoofdthemaNietGevonden;
import be.kdg.teamh.exceptions.kaart.KaartNietGevonden;

import java.util.List;

public interface KaartService
{
    List<Kaart> all();

    void create(KaartRequest dto) throws GebruikerNietGevonden;

    Kaart find(int id) throws KaartNietGevonden;

    void update(int id, KaartRequest dto) throws KaartNietGevonden, GebruikerNietGevonden;

    void delete(int id) throws KaartNietGevonden;

    Subthema getSubthema(int id) throws KaartNietGevonden;

    void addSubthema(int id, SubthemaRequest dto) throws KaartNietGevonden, HoofdthemaNietGevonden;

    List<Commentaar> getCommentaren(int id) throws KaartNietGevonden;

    void addCommentaar(int id, CommentaarRequest dto) throws KaartNietGevonden, CommentaarNietToegelaten, GebruikerNietGevonden;

    List<Spelkaart> getSpelkaarten(int id) throws KaartNietGevonden;

    void addSpelkaart(int id, SpelkaartRequest dto) throws KaartNietGevonden, CirkelsessieNietGevonden;

    Gebruiker getGebruiker(int id) throws KaartNietGevonden;
}
