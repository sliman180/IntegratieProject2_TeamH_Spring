package be.kdg.teamh.services;

import be.kdg.teamh.dtos.request.BerichtRequest;
import be.kdg.teamh.dtos.request.CirkelsessieCloneRequest;
import be.kdg.teamh.dtos.request.CirkelsessieRequest;
import be.kdg.teamh.dtos.request.KaartRequest;
import be.kdg.teamh.entities.*;
import be.kdg.teamh.exceptions.AlreadyJoinedCirkelsessie;
import be.kdg.teamh.exceptions.notfound.CirkelsessieNotFound;
import be.kdg.teamh.exceptions.notfound.GebruikerNotFound;
import be.kdg.teamh.exceptions.notfound.SubthemaNotFound;
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
    public List<Cirkelsessie> gepland() {
        return all().stream().filter(cirkelsessie -> cirkelsessie.getStatus() == Status.OPEN && DateTime.now().isBefore(cirkelsessie.getStartDatum())).collect(Collectors.toList());
    }

    @Override
    public void create(CirkelsessieRequest dto) throws GebruikerNotFound, SubthemaNotFound
    {
        Gebruiker gebruiker = gebruikers.findOne(dto.getGebruiker());
        Subthema subthema = null;

        if (gebruiker == null)
        {
            throw new GebruikerNotFound();
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
                throw new SubthemaNotFound();
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
    public Cirkelsessie find(int id) throws CirkelsessieNotFound
    {
        Cirkelsessie cirkelsessie = repository.findOne(id);

        if (cirkelsessie == null)
        {
            throw new CirkelsessieNotFound();
        }

        return cirkelsessie;
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
        clone.setStartDatum(DateTime.now());
        clone.setSubthema(cirkelsessie.getSubthema());
        clone.setGebruiker(cirkelsessie.getGebruiker());
        clone.setDeelnames(cirkelsessie.getDeelnames().stream().collect(Collectors.toList()));

        repository.save(clone);
    }

    public void clone(int id, CirkelsessieCloneRequest dto) throws CirkelsessieNotFound, SubthemaNotFound
    {
        Cirkelsessie cirkelsessie = repository.findOne(id);
        Gebruiker gebruiker=gebruikers.findOne(dto.getGebruiker());

        if (cirkelsessie == null)
        {
            throw new CirkelsessieNotFound();
        }

        Cirkelsessie clone = new Cirkelsessie();
        clone.setNaam(dto.getNaam());
        clone.setMaxAantalKaarten(cirkelsessie.getMaxAantalKaarten());
        clone.setAantalCirkels(cirkelsessie.getAantalCirkels());
        clone.setStatus(dto.getStatus());
        clone.setStartDatum(dto.getStartDatum());
        clone.setGebruiker(gebruiker);

        if(cirkelsessie.getSubthema()!=null){
            Subthema oldSubthema = subthemas.findOne(cirkelsessie.getSubthema().getId());

            if(oldSubthema==null){
                throw new SubthemaNotFound();
            }

            oldSubthema.addCirkelsessie(cirkelsessie);
            oldSubthema = subthemas.saveAndFlush(oldSubthema);
            clone.setSubthema(oldSubthema);

            for (Kaart kaart : oldSubthema.getKaarten())
            {
                Spelkaart spelkaart = new Spelkaart(kaart, clone);
                spelkaart = spelkaarten.save(spelkaart);
                clone.addSpelkaart(spelkaart);
            }
        }

        for(Deelname deelname : cirkelsessie.getDeelnames()){
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
    public Subthema getSubthema(int id) throws CirkelsessieNotFound
    {
        Cirkelsessie cirkelsessie = repository.findOne(id);

        if (cirkelsessie == null)
        {
            throw new CirkelsessieNotFound();
        }

        return cirkelsessie.getSubthema();
    }

    @Override
    public List<Deelname> getDeelnames(int id) throws CirkelsessieNotFound
    {
        Cirkelsessie cirkelsessie = repository.findOne(id);

        if (cirkelsessie == null)
        {
            throw new CirkelsessieNotFound();
        }

        return cirkelsessie.getDeelnames();
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
    public List<Spelkaart> getSpelkaarten(int id) throws CirkelsessieNotFound
    {
        Cirkelsessie cirkelsessie = repository.findOne(id);

        if (cirkelsessie == null)
        {
            throw new CirkelsessieNotFound();
        }

        return cirkelsessie.getSpelkaarten();
    }

    @Override
    public void addSpelkaart(int id, KaartRequest dto) throws CirkelsessieNotFound, GebruikerNotFound
    {
        Cirkelsessie cirkelsessie = repository.findOne(id);

        if (cirkelsessie == null)
        {
            throw new CirkelsessieNotFound();
        }

        Gebruiker gebruiker = gebruikers.findOne(dto.getGebruiker());

        if (gebruiker == null)
        {
            throw new GebruikerNotFound();
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
    public List<Bericht> getBerichten(int id) throws CirkelsessieNotFound
    {
        Cirkelsessie cirkelsessie = repository.findOne(id);

        if (cirkelsessie == null)
        {
            throw new CirkelsessieNotFound();
        }

        return cirkelsessie.getBerichten();
    }

    @Override
    public void addBericht(int id, BerichtRequest dto) throws CirkelsessieNotFound, GebruikerNotFound
    {
        Cirkelsessie cirkelsessie = repository.findOne(id);

        if (cirkelsessie == null)
        {
            throw new CirkelsessieNotFound();
        }

        Gebruiker gebruiker = gebruikers.findOne(dto.getGebruiker());

        if (gebruiker == null)
        {
            throw new GebruikerNotFound();
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
    public Gebruiker getGebruiker(int id) throws CirkelsessieNotFound
    {
        Cirkelsessie cirkelsessie = repository.findOne(id);

        if (cirkelsessie == null)
        {
            throw new CirkelsessieNotFound();
        }

        return cirkelsessie.getGebruiker();
    }
}
