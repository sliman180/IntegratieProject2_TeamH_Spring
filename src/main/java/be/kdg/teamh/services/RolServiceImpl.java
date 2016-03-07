package be.kdg.teamh.services;

import be.kdg.teamh.entities.Rol;
import be.kdg.teamh.exceptions.RolNotFound;
import be.kdg.teamh.repositories.RolRepository;
import be.kdg.teamh.services.contracts.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RolServiceImpl implements RolService
{
    @Autowired
    private RolRepository repository;

    @Override
    public List<Rol> all()
    {
        return repository.findAll();
    }

    @Override
    public void create(Rol rol)
    {
        repository.save(rol);
    }

    @Override
    public Rol find(int id) throws RolNotFound
    {
        Rol rol = repository.findOne(id);

        if (rol == null)
        {
            throw new RolNotFound();
        }

        return rol;
    }

    @Override
    public void update(int id, Rol rol) throws RolNotFound
    {
        Rol old = find(id);

        old.setNaam(rol.getNaam());
        old.setBeschrijving(rol.getBeschrijving());

        repository.save(old);
    }

    @Override
    public void delete(int id) throws RolNotFound
    {
        Rol rol = find(id);

        repository.delete(rol);
    }
}
