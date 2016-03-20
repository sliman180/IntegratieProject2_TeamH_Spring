package be.kdg.teamh.services;

import be.kdg.teamh.dtos.request.DeelnameRequest;
import be.kdg.teamh.entities.Cirkelsessie;
import be.kdg.teamh.entities.Deelname;
import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.exceptions.cirkelsessie.CirkelsessieNietGevonden;
import be.kdg.teamh.exceptions.deelname.DeelnameNietGevonden;
import be.kdg.teamh.exceptions.gebruiker.GebruikerNietGevonden;
import be.kdg.teamh.repositories.CirkelsessieRepository;
import be.kdg.teamh.repositories.DeelnameRepository;
import be.kdg.teamh.repositories.GebruikerRepository;
import be.kdg.teamh.services.contracts.DeelnameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DeelnameServiceImpl implements DeelnameService
{
    private DeelnameRepository repository;
    private GebruikerRepository gebruikers;
    private CirkelsessieRepository cirkelsessies;

    @Autowired
    public DeelnameServiceImpl(DeelnameRepository repository, GebruikerRepository gebruikers, CirkelsessieRepository cirkelsessies)
    {
        this.repository = repository;
        this.gebruikers = gebruikers;
        this.cirkelsessies = cirkelsessies;
    }

    @Override
    public Deelname find(int id) throws DeelnameNietGevonden
    {
        Deelname deelname = repository.findOne(id);

        if (deelname == null)
        {
            throw new DeelnameNietGevonden();
        }

        return deelname;
    }

    @Override
    public void update(int id, DeelnameRequest dto) throws DeelnameNietGevonden, CirkelsessieNietGevonden, GebruikerNietGevonden
    {
        Gebruiker gebruiker = gebruikers.findOne(dto.getGebruiker());

        if (gebruiker == null)
        {
            throw new GebruikerNietGevonden();
        }

        Cirkelsessie cirkelsessie = cirkelsessies.findOne(dto.getCirkelsessie());

        if (cirkelsessie == null)
        {
            throw new CirkelsessieNietGevonden();
        }

        Deelname deelname = repository.findOne(id);

        if (deelname == null)
        {
            throw new DeelnameNietGevonden();
        }

        deelname.setAangemaakteKaarten(dto.getAangemaakteKaarten());
        deelname.setMedeorganisator(dto.isMedeorganisator());
        deelname.setCirkelsessie(cirkelsessie);
        deelname.setGebruiker(gebruiker);

        repository.saveAndFlush(deelname);
    }

    @Override
    public void delete(int id) throws DeelnameNietGevonden
    {
        Deelname deelname = repository.findOne(id);

        if (deelname == null)
        {
            throw new DeelnameNietGevonden();
        }

        repository.delete(deelname);
    }

    @Override
    public Gebruiker getGebruiker(int id) throws DeelnameNietGevonden
    {
        Deelname deelname = repository.findOne(id);

        if (deelname == null)
        {
            throw new DeelnameNietGevonden();
        }

        return deelname.getGebruiker();
    }

    @Override
    public Cirkelsessie getCirkelsessie(int id) throws DeelnameNietGevonden
    {
        Deelname deelname = repository.findOne(id);

        if (deelname == null)
        {
            throw new DeelnameNietGevonden();
        }

        return deelname.getCirkelsessie();
    }
}
