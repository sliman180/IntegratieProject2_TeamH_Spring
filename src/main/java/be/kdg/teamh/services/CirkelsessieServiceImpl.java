package be.kdg.teamh.services;

import be.kdg.teamh.dtos.request.BerichtRequest;
import be.kdg.teamh.dtos.request.CirkelsessieRequest;
import be.kdg.teamh.dtos.request.KaartRequest;
import be.kdg.teamh.dtos.response.*;
import be.kdg.teamh.entities.*;
import be.kdg.teamh.exceptions.AlreadyJoinedCirkelsessie;
import be.kdg.teamh.exceptions.notfound.CirkelsessieNotFound;
import be.kdg.teamh.exceptions.notfound.GebruikerNotFound;
import be.kdg.teamh.exceptions.notfound.SubthemaNotFound;
import be.kdg.teamh.repositories.*;
import be.kdg.teamh.services.contracts.CirkelsessieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CirkelsessieServiceImpl implements CirkelsessieService
{
    private CirkelsessieRepository repository;
    private GebruikerRepository gebruikers;
    private SubthemaRepository subthemas;
    private SpelkaartRepository spelkaarten;
    private KaartRepository kaarten;
    private DeelnameRepository deelnames;
    private BerichtRepository berichten;

    @Autowired
    public CirkelsessieServiceImpl(CirkelsessieRepository repository, GebruikerRepository gebruikers, SubthemaRepository subthemas, SpelkaartRepository spelkaarten, KaartRepository kaarten, DeelnameRepository deelnames, BerichtRepository berichten)
    {
        this.repository = repository;
        this.gebruikers = gebruikers;
        this.subthemas = subthemas;
        this.spelkaarten = spelkaarten;
        this.kaarten = kaarten;
        this.deelnames = deelnames;
        this.berichten = berichten;
    }

    @Override
    public List<CirkelsessieResponse> all()
    {
        List<Cirkelsessie> cirkelsessies = repository.findAll();
        List<CirkelsessieResponse> dtos = new ArrayList<>();

        for (Cirkelsessie cirkelsessie : cirkelsessies)
        {
            CirkelsessieResponse dto = new CirkelsessieResponse();

            dto.setId(cirkelsessie.getId());
            dto.setNaam(cirkelsessie.getNaam());
            dto.setAantalCirkels(cirkelsessie.getAantalCirkels());
            dto.setMaxAantalKaarten(cirkelsessie.getMaxAantalKaarten());
            dto.setStartDatum(cirkelsessie.getStartDatum());
            dto.setSubthema(cirkelsessie.getSubthema().getId());
            dto.setGebruiker(cirkelsessie.getGebruiker().getId());
            dto.setDeelnames(cirkelsessie.getDeelnames());
            dto.setSpelkaarten(cirkelsessie.getSpelkaarten());
            dto.setBerichten(cirkelsessie.getBerichten());

            dtos.add(dto);
        }

        return dtos;
    }

    @Override
    public List<CirkelsessieResponse> actief() // TODO: Spring JPA Query
    {
        List<Cirkelsessie> cirkelsessies = repository.findAll();
        List<CirkelsessieResponse> dtos = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        for (Cirkelsessie cirkelsessie : cirkelsessies)
        {
            if (cirkelsessie.getStatus() == Status.OPEN && now.isAfter(cirkelsessie.getStartDatum()))
            {
                CirkelsessieResponse dto = new CirkelsessieResponse();

                dto.setId(cirkelsessie.getId());
                dto.setNaam(cirkelsessie.getNaam());
                dto.setAantalCirkels(cirkelsessie.getAantalCirkels());
                dto.setMaxAantalKaarten(cirkelsessie.getMaxAantalKaarten());
                dto.setStartDatum(cirkelsessie.getStartDatum());
                dto.setSubthema(cirkelsessie.getSubthema().getId());
                dto.setGebruiker(cirkelsessie.getGebruiker().getId());
                dto.setDeelnames(cirkelsessie.getDeelnames());
                dto.setSpelkaarten(cirkelsessie.getSpelkaarten());
                dto.setBerichten(cirkelsessie.getBerichten());

                dtos.add(dto);
            }
        }

        return dtos;
    }

    @Override
    public List<CirkelsessieResponse> beindigd()
    {
        List<Cirkelsessie> cirkelsessies = repository.findAll();
        List<CirkelsessieResponse> dtos = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        for (Cirkelsessie cirkelsessie : cirkelsessies)
        {
            if (cirkelsessie.getStatus() == Status.BEEINDIGD)
            {
                CirkelsessieResponse dto = new CirkelsessieResponse();

                dto.setId(cirkelsessie.getId());
                dto.setNaam(cirkelsessie.getNaam());
                dto.setAantalCirkels(cirkelsessie.getAantalCirkels());
                dto.setMaxAantalKaarten(cirkelsessie.getMaxAantalKaarten());
                dto.setStartDatum(cirkelsessie.getStartDatum());
                dto.setSubthema(cirkelsessie.getSubthema().getId());
                dto.setGebruiker(cirkelsessie.getGebruiker().getId());
                dto.setDeelnames(cirkelsessie.getDeelnames());
                dto.setSpelkaarten(cirkelsessie.getSpelkaarten());
                dto.setBerichten(cirkelsessie.getBerichten());

                dtos.add(dto);
            }
        }

        return dtos;
    }

    @Override
    public List<CirkelsessieResponse> gesloten()
    {
        List<Cirkelsessie> cirkelsessies = repository.findAll();
        List<CirkelsessieResponse> dtos = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        for (Cirkelsessie cirkelsessie : cirkelsessies)
        {
            if (cirkelsessie.getStatus() == Status.GESLOTEN)
            {
                CirkelsessieResponse dto = new CirkelsessieResponse();

                dto.setId(cirkelsessie.getId());
                dto.setNaam(cirkelsessie.getNaam());
                dto.setAantalCirkels(cirkelsessie.getAantalCirkels());
                dto.setMaxAantalKaarten(cirkelsessie.getMaxAantalKaarten());
                dto.setStartDatum(cirkelsessie.getStartDatum());
                dto.setSubthema(cirkelsessie.getSubthema().getId());
                dto.setGebruiker(cirkelsessie.getGebruiker().getId());
                dto.setDeelnames(cirkelsessie.getDeelnames());
                dto.setSpelkaarten(cirkelsessie.getSpelkaarten());
                dto.setBerichten(cirkelsessie.getBerichten());

                dtos.add(dto);
            }
        }

        return dtos;
    }

    @Override
    public List<CirkelsessieResponse> gepland() // TODO: Spring JPA Query
    {
        List<Cirkelsessie> cirkelsessies = repository.findAll();
        List<CirkelsessieResponse> dtos = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        for (Cirkelsessie cirkelsessie : cirkelsessies)
        {
            if (now.isBefore(cirkelsessie.getStartDatum()))
            {
                CirkelsessieResponse dto = new CirkelsessieResponse();

                dto.setId(cirkelsessie.getId());
                dto.setNaam(cirkelsessie.getNaam());
                dto.setAantalCirkels(cirkelsessie.getAantalCirkels());
                dto.setMaxAantalKaarten(cirkelsessie.getMaxAantalKaarten());
                dto.setStartDatum(cirkelsessie.getStartDatum());
                dto.setSubthema(cirkelsessie.getSubthema().getId());
                dto.setGebruiker(cirkelsessie.getGebruiker().getId());
                dto.setDeelnames(cirkelsessie.getDeelnames());
                dto.setSpelkaarten(cirkelsessie.getSpelkaarten());
                dto.setBerichten(cirkelsessie.getBerichten());

                dtos.add(dto);
            }
        }

        return dtos;
    }

    @Override
    public void create(CirkelsessieRequest dto) throws GebruikerNotFound, SubthemaNotFound
    {
        Gebruiker gebruiker = gebruikers.findOne(dto.getGebruiker());

        if (gebruiker == null)
        {
            throw new GebruikerNotFound();
        }

        Subthema subthema = subthemas.findOne(dto.getSubthema());

        if (subthema == null)
        {
            throw new SubthemaNotFound();
        }

        Cirkelsessie cirkelsessie = new Cirkelsessie();

        cirkelsessie.setNaam(dto.getNaam());
        cirkelsessie.setAantalCirkels(dto.getAantalCirkels());
        cirkelsessie.setMaxAantalKaarten(dto.getMaxAantalKaarten());
        cirkelsessie.setStatus(dto.getStatus());
        cirkelsessie.setStartDatum(dto.getStartDatum());
        cirkelsessie.setGebruiker(gebruiker);
        cirkelsessie.setSubthema(subthema);

        cirkelsessie = repository.save(cirkelsessie);

        subthema.addCirkelsessie(cirkelsessie);
        subthemas.saveAndFlush(subthema);

        gebruiker.addCirkelsessie(cirkelsessie);
        gebruikers.saveAndFlush(gebruiker);
    }


    @Override
    public CirkelsessieResponse find(int id) throws CirkelsessieNotFound
    {
        Cirkelsessie cirkelsessie = repository.findOne(id);

        if (cirkelsessie == null)
        {
            throw new CirkelsessieNotFound();
        }

        CirkelsessieResponse dto = new CirkelsessieResponse();

        dto.setId(cirkelsessie.getId());
        dto.setNaam(cirkelsessie.getNaam());
        dto.setAantalCirkels(cirkelsessie.getAantalCirkels());
        dto.setMaxAantalKaarten(cirkelsessie.getMaxAantalKaarten());
        dto.setStartDatum(cirkelsessie.getStartDatum());
        dto.setSubthema(cirkelsessie.getSubthema().getId());
        dto.setGebruiker(cirkelsessie.getGebruiker().getId());
        dto.setDeelnames(cirkelsessie.getDeelnames());
        dto.setSpelkaarten(cirkelsessie.getSpelkaarten());
        dto.setBerichten(cirkelsessie.getBerichten());

        return dto;
    }

    @Override
    public void update(int id, CirkelsessieRequest dto) throws CirkelsessieNotFound
    {
        Cirkelsessie cirkelsessie = repository.findOne(id);

        if (cirkelsessie == null)
        {
            throw new CirkelsessieNotFound();
        }

        cirkelsessie.setNaam(dto.getNaam());
        cirkelsessie.setMaxAantalKaarten(dto.getMaxAantalKaarten());
        cirkelsessie.setAantalCirkels(dto.getAantalCirkels());
        cirkelsessie.setStatus(dto.getStatus());
        cirkelsessie.setStartDatum(dto.getStartDatum());

        repository.saveAndFlush(cirkelsessie);
    }

    @Override
    public void delete(int id) throws CirkelsessieNotFound
    {
        Cirkelsessie cirkelsessie = repository.findOne(id);

        if (cirkelsessie == null)
        {
            throw new CirkelsessieNotFound();
        }

        repository.delete(cirkelsessie);
    }

    public void clone(int id) throws CirkelsessieNotFound
    {
        Cirkelsessie cirkelsessie = repository.findOne(id);

        if (cirkelsessie == null)
        {
            throw new CirkelsessieNotFound();
        }

        Cirkelsessie clone = new Cirkelsessie();

        clone.setNaam(cirkelsessie.getNaam());
        clone.setMaxAantalKaarten(cirkelsessie.getMaxAantalKaarten());
        clone.setAantalCirkels(cirkelsessie.getAantalCirkels());
        clone.setStatus(cirkelsessie.getStatus());
        clone.setStartDatum(LocalDateTime.now());
        clone.setSubthema(cirkelsessie.getSubthema());
        clone.setGebruiker(cirkelsessie.getGebruiker());
        clone.setDeelnames(cirkelsessie.getDeelnames().stream().collect(Collectors.toList()));

        repository.save(clone);
    }

    @Override
    public SubthemaResponse getSubthema(int id) throws CirkelsessieNotFound
    {
        Cirkelsessie cirkelsessie = repository.findOne(id);

        if (cirkelsessie == null)
        {
            throw new CirkelsessieNotFound();
        }

        Subthema subthema = cirkelsessie.getSubthema();
        SubthemaResponse dto = new SubthemaResponse();

        dto.setId(subthema.getId());
        dto.setNaam(subthema.getNaam());
        dto.setBeschrijving(subthema.getBeschrijving());
        dto.setHoofdthema(subthema.getHoofdthema().getId());
        dto.setCirkelsessies(subthema.getCirkelsessies());
        dto.setKaarten(subthema.getKaarten());

        return dto;
    }

    @Override
    public List<DeelnameResponse> getDeelnames(int id) throws CirkelsessieNotFound
    {
        Cirkelsessie cirkelsessie = repository.findOne(id);

        if (cirkelsessie == null)
        {
            throw new CirkelsessieNotFound();
        }

        List<Deelname> deelnames = cirkelsessie.getDeelnames();
        List<DeelnameResponse> dtos = new ArrayList<>();

        for (Deelname deelname : deelnames)
        {
            DeelnameResponse dto = new DeelnameResponse();

            dto.setId(deelname.getId());
            dto.setAangemaakteKaarten(deelname.getAangemaakteKaarten());
            dto.setMedeorganisator(deelname.isMedeorganisator());
            dto.setCirkelsessie(deelname.getCirkelsessie().getId());
            dto.setGebruiker(deelname.getGebruiker().getId());

            dtos.add(dto);
        }

        return dtos;
    }

    @Override
    public void addDeelname(int id, int gebruikerId) throws CirkelsessieNotFound, GebruikerNotFound, AlreadyJoinedCirkelsessie
    {
        Cirkelsessie cirkelsessie = repository.findOne(id);

        if (cirkelsessie == null)
        {
            throw new CirkelsessieNotFound();
        }

        Gebruiker gebruiker = gebruikers.findOne(gebruikerId);

        for (Deelname deelname : cirkelsessie.getDeelnames())
        {
            if (deelname.getGebruiker().equals(gebruiker))
            {
                throw new AlreadyJoinedCirkelsessie();
            }
        }

        Deelname deelname = new Deelname();
        deelname.setAangemaakteKaarten(0);
        deelname.setMedeorganisator(false);
        deelname.setDatum(LocalDateTime.now());
        deelname.setCirkelsessie(cirkelsessie);
        deelname.setGebruiker(gebruiker);
        deelname = deelnames.save(deelname);

        gebruiker.addDeelname(deelname);
        gebruikers.saveAndFlush(gebruiker);

        cirkelsessie.addDeelname(deelname);
        repository.saveAndFlush(cirkelsessie);
    }

    @Override
    public List<SpelkaartResponse> getSpelkaarten(int id) throws CirkelsessieNotFound
    {
        Cirkelsessie cirkelsessie = repository.findOne(id);

        if (cirkelsessie == null)
        {
            throw new CirkelsessieNotFound();
        }

        List<Spelkaart> spelkaarten = cirkelsessie.getSpelkaarten();
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
    public void addSpelkaart(int id, int gebruikerId, KaartRequest kaartRequest) throws CirkelsessieNotFound, GebruikerNotFound
    {
        Cirkelsessie cirkelsessie = repository.findOne(id);

        if (cirkelsessie == null)
        {
            throw new CirkelsessieNotFound();
        }

        Gebruiker gebruiker = gebruikers.findOne(gebruikerId);

        Kaart kaart = new Kaart();
        kaart.setTekst(kaartRequest.getTekst());
        kaart.setImageUrl(kaartRequest.getImageUrl());
        kaart.setCommentsToelaatbaar(kaartRequest.isCommentsToelaatbaar());
        kaart.setGebruiker(gebruikers.findOne(kaartRequest.getGebruiker()));
        kaart = kaarten.save(kaart);

        Spelkaart spelkaart = new Spelkaart(kaart, cirkelsessie);
        spelkaart = spelkaarten.save(spelkaart);

        gebruiker.addKaart(kaart);
        gebruikers.saveAndFlush(gebruiker);

        cirkelsessie.addSpelkaart(spelkaart);
        repository.saveAndFlush(cirkelsessie);
    }

    @Override
    public List<BerichtResponse> getBerichten(int id) throws CirkelsessieNotFound
    {
        Cirkelsessie cirkelsessie = repository.findOne(id);

        if (cirkelsessie == null)
        {
            throw new CirkelsessieNotFound();
        }

        List<Bericht> berichten = cirkelsessie.getBerichten();
        List<BerichtResponse> dtos = new ArrayList<>();

        for (Bericht bericht : berichten)
        {
            BerichtResponse dto = new BerichtResponse();

            dto.setId(bericht.getId());
            dto.setTekst(bericht.getTekst());
            dto.setDatum(bericht.getDatum());
            dto.setCirkelsessie(bericht.getCirkelsessie().getId());
            dto.setGebruiker(bericht.getGebruiker().getId());

            dtos.add(dto);
        }

        return dtos;
    }

    @Override
    public void addBericht(int id, int gebruikerId, BerichtRequest berichtRequest) throws CirkelsessieNotFound, GebruikerNotFound
    {
        Cirkelsessie cirkelsessie = repository.findOne(id);

        if (cirkelsessie == null)
        {
            throw new CirkelsessieNotFound();
        }

        Gebruiker gebruiker = gebruikers.findOne(gebruikerId);

        Bericht bericht = new Bericht();
        bericht.setTekst(berichtRequest.getTekst());
        bericht.setDatum(LocalDateTime.now());
        bericht.setCirkelsessie(cirkelsessie);
        bericht.setGebruiker(gebruikers.findOne(berichtRequest.getGebruiker()));
        bericht = berichten.save(bericht);

        gebruiker.addBericht(bericht);
        gebruikers.saveAndFlush(gebruiker);

        cirkelsessie.addBericht(bericht);
        repository.saveAndFlush(cirkelsessie);
    }
}
