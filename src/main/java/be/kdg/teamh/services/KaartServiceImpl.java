package be.kdg.teamh.services;

import be.kdg.teamh.dtos.request.CommentaarRequest;
import be.kdg.teamh.dtos.request.KaartRequest;
import be.kdg.teamh.dtos.request.SpelkaartRequest;
import be.kdg.teamh.dtos.request.SubthemaRequest;
import be.kdg.teamh.dtos.response.CommentaarResponse;
import be.kdg.teamh.dtos.response.KaartResponse;
import be.kdg.teamh.dtos.response.SpelkaartResponse;
import be.kdg.teamh.dtos.response.SubthemaResponse;
import be.kdg.teamh.entities.*;
import be.kdg.teamh.exceptions.CommentsNotAllowed;
import be.kdg.teamh.exceptions.notfound.CirkelsessieNotFound;
import be.kdg.teamh.exceptions.notfound.GebruikerNotFound;
import be.kdg.teamh.exceptions.notfound.HoofdthemaNotFound;
import be.kdg.teamh.exceptions.notfound.KaartNotFound;
import be.kdg.teamh.repositories.*;
import be.kdg.teamh.services.contracts.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class KaartServiceImpl implements KaartenService
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

    public List<KaartResponse> all()
    {
        List<Kaart> kaarten = repository.findAll();
        List<KaartResponse> dtos = new ArrayList<>();

        for (Kaart kaart : kaarten)
        {
            KaartResponse dto = new KaartResponse();

            dto.setId(kaart.getId());
            dto.setTekst(kaart.getTekst());
            dto.setImageUrl(kaart.getImageUrl());
            dto.setCommentsToelaatbaar(kaart.isCommentsToelaatbaar());
            dto.setGebruiker(kaart.getGebruiker().getId());
            dto.setSubthemas(kaart.getSubthemas());
            dto.setSpelkaarten(kaart.getSpelkaarten());
            dto.setCommentaren(kaart.getCommentaren());

            dtos.add(dto);
        }

        return dtos;
    }

    @Override
    public void create(KaartRequest dto) throws GebruikerNotFound
    {
        Gebruiker gebruiker = gebruikers.findOne(dto.getGebruiker());

        if (gebruiker == null)
        {
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
    public KaartResponse find(int id) throws KaartNotFound
    {
        Kaart kaart = repository.findOne(id);

        if (kaart == null)
        {
            throw new KaartNotFound();
        }

        KaartResponse dto = new KaartResponse();

        dto.setId(kaart.getId());
        dto.setTekst(kaart.getTekst());
        dto.setImageUrl(kaart.getImageUrl());
        dto.setCommentsToelaatbaar(kaart.isCommentsToelaatbaar());
        dto.setGebruiker(kaart.getGebruiker().getId());
        dto.setSubthemas(kaart.getSubthemas());
        dto.setSpelkaarten(kaart.getSpelkaarten());
        dto.setCommentaren(kaart.getCommentaren());

        return dto;
    }

    @Override
    public void update(int id, KaartRequest dto) throws KaartNotFound, GebruikerNotFound
    {
        Gebruiker gebruiker = gebruikers.findOne(dto.getGebruiker());

        if (gebruiker == null)
        {
            throw new GebruikerNotFound();
        }

        Kaart kaart = repository.findOne(id);

        if (kaart == null)
        {
            throw new KaartNotFound();
        }

        kaart.setTekst(dto.getTekst());
        kaart.setImageUrl(dto.getImageUrl());
        kaart.setCommentsToelaatbaar(dto.isCommentsToelaatbaar());
        kaart.setGebruiker(gebruiker);

        repository.saveAndFlush(kaart);
    }

    @Override
    public void delete(int id) throws KaartNotFound
    {
        Kaart kaart = repository.findOne(id);

        if (kaart == null)
        {
            throw new KaartNotFound();
        }

        repository.delete(kaart);
    }

    @Override
    public List<SubthemaResponse> getSubthemas(int id) throws KaartNotFound
    {
        Kaart kaart = repository.findOne(id);

        if (kaart == null)
        {
            throw new KaartNotFound();
        }

        List<Subthema> subthemas = kaart.getSubthemas();
        List<SubthemaResponse> dtos = new ArrayList<>();

        for (Subthema subthema : subthemas)
        {
            SubthemaResponse dto = new SubthemaResponse();

            dto.setId(subthema.getId());
            dto.setNaam(subthema.getNaam());
            dto.setBeschrijving(subthema.getBeschrijving());
            dto.setHoofdthema(subthema.getHoofdthema().getId());
            dto.setCirkelsessies(subthema.getCirkelsessies());
            dto.setKaarten(subthema.getKaarten());

            dtos.add(dto);
        }

        return dtos;
    }

    @Override
    public void addSubthema(int id, SubthemaRequest dto) throws KaartNotFound, HoofdthemaNotFound
    {
        Hoofdthema hoofdthema = hoofdthemas.findOne(dto.getHoofdthema());

        if (hoofdthema == null)
        {
            throw new HoofdthemaNotFound();
        }

        Kaart kaart = repository.findOne(id);

        if (kaart == null)
        {
            throw new KaartNotFound();
        }

        Subthema subthema = new Subthema();
        subthema.setNaam(dto.getNaam());
        subthema.setBeschrijving(dto.getBeschrijving());
        subthema.setHoofdthema(hoofdthema);
        subthema = subthemas.save(subthema);

        kaart.addSubthema(subthema);
        repository.save(kaart);
    }

    @Override
    public List<CommentaarResponse> getCommentaren(int id) throws KaartNotFound
    {
        Kaart kaart = repository.findOne(id);

        if (kaart == null)
        {
            throw new KaartNotFound();
        }

        List<Commentaar> commentaren = kaart.getCommentaren();
        List<CommentaarResponse> dtos = new ArrayList<>();

        for (Commentaar commentaar : commentaren)
        {
            CommentaarResponse dto = new CommentaarResponse();

            dto.setId(commentaar.getId());
            dto.setTekst(commentaar.getTekst());
            dto.setDatum(commentaar.getDatum());
            dto.setKaart(commentaar.getKaart().getId());
            dto.setGebruiker(commentaar.getGebruiker().getId());

            dtos.add(dto);
        }

        return dtos;
    }

    @Override
    public void addCommentaar(int id, CommentaarRequest dto) throws KaartNotFound, CommentsNotAllowed, GebruikerNotFound
    {
        Gebruiker gebruiker = gebruikers.findOne(dto.getGebruiker());

        if (gebruiker == null)
        {
            throw new GebruikerNotFound();
        }

        Kaart kaart = repository.findOne(id);

        if (kaart == null)
        {
            throw new KaartNotFound();
        }

        if (!kaart.isCommentsToelaatbaar())
        {
            throw new CommentsNotAllowed();
        }

        Commentaar commentaar = new Commentaar();
        commentaar.setTekst(dto.getTekst());
        commentaar.setDatum(dto.getDatum());
        commentaar.setGebruiker(gebruiker);
        commentaar.setKaart(kaart);
        commentaar = commentaren.save(commentaar);

        kaart.addCommentaar(commentaar);
        repository.save(kaart);
    }

    @Override
    public List<SpelkaartResponse> getSpelkaarten(int id) throws KaartNotFound
    {
        Kaart kaart = repository.findOne(id);

        if (kaart == null)
        {
            throw new KaartNotFound();
        }

        List<Spelkaart> spelkaarten = kaart.getSpelkaarten();
        List<SpelkaartResponse> dtos = new ArrayList<>();

        for (Spelkaart spelkaart : spelkaarten)
        {
            SpelkaartResponse dto = new SpelkaartResponse();

            dto.setId(spelkaart.getId());
            dto.setPositie(spelkaart.getPositie());
            dto.setCirkelsessie(spelkaart.getCirkelsessie().getId());
            dto.setKaart(spelkaart.getKaart().getId());

            dtos.add(dto);
        }

        return dtos;
    }

    @Override
    public void addSpelkaart(int id, SpelkaartRequest dto) throws KaartNotFound, CirkelsessieNotFound
    {
        Cirkelsessie cirkelsessie = cirkelsessies.findOne(dto.getCirkelsessie());

        if (cirkelsessie == null)
        {
            throw new CirkelsessieNotFound();
        }

        Kaart kaart = repository.findOne(id);

        if (kaart == null)
        {
            throw new KaartNotFound();
        }

        Spelkaart spelkaart = new Spelkaart();
        spelkaart.setPositie(0);
        spelkaart.setCirkelsessie(cirkelsessie);
        spelkaart.setKaart(kaart);
        spelkaart = spelkaarten.save(spelkaart);

        kaart.addSpelkaart(spelkaart);
        repository.saveAndFlush(kaart);
    }
}


