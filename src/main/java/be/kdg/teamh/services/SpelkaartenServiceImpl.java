package be.kdg.teamh.services;

import be.kdg.teamh.entities.Spelkaart;
import be.kdg.teamh.exceptions.KaartMaxPositieReached;
import be.kdg.teamh.exceptions.KaartNotFoundException;
import be.kdg.teamh.repositories.SpelkaartenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SpelkaartenServiceImpl implements SpelkaartenService {


    @Autowired
    SpelkaartenRepository repository;

    public List<Spelkaart> all() {
        return repository.findAll();
    }

    @Override
    public void create(Spelkaart spelkaart) {
        repository.save(spelkaart);
    }


    @Override
    public Spelkaart find(int id) throws KaartNotFoundException {
        Spelkaart kaart = repository.findOne(id);

        if (kaart == null) {
            throw new KaartNotFoundException();
        }

        return kaart;
    }

    @Override
    public void verschuif(int id) throws KaartNotFoundException, KaartMaxPositieReached {

        Spelkaart spelkaart = repository.findOne(id);
        if (spelkaart == null) {
            throw new KaartNotFoundException();
        }

        if (spelkaart.getPositie() == spelkaart.getCirkelsessie().getAantalCirkels()) {
            throw new KaartMaxPositieReached();
        }


        spelkaart.setPositie(spelkaart.getPositie() + 1);
        repository.save(spelkaart);
    }

    @Override
    public void update(int id, Spelkaart spelkaart) throws KaartNotFoundException {


        Spelkaart old = repository.findOne(id);

        if (old == null) {
            throw new KaartNotFoundException();
        }

        old.setCirkelsessie(spelkaart.getCirkelsessie());
        old.setKaart(spelkaart.getKaart());
        old.setPositie(spelkaart.getPositie());

        repository.save(old);
    }

    @Override
    public void delete(int id) throws KaartNotFoundException {

        Spelkaart kaart = repository.findOne(id);

        if (kaart == null) {
            throw new KaartNotFoundException();
        }

        repository.delete(kaart);

    }
}
