package be.kdg.teamh.services;

import be.kdg.teamh.dtos.request.SpelkaartRequest;
import be.kdg.teamh.entities.*;
import be.kdg.teamh.exceptions.SpelkaartMaxPositionReached;
import be.kdg.teamh.exceptions.notfound.CirkelsessieNotFound;
import be.kdg.teamh.exceptions.notfound.KaartNotFound;
import be.kdg.teamh.exceptions.notfound.SpelkaartNotFound;
import be.kdg.teamh.repositories.CirkelsessieRepository;
import be.kdg.teamh.repositories.DeelnameRepository;
import be.kdg.teamh.repositories.KaartRepository;
import be.kdg.teamh.repositories.SpelkaartRepository;
import be.kdg.teamh.services.contracts.SpelkaartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class SpelkaartServiceImpl implements SpelkaartService {
    private SpelkaartRepository repository;
    private CirkelsessieRepository cirkelsessies;
    private KaartRepository kaarten;
    private DeelnameRepository deelnames;

    @Autowired
    public SpelkaartServiceImpl(SpelkaartRepository repository, CirkelsessieRepository cirkelsessies, KaartRepository kaarten, DeelnameRepository deelnames) {
        this.repository = repository;
        this.cirkelsessies = cirkelsessies;
        this.kaarten = kaarten;
        this.deelnames = deelnames;
    }

    @Override
    public List<Spelkaart> all() {
        return repository.findAll();
    }

    @Override
    public void create(SpelkaartRequest dto) throws CirkelsessieNotFound, KaartNotFound {
        Cirkelsessie cirkelsessie = cirkelsessies.findOne(dto.getCirkelsessie());

        if (cirkelsessie == null) {
            throw new CirkelsessieNotFound();
        }

        Kaart kaart = kaarten.findOne(dto.getKaart());

        if (kaart == null) {
            throw new KaartNotFound();
        }

        Spelkaart spelkaart = new Spelkaart();

        spelkaart.setPositie(0);
        spelkaart.setCirkelsessie(cirkelsessie);
        spelkaart.setKaart(kaart);

        repository.saveAndFlush(spelkaart);
    }

    @Override
    public Spelkaart find(int id) throws SpelkaartNotFound {
        Spelkaart spelkaart = repository.findOne(id);

        if (spelkaart == null) {
            throw new SpelkaartNotFound();
        }

        return spelkaart;
    }

    @Override
    public void update(int id, SpelkaartRequest dto) throws SpelkaartNotFound, CirkelsessieNotFound, KaartNotFound {
        Cirkelsessie cirkelsessie = cirkelsessies.findOne(dto.getCirkelsessie());

        if (cirkelsessie == null) {
            throw new CirkelsessieNotFound();
        }

        Kaart kaart = kaarten.findOne(dto.getKaart());

        if (kaart == null) {
            throw new KaartNotFound();
        }

        Spelkaart spelkaart = repository.findOne(id);

        if (spelkaart == null) {
            throw new SpelkaartNotFound();
        }

        spelkaart.setPositie(dto.getPositie());
        spelkaart.setCirkelsessie(cirkelsessie);
        spelkaart.setKaart(kaart);

        repository.saveAndFlush(spelkaart);
    }

    @Override
    public void delete(int id) throws SpelkaartNotFound {
        Spelkaart spelkaart = repository.findOne(id);

        if (spelkaart == null) {
            throw new SpelkaartNotFound();
        }

        repository.delete(spelkaart);
    }

    @Override
    public void verschuif(int id, Gebruiker gebruiker) throws SpelkaartNotFound, SpelkaartMaxPositionReached {
        Spelkaart spelkaart = repository.findOne(id);

        if (spelkaart == null) {
            throw new SpelkaartNotFound();
        }

        if (spelkaart.getPositie() >= spelkaart.getCirkelsessie().getAantalCirkels()) {
            throw new SpelkaartMaxPositionReached();
        }

        List<Deelname> deelnameList = spelkaart.getCirkelsessie().getDeelnames();
        Collections.sort(deelnameList, (Deelname o1, Deelname o2) ->
                o1.getDatum().compareTo(o2.getDatum()));

        spelkaart.setPositie(spelkaart.getPositie() + 1);

        repository.saveAndFlush(spelkaart);

        for (int x = 0; x < deelnameList.size(); x++) {
            if (deelnameList.get(x).getGebruiker().getId() == gebruiker.getId()) {
                if (x + 1 == deelnameList.size()) {
                    deelnameList.get(x).setAanDeBeurt(false);
                    Deelname deelname = deelnameList.get(0);
                    deelname.setAanDeBeurt(true);
                    deelnames.saveAndFlush(deelname);
                    return;
                } else {
                    deelnameList.get(x).setAanDeBeurt(false);
                    Deelname deelname = deelnameList.get(x + 1);
                    deelname.setAanDeBeurt(true);
                    deelnames.saveAndFlush(deelname);
                    return;
                }

            }
        }


    }

    @Override
    public Kaart getKaart(int id) throws SpelkaartNotFound {
        Spelkaart spelkaart = repository.findOne(id);

        if (spelkaart == null) {
            throw new SpelkaartNotFound();
        }

        return spelkaart.getKaart();
    }
}
