package be.kdg.teamh.services;

import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.entities.Organisatie;
import be.kdg.teamh.exceptions.GebruikerNotFound;
import be.kdg.teamh.exceptions.OrganisatieNotFound;
import be.kdg.teamh.repositories.GebruikerRepository;
import be.kdg.teamh.repositories.OrganisatieRepository;
import be.kdg.teamh.services.contracts.OrganisatieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrganisatieServiceImpl implements OrganisatieService
{
    @Autowired
    private OrganisatieRepository repository;

    @Autowired
    private GebruikerRepository gebruikerRepository;

    public List<Organisatie> all()
    {
        return repository.findAll();
    }

    @Override
    public void create(int userId, Organisatie organisatie)
    {
        Gebruiker gebruiker = gebruikerRepository.findOne(userId);


        organisatie.setOrganisator(gebruiker);

        repository.save(organisatie);
    }

    @Override
    public Organisatie find(int id) throws OrganisatieNotFound
    {
        Organisatie organisatie = repository.findOne(id);

        if (organisatie == null)
        {
            throw new OrganisatieNotFound();
        }

        return organisatie;
    }

    @Override
    public void update(int id, Organisatie organisatie) throws OrganisatieNotFound
    {
        Organisatie old = repository.findOne(id);

        old.setNaam(organisatie.getNaam());
        old.setBeschrijving(organisatie.getBeschrijving());
        old.setOrganisator(organisatie.getOrganisator());

        repository.saveAndFlush(old);
    }

    @Override
    public void delete(int id) throws OrganisatieNotFound
    {
        Organisatie organisatie = find(id);


        repository.delete(organisatie);
    }


    @Override
    public List<Organisatie> getMyOrganisaties(int userId) throws GebruikerNotFound
    {
        Gebruiker gebruiker = gebruikerRepository.findOne(userId);
        List<Organisatie> myOrganisaties = new ArrayList<>();
        if(gebruiker==null){

            throw new GebruikerNotFound();
        }

        for(Organisatie organisatie: repository.findAll()){
            if(organisatie.getOrganisator().getId()==userId){
                myOrganisaties.add(organisatie);
            }

        }
        return myOrganisaties;
    }
}
