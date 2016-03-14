package be.kdg.teamh.services;

import be.kdg.teamh.entities.*;
import be.kdg.teamh.exceptions.CommentsNotAllowed;
import be.kdg.teamh.exceptions.notfound.GebruikerNotFound;
import be.kdg.teamh.exceptions.notfound.KaartNotFound;
import be.kdg.teamh.repositories.GebruikerRepository;
import be.kdg.teamh.repositories.KaartenRepository;
import be.kdg.teamh.services.contracts.KaartenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class KaartenServiceImpl implements KaartenService {
    @Autowired
    private KaartenRepository repository;

    @Autowired
    private GebruikerRepository gebruikerRepository;

    public List<Kaart> all() {
        return repository.findAll();
    }

    @Override
    public void create(int userId, Kaart kaart) throws GebruikerNotFound {
        Gebruiker gebruiker = gebruikerRepository.findOne(userId);

        if (gebruiker == null) {

            throw new GebruikerNotFound();
        }

        kaart.setGebruiker(gebruiker);
        Kaart savedKaart = repository.save(kaart);

        gebruiker.addKaart(savedKaart);
        gebruikerRepository.save(gebruiker);

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
    public void update(int id, Kaart kaart) throws KaartNotFound {
        Kaart old = find(id);

        old.setImageUrl(kaart.getImageUrl());
        old.setTekst(kaart.getTekst());
        old.setGebruiker(kaart.getGebruiker());

        repository.saveAndFlush(old);
    }

    @Override
    public void delete(int id) throws KaartNotFound {
        Kaart kaart = find(id);

        repository.delete(kaart);
    }

    @Override
    public List<Commentaar> allComments(int id) throws KaartNotFound {
        return find(id).getCommentaren();
    }

    @Override
    public void createComment(int id, Commentaar commentaar) throws KaartNotFound, CommentsNotAllowed {
        Kaart kaart = find(id);

        if (!kaart.isCommentsToelaatbaar()) {
            throw new CommentsNotAllowed();
        }

        kaart.addComment(commentaar);

        repository.save(kaart);
    }

    @Override
    public Subthema getSubthema(int id) throws KaartNotFound {
        return find(id).getSubthema();
    }

    @Override
    public void addSubthema(int id, Subthema subthema) throws KaartNotFound {
        Kaart kaart = find(id);

        kaart.setSubthema(subthema);

        repository.save(kaart);
    }

    @Override
    public List<Spelkaart> getSpelkaarten(int id) throws KaartNotFound {
        return find(id).getSpelkaarten();
    }

    public void addSpelkaart(int id, Spelkaart spelkaart) throws KaartNotFound {
        Kaart kaart = find(id);

        if (kaart == null) {
            throw new KaartNotFound();
        }

        kaart.addSpelkaart(spelkaart);

        repository.saveAndFlush(kaart);
    }
}


