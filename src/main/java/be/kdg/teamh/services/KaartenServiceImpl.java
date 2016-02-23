package be.kdg.teamh.services;


import be.kdg.teamh.entities.Kaart;
import be.kdg.teamh.exceptions.KaartNotFoundException;
import be.kdg.teamh.repositories.KaartenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KaartenServiceImpl implements KaartenService {

    @Autowired
    KaartenRepository repository;

    public List<Kaart> all() {
        return repository.findAll();
    }

    @Override
    public void create(Kaart kaart) {
        repository.save(kaart);
    }

    @Override
    public Kaart find(int id) throws KaartNotFoundException {
        Kaart kaart = repository.findOne(id);

        if (kaart == null) {
            throw new KaartNotFoundException();
        }

        return kaart;
    }

    @Override
    public void update(int id, Kaart kaart) throws KaartNotFoundException {


        Kaart old = repository.findOne(id);

        if (old == null) {
            throw new KaartNotFoundException();
        }

        old.setImageUrl(kaart.getImageUrl());
        old.setTekst(kaart.getTekst());
        old.setGebruiker(kaart.getGebruiker());

        repository.save(old);
    }

    @Override
    public void delete(int id) throws KaartNotFoundException {

        Kaart kaart = repository.findOne(id);

        if (kaart == null) {
            throw new KaartNotFoundException();
        }

        repository.delete(kaart);

    }
}
