package be.kdg.teamh.services;

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
import be.kdg.teamh.repositories.*;
import be.kdg.teamh.services.contracts.KaartService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class KaartServiceImpl implements KaartService
{
    private CirkelsessieRepository cirkelsessies;
    private CommentaarRepository commentaren;
    private GebruikerRepository gebruikers;
    private HoofdthemaRepository hoofdthemas;
    private KaartRepository repository;
    private SpelkaartRepository spelkaarten;
    private SubthemaRepository subthemas;

    @Autowired
    public KaartServiceImpl(CirkelsessieRepository cirkelsessies, CommentaarRepository commentaren, GebruikerRepository gebruikers, HoofdthemaRepository hoofdthemas, KaartRepository repository, SpelkaartRepository spelkaarten, SubthemaRepository subthemas)
    {
        this.cirkelsessies = cirkelsessies;
        this.commentaren = commentaren;
        this.gebruikers = gebruikers;
        this.hoofdthemas = hoofdthemas;
        this.repository = repository;
        this.spelkaarten = spelkaarten;
        this.subthemas = subthemas;
    }

    public List<Kaart> all()
    {
        return repository.findAll();
    }

    @Override
    public void create(KaartRequest dto) throws GebruikerNietGevonden
    {
        Gebruiker gebruiker = gebruikers.findOne(dto.getGebruiker());

        if (gebruiker == null)
        {
            throw new GebruikerNietGevonden();
        }

        Kaart kaart = new Kaart();
        kaart.setTekst(dto.getTekst());
        kaart.setImageUrl(dto.getImageUrl());
        kaart.setCommentsToelaatbaar(dto.isCommentsToelaatbaar());
        kaart.setGebruiker(gebruiker);
        kaart = repository.save(kaart);

        gebruiker.addKaart(kaart);
        gebruikers.saveAndFlush(gebruiker);
    }

    @Override
    public Kaart find(int id) throws KaartNietGevonden
    {
        Kaart kaart = repository.findOne(id);

        if (kaart == null)
        {
            throw new KaartNietGevonden();
        }

        return kaart;
    }

    @Override
    public void update(int id, KaartRequest dto) throws KaartNietGevonden, GebruikerNietGevonden
    {
        Gebruiker gebruiker = gebruikers.findOne(dto.getGebruiker());

        if (gebruiker == null)
        {
            throw new GebruikerNietGevonden();
        }

        Kaart kaart = repository.findOne(id);

        if (kaart == null)
        {
            throw new KaartNietGevonden();
        }

        kaart.setTekst(dto.getTekst());
        kaart.setImageUrl(dto.getImageUrl());
        kaart.setCommentsToelaatbaar(dto.isCommentsToelaatbaar());
        kaart.setGebruiker(gebruiker);

        repository.saveAndFlush(kaart);
    }

    @Override
    public void delete(int id) throws KaartNietGevonden
    {
        Kaart kaart = repository.findOne(id);


        if (kaart == null)
        {
            throw new KaartNietGevonden();
        }

        repository.delete(kaart);
    }

    @Override
    public Subthema getSubthema(int id) throws KaartNietGevonden
    {
        Kaart kaart = repository.findOne(id);

        if (kaart == null)
        {
            throw new KaartNietGevonden();
        }

        return kaart.getSubthema();
    }

    @Override
    public List<Commentaar> getCommentaren(int id) throws KaartNietGevonden
    {
        Kaart kaart = repository.findOne(id);

        if (kaart == null)
        {
            throw new KaartNietGevonden();
        }

        return kaart.getCommentaren();
    }

    @Override
    public void addCommentaar(int id, CommentaarRequest dto) throws KaartNietGevonden, CommentaarNietToegelaten, GebruikerNietGevonden
    {
        Gebruiker gebruiker = gebruikers.findOne(dto.getGebruiker());

        if (gebruiker == null)
        {
            throw new GebruikerNietGevonden();
        }

        Kaart kaart = repository.findOne(id);

        if (kaart == null)
        {
            throw new KaartNietGevonden();
        }

        if (!kaart.isCommentsToelaatbaar())
        {
            throw new CommentaarNietToegelaten();
        }

        Commentaar commentaar = new Commentaar();
        commentaar.setTekst(dto.getTekst());
        commentaar.setDatum(DateTime.now());
        commentaar.setGebruiker(gebruiker);
        commentaar.setKaart(kaart);
        commentaar = commentaren.saveAndFlush(commentaar);

        gebruiker.addCommentaar(commentaar);
        gebruikers.saveAndFlush(gebruiker);

        kaart.addCommentaar(commentaar);
        repository.saveAndFlush(kaart);
    }

    @Override
    public List<Spelkaart> getSpelkaarten(int id) throws KaartNietGevonden
    {
        Kaart kaart = repository.findOne(id);

        if (kaart == null)
        {
            throw new KaartNietGevonden();
        }

        return kaart.getSpelkaarten();
    }

    @Override
    public void addSpelkaart(int id, SpelkaartRequest dto) throws KaartNietGevonden, CirkelsessieNietGevonden
    {
        Cirkelsessie cirkelsessie = cirkelsessies.findOne(dto.getCirkelsessie());

        if (cirkelsessie == null)
        {
            throw new CirkelsessieNietGevonden();
        }

        Kaart kaart = repository.findOne(id);

        if (kaart == null)
        {
            throw new KaartNietGevonden();
        }

        Spelkaart spelkaart = new Spelkaart();
        spelkaart.setPositie(0);
        spelkaart.setCirkelsessie(cirkelsessie);
        spelkaart.setKaart(kaart);
        spelkaart = spelkaarten.save(spelkaart);

        cirkelsessie.addSpelkaart(spelkaart);
        cirkelsessies.saveAndFlush(cirkelsessie);

        kaart.addSpelkaart(spelkaart);
        repository.saveAndFlush(kaart);
    }

    @Override
    public Gebruiker getGebruiker(int id) throws KaartNietGevonden
    {
        Kaart kaart = repository.findOne(id);

        if (kaart == null)
        {
            throw new KaartNietGevonden();
        }
        return kaart.getGebruiker();
    }
}


