package be.kdg.teamh.services;

import be.kdg.teamh.dtos.request.DeelnameRequest;
import be.kdg.teamh.entities.Cirkelsessie;
import be.kdg.teamh.entities.Deelname;
import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.exceptions.notfound.CirkelsessieNotFound;
import be.kdg.teamh.exceptions.notfound.DeelnameNotFound;
import be.kdg.teamh.exceptions.notfound.GebruikerNotFound;
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
    public void update(int id, DeelnameRequest dto) throws DeelnameNotFound, CirkelsessieNotFound, GebruikerNotFound
    {
        Gebruiker gebruiker = gebruikers.findOne(dto.getGebruiker());

        if (gebruiker == null)
        {
            throw new GebruikerNotFound();
        }

        Cirkelsessie cirkelsessie = cirkelsessies.findOne(dto.getCirkelsessie());

        if (cirkelsessie == null)
        {
            throw new CirkelsessieNotFound();
        }

        Deelname deelname = repository.findOne(id);

        if (deelname == null)
        {
            throw new DeelnameNotFound();
        }

        deelname.setAangemaakteKaarten(dto.getAangemaakteKaarten());
        deelname.setMedeorganisator(dto.isMedeorganisator());
        deelname.setCirkelsessie(cirkelsessie);
        deelname.setGebruiker(gebruiker);

        repository.saveAndFlush(deelname);
    }

    @Override
    public void delete(int id) throws DeelnameNotFound
    {
        Deelname deelname = repository.findOne(id);

        if (deelname == null)
        {
            throw new DeelnameNotFound();
        }

        repository.delete(deelname);
    }

    @Override
    public Gebruiker getGebruiker(int id) throws DeelnameNotFound
    {
        Deelname deelname = repository.findOne(id);

        if (deelname == null)
        {
            throw new DeelnameNotFound();
        }

        return deelname.getGebruiker();
    }

    @Override
    public Cirkelsessie getCirkelsessie(int id) throws DeelnameNotFound
    {
        Deelname deelname = repository.findOne(id);

        if (deelname == null)
        {
            throw new DeelnameNotFound();
        }

        return deelname.getCirkelsessie();
    }
}
