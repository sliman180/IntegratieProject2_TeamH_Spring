package be.kdg.teamh.services;

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
import be.kdg.teamh.repositories.*;
import be.kdg.teamh.services.contracts.KaartService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class KaartServiceImpl implements KaartService {
    private CirkelsessieRepository cirkelsessies;
    private CommentaarRepository commentaren;
    private GebruikerRepository gebruikers;
    private HoofdthemaRepository hoofdthemas;
    private KaartRepository repository;
    private SpelkaartRepository spelkaarten;
    private SubthemaRepository subthemas;

    @Autowired
    public KaartServiceImpl(CirkelsessieRepository cirkelsessies, CommentaarRepository commentaren, GebruikerRepository gebruikers, HoofdthemaRepository hoofdthemas, KaartRepository repository, SpelkaartRepository spelkaarten, SubthemaRepository subthemas) {
        this.cirkelsessies = cirkelsessies;
        this.commentaren = commentaren;
        this.gebruikers = gebruikers;
        this.hoofdthemas = hoofdthemas;
        this.repository = repository;
        this.spelkaarten = spelkaarten;
        this.subthemas = subthemas;
    }

    public List<Kaart> all() {
        return repository.findAll();
    }

    @Override
    public void create(KaartRequest dto) throws GebruikerNotFound {
        Gebruiker gebruiker = gebruikers.findOne(dto.getGebruiker());

        if (gebruiker == null) {
            throw new GebruikerNotFound();
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
    public Kaart find(int id) throws KaartNotFound {
        Kaart kaart = repository.findOne(id);

        if (kaart == null) {
            throw new KaartNotFound();
        }

        return kaart;
    }

    @Override
    public void update(int id, KaartRequest dto) throws KaartNotFound, GebruikerNotFound {
        Gebruiker gebruiker = gebruikers.findOne(dto.getGebruiker());

        if (gebruiker == null) {
            throw new GebruikerNotFound();
        }

        Kaart kaart = repository.findOne(id);

        if (kaart == null) {
            throw new KaartNotFound();
        }

        kaart.setTekst(dto.getTekst());
        kaart.setImageUrl(dto.getImageUrl());
        kaart.setCommentsToelaatbaar(dto.isCommentsToelaatbaar());
        kaart.setGebruiker(gebruiker);

        repository.saveAndFlush(kaart);
    }

    @Override
    public void delete(int id) throws KaartNotFound {
        Kaart kaart = repository.findOne(id);

        if (kaart == null) {
            throw new KaartNotFound();
        }

        repository.delete(kaart);
    }

    @Override
    public Subthema getSubthema(int id) throws KaartNotFound {
        Kaart kaart = repository.findOne(id);

        if (kaart == null) {
            throw new KaartNotFound();
        }

        return kaart.getSubthema();
    }

    @Override
    public void addSubthema(int id, SubthemaRequest dto) throws KaartNotFound, HoofdthemaNotFound {
        Hoofdthema hoofdthema = hoofdthemas.findOne(dto.getHoofdthema());

        if (hoofdthema == null) {
            throw new HoofdthemaNotFound();
        }

        Kaart kaart = repository.findOne(id);

        if (kaart == null) {
            throw new KaartNotFound();
        }

        Subthema subthema = new Subthema();
        subthema.setNaam(dto.getNaam());
        subthema.setBeschrijving(dto.getBeschrijving());
        subthema.setHoofdthema(hoofdthema);
        subthema = subthemas.save(subthema);

        hoofdthema.addSubthema(subthema);
        hoofdthemas.saveAndFlush(hoofdthema);

        kaart.setSubthema(subthema);
        repository.saveAndFlush(kaart);
    }

    @Override
    public List<Commentaar> getCommentaren(int id) throws KaartNotFound {
        Kaart kaart = repository.findOne(id);

        if (kaart == null) {
            throw new KaartNotFound();
        }

        return kaart.getCommentaren();
    }

    @Override
    public void addCommentaar(int id, CommentaarRequest dto) throws KaartNotFound, CommentsNotAllowed, GebruikerNotFound {
        Gebruiker gebruiker = gebruikers.findOne(dto.getGebruiker());

        if (gebruiker == null) {
            throw new GebruikerNotFound();
        }

        Kaart kaart = repository.findOne(id);

        if (kaart == null) {
            throw new KaartNotFound();
        }

        if (!kaart.isCommentsToelaatbaar()) {
            throw new CommentsNotAllowed();
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
    public List<Spelkaart> getSpelkaarten(int id) throws KaartNotFound {
        Kaart kaart = repository.findOne(id);

        if (kaart == null) {
            throw new KaartNotFound();
        }

        return kaart.getSpelkaarten();
    }

    @Override
    public void addSpelkaart(int id, SpelkaartRequest dto) throws KaartNotFound, CirkelsessieNotFound {
        Cirkelsessie cirkelsessie = cirkelsessies.findOne(dto.getCirkelsessie());

        if (cirkelsessie == null) {
            throw new CirkelsessieNotFound();
        }

        Kaart kaart = repository.findOne(id);

        if (kaart == null) {
            throw new KaartNotFound();
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
    public Gebruiker getGebruiker(int id) throws KaartNotFound {
        Kaart kaart = repository.findOne(id);

        if (kaart == null) {
            throw new KaartNotFound();
        }
        return kaart.getGebruiker();
    }
}


