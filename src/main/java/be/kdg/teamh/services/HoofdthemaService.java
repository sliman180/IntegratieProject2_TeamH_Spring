package be.kdg.teamh.services;

import be.kdg.teamh.entities.Hoofdthema;
import be.kdg.teamh.repositories.HoofdthemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HoofdthemaService
{
    @Autowired
    private HoofdthemaRepository repository;

    public List<Hoofdthema> all()
    {
        return repository.findAll();
    }
}
