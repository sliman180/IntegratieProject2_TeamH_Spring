package be.kdg.teamh.services;

import be.kdg.teamh.dtos.request.RolRequest;
import be.kdg.teamh.entities.Rol;
import be.kdg.teamh.exceptions.notfound.RolNotFound;
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
    private RolRepository repository;

    @Autowired
    public RolServiceImpl(RolRepository repository)
    {
        this.repository = repository;
    }

    @Override
    public List<Rol> all()
    {
        return repository.findAll();
    }

    @Override
    public void create(RolRequest dto)
    {
        Rol rol = new Rol();

        rol.setNaam(dto.getNaam());

        repository.saveAndFlush(rol);
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
    public void update(int id, RolRequest dto) throws RolNotFound
    {
        Rol rol = repository.findOne(id);

        if (rol == null)
        {
            throw new RolNotFound();
        }

        rol.setNaam(dto.getNaam());

        repository.saveAndFlush(rol);
    }

    @Override
    public void delete(int id) throws RolNotFound
    {
        Rol rol = repository.findOne(id);

        if (rol == null)
        {
            throw new RolNotFound();
        }

        repository.delete(rol);
    }
}
