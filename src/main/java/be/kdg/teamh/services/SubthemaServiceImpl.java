package be.kdg.teamh.services;

import be.kdg.teamh.dtos.request.SubthemaRequest;
import be.kdg.teamh.dtos.response.SubthemaResponse;
import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.entities.Hoofdthema;
import be.kdg.teamh.entities.Subthema;
import be.kdg.teamh.exceptions.notfound.GebruikerNotFound;
import be.kdg.teamh.exceptions.notfound.HoofdthemaNotFound;
import be.kdg.teamh.exceptions.notfound.SubthemaNotFound;
import be.kdg.teamh.repositories.GebruikerRepository;
import be.kdg.teamh.repositories.HoofdthemaRepository;
import be.kdg.teamh.repositories.SubthemaRepository;
import be.kdg.teamh.services.contracts.SubthemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SubthemaServiceImpl implements SubthemaService
{
    private SubthemaRepository repository;
    private HoofdthemaRepository hoofdthemas;
    private GebruikerRepository gebruikers;

    @Autowired
    public SubthemaServiceImpl(SubthemaRepository repository, HoofdthemaRepository hoofdthemas, GebruikerRepository gebruikers)
    {
        this.repository = repository;
        this.hoofdthemas = hoofdthemas;
        this.gebruikers = gebruikers;
    }

    @Override
    public List<SubthemaResponse> all()
    {
        List<Subthema> subthemas = repository.findAll();
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
        subthema = repository.save(subthema);

        hoofdthema.addSubthema(subthema);
        hoofdthemas.saveAndFlush(hoofdthema);

        gebruiker.addSubthema(subthema);
        gebruikers.save(gebruiker);
    }

    @Override
    public SubthemaResponse find(int id) throws SubthemaNotFound
    {
        Subthema subthema = repository.findOne(id);

        if (subthema == null)
        {
            throw new SubthemaNotFound();
        }

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
    public void update(int id, SubthemaRequest dto) throws SubthemaNotFound, HoofdthemaNotFound
    {
        Hoofdthema hoofdthema = hoofdthemas.findOne(dto.getHoofdthema());

        if (hoofdthema == null)
        {
            throw new HoofdthemaNotFound();
        }

        Subthema subthema = repository.findOne(id);

        if (subthema == null)
        {
            throw new SubthemaNotFound();
        }

        subthema.setNaam(dto.getNaam());
        subthema.setBeschrijving(dto.getBeschrijving());
        subthema.setHoofdthema(hoofdthema);

        repository.saveAndFlush(subthema);
    }

    @Override
    public void delete(int id) throws SubthemaNotFound
    {
        Subthema subthema = repository.findOne(id);

        if (subthema == null)
        {
            throw new SubthemaNotFound();
        }

        repository.delete(subthema);
    }
}
