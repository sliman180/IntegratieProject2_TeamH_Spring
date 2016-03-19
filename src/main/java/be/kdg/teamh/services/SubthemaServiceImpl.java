package be.kdg.teamh.services;

import be.kdg.teamh.dtos.request.KaartRequest;
import be.kdg.teamh.dtos.request.SubthemaRequest;
import be.kdg.teamh.entities.*;
import be.kdg.teamh.exceptions.IsForbidden;
import be.kdg.teamh.exceptions.notfound.GebruikerNotFound;
import be.kdg.teamh.exceptions.notfound.HoofdthemaNotFound;
import be.kdg.teamh.exceptions.notfound.SubthemaNotFound;
import be.kdg.teamh.repositories.GebruikerRepository;
import be.kdg.teamh.repositories.HoofdthemaRepository;
import be.kdg.teamh.repositories.KaartRepository;
import be.kdg.teamh.repositories.SubthemaRepository;
import be.kdg.teamh.services.contracts.SubthemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SubthemaServiceImpl implements SubthemaService
{
    private SubthemaRepository repository;
    private HoofdthemaRepository hoofdthemas;
    private GebruikerRepository gebruikers;
    private KaartRepository kaarten;

    @Autowired
    public SubthemaServiceImpl(SubthemaRepository repository, HoofdthemaRepository hoofdthemas, GebruikerRepository gebruikers, KaartRepository kaarten)
    {
        this.repository = repository;
        this.hoofdthemas = hoofdthemas;
        this.gebruikers = gebruikers;
        this.kaarten = kaarten;
    }

    @Override
    public List<Subthema> all()
    {
        return repository.findAll();
    }

    @Override
    public void create(SubthemaRequest dto) throws HoofdthemaNotFound, GebruikerNotFound
    {
        Hoofdthema hoofdthema = hoofdthemas.findOne(dto.getHoofdthema());

        if (hoofdthema == null)
        {
            throw new HoofdthemaNotFound();
        }

        Gebruiker gebruiker = gebruikers.findOne(dto.getGebruiker());

        if (gebruiker == null)
        {
            throw new GebruikerNotFound();
        }

        Subthema subthema = new Subthema();
        subthema.setNaam(dto.getNaam());
        subthema.setBeschrijving(dto.getBeschrijving());
        subthema.setHoofdthema(hoofdthema);
        subthema.setGebruiker(gebruiker);
        subthema = repository.saveAndFlush(subthema);

        hoofdthema.addSubthema(subthema);
        hoofdthemas.saveAndFlush(hoofdthema);

        gebruiker.addSubthema(subthema);
        gebruikers.saveAndFlush(gebruiker);
    }

    @Override
    public Subthema find(int id) throws SubthemaNotFound
    {
        Subthema subthema = repository.findOne(id);

        if (subthema == null)
        {
            throw new SubthemaNotFound();
        }

        return subthema;
    }

    @Override
    public void update(int id,Gebruiker ingelogdeGebruiker, SubthemaRequest dto) throws SubthemaNotFound, HoofdthemaNotFound, GebruikerNotFound
    {
        Hoofdthema hoofdthema = hoofdthemas.findOne(dto.getHoofdthema());

        if (hoofdthema == null)
        {
            throw new HoofdthemaNotFound();
        }

        Gebruiker gebruiker = gebruikers.findOne(dto.getGebruiker());

        if (gebruiker == null)
        {
            throw new GebruikerNotFound();
        }

        Subthema subthema = repository.findOne(id);

        if (subthema == null)
        {
            throw new SubthemaNotFound();
        }
        if(ingelogdeGebruiker.getId()!=subthema.getGebruiker().getId()){
            throw new IsForbidden();
        }

        subthema.setNaam(dto.getNaam());
        subthema.setBeschrijving(dto.getBeschrijving());
        subthema.setHoofdthema(hoofdthema);
        subthema.setGebruiker(gebruiker);

        repository.saveAndFlush(subthema);
    }

    @Override
    public void delete(int id, Gebruiker gebruiker) throws SubthemaNotFound
    {
        Subthema subthema = repository.findOne(id);

        if (subthema == null)
        {
            throw new SubthemaNotFound();
        }

        if(gebruiker.getId()!=subthema.getGebruiker().getId()){
            throw new IsForbidden();
        }

        repository.delete(subthema);
    }

    @Override
    public Organisatie findOrganisatie(Integer id) throws SubthemaNotFound
    {
        Subthema subthema = repository.findOne(id);

        if (subthema == null)
        {
            throw new SubthemaNotFound();
        }

        return subthema.getHoofdthema().getOrganisatie();
    }

    @Override
    public Hoofdthema findHoofdthema(Integer id) throws SubthemaNotFound
    {
        Subthema subthema = repository.findOne(id);

        if (subthema == null)
        {
            throw new SubthemaNotFound();
        }

        return subthema.getHoofdthema();
    }

    @Override
    public void addKaart(int subthemaId, KaartRequest kaart) throws SubthemaNotFound, GebruikerNotFound
    {
        Subthema subthema = repository.findOne(subthemaId);
        Gebruiker gebruiker = gebruikers.findOne(kaart.getGebruiker());

        if (subthema == null)
        {
            throw new SubthemaNotFound();
        }

        if (gebruiker == null)
        {
            throw new GebruikerNotFound();
        }

        //kaart
        Kaart kaartje = new Kaart();
        kaartje.setImageUrl(kaart.getImageUrl());
        kaartje.setTekst(kaart.getTekst());
        kaartje.setGebruiker(gebruiker);
        kaartje.setSubthema(subthema);
        kaartje.setCommentsToelaatbaar(false);

        Kaart savedKaart = kaarten.save(kaartje);

        //cirkelsessie
        subthema.addKaart(savedKaart);
        repository.saveAndFlush(subthema);

        //gebruiker
        gebruiker.addKaart(savedKaart);
        gebruikers.saveAndFlush(gebruiker);
    }

    @Override
    public List<Kaart> findKaarten(Integer id) throws SubthemaNotFound
    {
        Subthema subthema = repository.findOne(id);

        if (subthema == null)
        {
            throw new SubthemaNotFound();
        }

        return subthema.getKaarten();
    }

    @Override
    public List<Cirkelsessie> findCirkelsessies(Integer id) throws SubthemaNotFound
    {
        Subthema subthema = repository.findOne(id);

        if (subthema == null)
        {
            throw new SubthemaNotFound();
        }

        return subthema.getCirkelsessies();
    }
}
