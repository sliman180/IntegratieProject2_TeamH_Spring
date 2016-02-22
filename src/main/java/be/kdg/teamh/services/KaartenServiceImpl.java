package be.kdg.teamh.services;


import be.kdg.teamh.entities.Kaart;
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
}
