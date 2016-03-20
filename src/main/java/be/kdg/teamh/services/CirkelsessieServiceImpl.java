package be.kdg.teamh.services;

import be.kdg.teamh.dtos.request.BerichtRequest;
import be.kdg.teamh.dtos.request.CirkelsessieCloneRequest;
import be.kdg.teamh.dtos.request.CirkelsessieRequest;
import be.kdg.teamh.dtos.request.KaartRequest;
import be.kdg.teamh.entities.*;
import be.kdg.teamh.exceptions.cirkelsessie.CirkelsessieNietGevonden;
import be.kdg.teamh.exceptions.deelname.DeelnameNietGevonden;
import be.kdg.teamh.exceptions.gebruiker.GebruikerIsReedsDeelnemer;
import be.kdg.teamh.exceptions.gebruiker.GebruikerNietGevonden;
import be.kdg.teamh.exceptions.subthema.SubthemaNietGevonden;
import be.kdg.teamh.repositories.*;
import be.kdg.teamh.services.contracts.CirkelsessieService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<Cirkelsessie> all()
    {
        return repository.findAll();
    }

    @Override
    public List<Cirkelsessie> actief()
    {
        return all().stream().filter(cirkelsessie -> cirkelsessie.getStatus() == Status.OPEN && DateTime.now().isAfter(cirkelsessie.getStartDatum())).collect(Collectors.toList());
    }

    @Override
    public List<Cirkelsessie> beindigd()
    {
        return all().stream().filter(cirkelsessie -> cirkelsessie.getStatus() == Status.BEEINDIGD).collect(Collectors.toList());
    }

    @Override
    public List<Cirkelsessie> gesloten()
    {
        return all().stream().filter(cirkelsessie -> cirkelsessie.getStatus() == Status.GESLOTEN).collect(Collectors.toList());
    }

    @Override
    public List<Cirkelsessie> gepland()
    {
        return all().stream().filter(cirkelsessie -> cirkelsessie.getStatus() == Status.OPEN && DateTime.now().isBefore(cirkelsessie.getStartDatum())).collect(Collectors.toList());
    }

    @Override
    public void create(CirkelsessieRequest dto) throws GebruikerNietGevonden, SubthemaNietGevonden
    {
        Gebruiker gebruiker = gebruikers.findOne(dto.getGebruiker());
        Subthema subthema = null;

        if (gebruiker == null)
        {
            throw new GebruikerNietGevonden();
        }

        Cirkelsessie cirkelsessie = new Cirkelsessie();
        cirkelsessie.setNaam(dto.getNaam());
        cirkelsessie.setAantalCirkels(dto.getAantalCirkels());
        cirkelsessie.setMaxAantalKaarten(dto.getMaxAantalKaarten());
        cirkelsessie.setStatus(dto.getStatus());
        cirkelsessie.setStartDatum(dto.getStartDatum());
        cirkelsessie.setGebruiker(gebruiker);

        if (dto.getSubthema() > 0)
        {
            subthema = subthemas.findOne(dto.getSubthema());

            if (subthema == null)
            {
                throw new SubthemaNietGevonden();
            }

            cirkelsessie.setSubthema(subthema);

            for (Kaart kaart : subthema.getKaarten())
            {
                Spelkaart spelkaart = new Spelkaart(kaart, cirkelsessie);
                spelkaart = spelkaarten.save(spelkaart);
                cirkelsessie.addSpelkaart(spelkaart);
            }
        }

        cirkelsessie = repository.save(cirkelsessie);

        if (subthema != null)
        {
            subthema.addCirkelsessie(cirkelsessie);
            subthemas.saveAndFlush(subthema);
        }

        gebruiker.addCirkelsessie(cirkelsessie);
        gebruikers.saveAndFlush(gebruiker);
    }


    @Override
    public Cirkelsessie find(int id) throws CirkelsessieNietGevonden
    {
        Cirkelsessie cirkelsessie = repository.findOne(id);

        if (cirkelsessie == null)
        {
            throw new CirkelsessieNietGevonden();
        }

        return cirkelsessie;
    }

    @Override
    public void update(int id, CirkelsessieRequest dto) throws CirkelsessieNietGevonden
    {
        Cirkelsessie cirkelsessie = repository.findOne(id);

        if (cirkelsessie == null)
        {
            throw new CirkelsessieNietGevonden();
        }

        cirkelsessie.setNaam(dto.getNaam());
        cirkelsessie.setMaxAantalKaarten(dto.getMaxAantalKaarten());
        cirkelsessie.setAantalCirkels(dto.getAantalCirkels());
        cirkelsessie.setStatus(dto.getStatus());
        cirkelsessie.setStartDatum(dto.getStartDatum());

        repository.saveAndFlush(cirkelsessie);
    }

    @Override
    public void delete(int id) throws CirkelsessieNietGevonden
    {
        Cirkelsessie cirkelsessie = repository.findOne(id);

        if (cirkelsessie == null)
        {
            throw new CirkelsessieNietGevonden();
        }

        repository.delete(cirkelsessie);
    }

    public void clone(int id) throws CirkelsessieNietGevonden
    {
        Cirkelsessie cirkelsessie = repository.findOne(id);

        if (cirkelsessie == null)
        {
            throw new CirkelsessieNietGevonden();
        }

        Cirkelsessie clone = new Cirkelsessie();

        clone.setNaam(cirkelsessie.getNaam());
        clone.setMaxAantalKaarten(cirkelsessie.getMaxAantalKaarten());
        clone.setAantalCirkels(cirkelsessie.getAantalCirkels());
        clone.setStatus(cirkelsessie.getStatus());
        clone.setStartDatum(DateTime.now());
        clone.setSubthema(cirkelsessie.getSubthema());
        clone.setGebruiker(cirkelsessie.getGebruiker());
        clone.setDeelnames(cirkelsessie.getDeelnames().stream().collect(Collectors.toList()));

        repository.save(clone);
    }

    public void clone(int id, CirkelsessieCloneRequest dto) throws CirkelsessieNietGevonden, SubthemaNietGevonden
    {
        Cirkelsessie cirkelsessie = repository.findOne(id);

        if (cirkelsessie == null)
        {
            throw new CirkelsessieNietGevonden();
        }

        Gebruiker gebruiker = gebruikers.findOne(dto.getGebruiker());

        if (gebruiker == null)
        {
            throw new GebruikerNietGevonden();
        }

        Cirkelsessie clone = new Cirkelsessie();
        clone.setNaam(dto.getNaam());
        clone.setMaxAantalKaarten(cirkelsessie.getMaxAantalKaarten());
        clone.setAantalCirkels(cirkelsessie.getAantalCirkels());
        clone.setStatus(dto.getStatus());
        clone.setStartDatum(dto.getStartDatum());
        clone.setGebruiker(gebruiker);

        if (cirkelsessie.getSubthema() != null)
        {
            Subthema subthema = subthemas.findOne(cirkelsessie.getSubthema().getId());

            if (subthema == null)
            {
                throw new SubthemaNietGevonden();
            }

            subthema.addCirkelsessie(cirkelsessie);
            subthema = subthemas.saveAndFlush(subthema);
            clone.setSubthema(subthema);

            for (Kaart kaart : subthema.getKaarten())
            {
                Spelkaart spelkaart = new Spelkaart(kaart, clone);
                spelkaart = spelkaarten.save(spelkaart);
                clone.addSpelkaart(spelkaart);
            }
        }

        for (Deelname deelname : cirkelsessie.getDeelnames())
        {
            Deelname cloneDeelname = new Deelname();
            cloneDeelname.setCirkelsessie(clone);
            cloneDeelname.setAanDeBeurt(deelname.isAanDeBeurt());
            cloneDeelname.setAangemaakteKaarten(deelname.getAangemaakteKaarten());
            cloneDeelname.setDatum(deelname.getDatum());
            cloneDeelname.setMedeorganisator(deelname.isMedeorganisator());
            cloneDeelname.setGebruiker(gebruiker);

            cloneDeelname = deelnames.save(cloneDeelname);
            clone.addDeelname(cloneDeelname);
            gebruiker.addDeelname(cloneDeelname);
        }

        clone = repository.save(clone);

        gebruiker.addCirkelsessie(clone);
        gebruikers.saveAndFlush(gebruiker);
    }

    @Override
    public Subthema findSubthema(int id) throws CirkelsessieNietGevonden
    {
        Cirkelsessie cirkelsessie = repository.findOne(id);

        if (cirkelsessie == null)
        {
            throw new CirkelsessieNietGevonden();
        }

        return cirkelsessie.getSubthema();
    }

    @Override
    public Gebruiker findGebruiker(int id) throws CirkelsessieNietGevonden
    {
        Cirkelsessie cirkelsessie = repository.findOne(id);

        if (cirkelsessie == null)
        {
            throw new CirkelsessieNietGevonden();
        }

        return cirkelsessie.getGebruiker();
    }

    @Override
    public List<Deelname> findDeelnames(int id) throws CirkelsessieNietGevonden
    {
        Cirkelsessie cirkelsessie = repository.findOne(id);

        if (cirkelsessie == null)
        {
            throw new CirkelsessieNietGevonden();
        }

        return cirkelsessie.getDeelnames();
    }

    @Override
    public void addDeelname(int id, int gebruikerId) throws CirkelsessieNietGevonden, GebruikerNietGevonden, GebruikerIsReedsDeelnemer
    {
        Cirkelsessie cirkelsessie = repository.findOne(id);

        if (cirkelsessie == null)
        {
            throw new CirkelsessieNietGevonden();
        }

        Gebruiker gebruiker = gebruikers.findOne(gebruikerId);

        for (Deelname deelname : cirkelsessie.getDeelnames())
        {
            if (deelname.getGebruiker().equals(gebruiker))
            {
                throw new GebruikerIsReedsDeelnemer();
            }
        }


        Deelname deelname = new Deelname();
        if (cirkelsessie.getDeelnames().size() == 0)
        {
            deelname.setAanDeBeurt(true);
        }
        deelname.setAangemaakteKaarten(0);
        deelname.setMedeorganisator(false);
        deelname.setDatum(DateTime.now());
        deelname.setCirkelsessie(cirkelsessie);
        deelname.setGebruiker(gebruiker);
        deelname = deelnames.save(deelname);

        gebruiker.addDeelname(deelname);
        gebruikers.saveAndFlush(gebruiker);

        cirkelsessie.addDeelname(deelname);
        repository.saveAndFlush(cirkelsessie);
    }

    @Override
    public List<Spelkaart> findSpelkaarten(int id) throws CirkelsessieNietGevonden
    {
        Cirkelsessie cirkelsessie = repository.findOne(id);

        if (cirkelsessie == null)
        {
            throw new CirkelsessieNietGevonden();
        }

        return cirkelsessie.getSpelkaarten();
    }

    @Override
    public void addSpelkaart(int id, KaartRequest dto) throws CirkelsessieNietGevonden, GebruikerNietGevonden
    {
        Cirkelsessie cirkelsessie = repository.findOne(id);

        if (cirkelsessie == null)
        {
            throw new CirkelsessieNietGevonden();
        }

        Gebruiker gebruiker = gebruikers.findOne(dto.getGebruiker());

        if (gebruiker == null)
        {
            throw new GebruikerNietGevonden();
        }

        Kaart kaart = new Kaart();
        kaart.setTekst(dto.getTekst());
        kaart.setImageUrl(dto.getImageUrl());
        kaart.setCommentsToelaatbaar(dto.isCommentsToelaatbaar());
        kaart.setGebruiker(gebruikers.findOne(dto.getGebruiker()));
        kaart = kaarten.save(kaart);

        gebruiker.addKaart(kaart);
        gebruikers.saveAndFlush(gebruiker);

        for (Deelname deelname : cirkelsessie.getDeelnames())
        {
            if (deelname.getGebruiker().getId() == gebruiker.getId())
            {
                deelname.setAangemaakteKaarten(deelname.getAangemaakteKaarten() + 1);
                deelnames.saveAndFlush(deelname);
            }
        }

        Spelkaart spelkaart = new Spelkaart(kaart, cirkelsessie);
        spelkaart = spelkaarten.save(spelkaart);

        cirkelsessie.addSpelkaart(spelkaart);
        repository.saveAndFlush(cirkelsessie);
    }

    @Override
    public List<Bericht> findBerichten(int id) throws CirkelsessieNietGevonden
    {
        Cirkelsessie cirkelsessie = repository.findOne(id);

        if (cirkelsessie == null)
        {
            throw new CirkelsessieNietGevonden();
        }

        return cirkelsessie.getBerichten();
    }

    @Override
    public void addBericht(int id, BerichtRequest dto) throws CirkelsessieNietGevonden, GebruikerNietGevonden
    {
        Cirkelsessie cirkelsessie = repository.findOne(id);

        if (cirkelsessie == null)
        {
            throw new CirkelsessieNietGevonden();
        }

        Gebruiker gebruiker = gebruikers.findOne(dto.getGebruiker());

        if (gebruiker == null)
        {
            throw new GebruikerNietGevonden();
        }

        Bericht bericht = new Bericht();
        bericht.setTekst(dto.getTekst());
        bericht.setDatum(DateTime.now().now());
        bericht.setCirkelsessie(cirkelsessie);
        bericht.setGebruiker(gebruiker);
        bericht = berichten.save(bericht);

        gebruiker.addBericht(bericht);
        gebruikers.saveAndFlush(gebruiker);

        cirkelsessie.addBericht(bericht);
        repository.saveAndFlush(cirkelsessie);
    }

    @Override
    public boolean isMedeOrganisatorDeelname(int id, int gebruiker)
    {
        Deelname deelname = deelnames.findOne(id);

        if (deelname == null)
        {
            throw new DeelnameNietGevonden();
        }

        for (Deelname cirkelsessieDeelname : deelname.getCirkelsessie().getDeelnames())
        {
            if (gebruiker == cirkelsessieDeelname.getGebruiker().getId())
            {
                if (cirkelsessieDeelname.isMedeorganisator())
                {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean isMedeOrganisatorCirkelsessie(int id, int gebruiker)
    {
        Cirkelsessie cirkelsessie = repository.findOne(id);

        if (cirkelsessie == null)
        {
            throw new CirkelsessieNietGevonden();
        }

        for (Deelname deelname : cirkelsessie.getDeelnames())
        {
            if (gebruiker == deelname.getGebruiker().getId())
            {
                if (deelname.isMedeorganisator())
                {
                    return true;
                }
            }
        }

        return false;
    }
}
