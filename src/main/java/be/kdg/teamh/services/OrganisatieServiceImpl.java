package be.kdg.teamh.services;

import be.kdg.teamh.dtos.request.OrganisatieRequest;
import be.kdg.teamh.dtos.response.OrganisatieResponse;
import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.entities.Organisatie;
import be.kdg.teamh.exceptions.notfound.GebruikerNotFound;
import be.kdg.teamh.exceptions.notfound.OrganisatieNotFound;
import be.kdg.teamh.repositories.OrganisatieRepository;
import be.kdg.teamh.repositories.GebruikerRepository;
import be.kdg.teamh.services.contracts.OrganisatieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<OrganisatieResponse> all()
    {
        List<Organisatie> organisaties = repository.findAll();
        List<OrganisatieResponse> dtos = new ArrayList<>();

        for (Organisatie organisatie : organisaties)
        {
            OrganisatieResponse dto = new OrganisatieResponse();

            dto.setId(organisatie.getId());
            dto.setNaam(organisatie.getNaam());
            dto.setBeschrijving(organisatie.getBeschrijving());
            dto.setGebruiker(organisatie.getGebruiker().getId());
            dto.setHoofdthemas(organisatie.getHoofdthemas());

            dtos.add(dto);
        }

        return dtos;
    }

    @Override
    public List<OrganisatieResponse> allOfGebruiker(int id) throws GebruikerNotFound
    {
        return all().stream().filter(organisatie -> organisatie.getGebruiker() == id).collect(Collectors.toList());
    }

    @Override
    public void create(OrganisatieRequest dto) throws GebruikerNotFound
    {
        Gebruiker gebruiker = gebruikers.findOne(dto.getGebruiker());

        if (gebruiker == null)
        {
            throw new GebruikerNotFound();
        }

        Organisatie organisatie = new Organisatie();
        organisatie.setNaam(dto.getNaam());
        organisatie.setBeschrijving(dto.getBeschrijving());
        organisatie.setGebruiker(gebruiker);
        organisatie = repository.save(organisatie);

        gebruiker.addOrganisatie(organisatie);
        gebruikers.saveAndFlush(gebruiker);
    }

    @Override
    public OrganisatieResponse find(int id) throws OrganisatieNotFound
    {
        Organisatie organisatie = repository.findOne(id);

        if (organisatie == null)
        {
            throw new OrganisatieNotFound();
        }

        OrganisatieResponse dto = new OrganisatieResponse();

        dto.setId(organisatie.getId());
        dto.setNaam(organisatie.getNaam());
        dto.setBeschrijving(organisatie.getBeschrijving());
        dto.setGebruiker(organisatie.getGebruiker().getId());
        dto.setHoofdthemas(organisatie.getHoofdthemas());

        return dto;
    }

    @Override
    public void update(int id, OrganisatieRequest dto) throws OrganisatieNotFound, GebruikerNotFound
    {
        Gebruiker gebruiker = gebruikers.findOne(dto.getGebruiker());

        if (gebruiker == null)
        {
            throw new GebruikerNotFound();
        }

        Organisatie organisatie = repository.findOne(id);

        if (organisatie == null)
        {
            throw new OrganisatieNotFound();
        }

        organisatie.setNaam(dto.getNaam());
        organisatie.setBeschrijving(dto.getBeschrijving());
        organisatie.setGebruiker(gebruiker);

        repository.saveAndFlush(organisatie);
    }

    @Override
    public void delete(int id) throws OrganisatieNotFound
    {
        Organisatie organisatie = repository.findOne(id);

        if (organisatie == null)
        {
            throw new OrganisatieNotFound();
        }

        repository.delete(organisatie);
    }
}
