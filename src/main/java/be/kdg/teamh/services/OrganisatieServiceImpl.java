package be.kdg.teamh.services;

import be.kdg.teamh.dtos.request.OrganisatieRequest;
import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.entities.Hoofdthema;
import be.kdg.teamh.entities.Organisatie;
import be.kdg.teamh.exceptions.gebruiker.GebruikerNietGevonden;
import be.kdg.teamh.exceptions.organisatie.OrganisatieNietGevonden;
import be.kdg.teamh.repositories.GebruikerRepository;
import be.kdg.teamh.repositories.OrganisatieRepository;
import be.kdg.teamh.services.contracts.OrganisatieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrganisatieServiceImpl implements OrganisatieService
{
    private OrganisatieRepository repository;
    private GebruikerRepository gebruikers;

    @Autowired
    public OrganisatieServiceImpl(OrganisatieRepository repository, GebruikerRepository gebruikers)
    {
        this.repository = repository;
        this.gebruikers = gebruikers;
    }

    public List<Organisatie> all()
    {
        return repository.findAll();
    }

    @Override
    public void create(OrganisatieRequest dto) throws GebruikerNietGevonden
    {
        Gebruiker gebruiker = gebruikers.findOne(dto.getGebruiker());

        if (gebruiker == null)
        {
            throw new GebruikerNietGevonden();
        }

        Organisatie organisatie = new Organisatie();
        organisatie.setNaam(dto.getNaam());
        organisatie.setBeschrijving(dto.getBeschrijving());
        organisatie.setGebruiker(gebruiker);
        organisatie = repository.saveAndFlush(organisatie);

        gebruiker.addOrganisatie(organisatie);
        gebruikers.saveAndFlush(gebruiker);
    }

    @Override
    public Organisatie find(int id) throws OrganisatieNietGevonden
    {
        Organisatie organisatie = repository.findOne(id);

        if (organisatie == null)
        {
            throw new OrganisatieNietGevonden();
        }

        return organisatie;
    }

    @Override
    public void update(int id, OrganisatieRequest dto) throws OrganisatieNietGevonden, GebruikerNietGevonden
    {
        Gebruiker gebruiker = gebruikers.findOne(dto.getGebruiker());

        if (gebruiker == null)
        {
            throw new GebruikerNietGevonden();
        }

        Organisatie organisatie = repository.findOne(id);

        if (organisatie == null)
        {
            throw new OrganisatieNietGevonden();
        }

        organisatie.setNaam(dto.getNaam());
        organisatie.setBeschrijving(dto.getBeschrijving());
        organisatie.setGebruiker(gebruiker);

        repository.saveAndFlush(organisatie);
    }

    @Override
    public void delete(int id) throws OrganisatieNietGevonden
    {
        Organisatie organisatie = repository.findOne(id);

        if (organisatie == null)
        {
            throw new OrganisatieNietGevonden();
        }

        repository.delete(organisatie);
    }

    @Override
    public List<Hoofdthema> findHoofdthemas(int id) throws OrganisatieNietGevonden
    {
        Organisatie organisatie = find(id);

        return organisatie.getHoofdthemas();
    }
}
