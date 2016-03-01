package be.kdg.teamh.services;

import be.kdg.teamh.entities.Spelkaart;
import be.kdg.teamh.exceptions.SpelkaartMaxPositionReached;
import be.kdg.teamh.exceptions.SpelkaartNotFound;
import be.kdg.teamh.repositories.SpelkaartenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpelkaartenServiceImpl implements SpelkaartenService
{
    @Autowired
    SpelkaartenRepository repository;

    public List<Spelkaart> all()
    {
        return repository.findAll();
    }

    @Override
    public void create(Spelkaart spelkaart)
    {
        repository.save(spelkaart);
    }

    @Override
    public Spelkaart find(int id) throws SpelkaartNotFound
    {
        Spelkaart spelkaart = repository.findOne(id);

        if (spelkaart == null)
        {
            throw new SpelkaartNotFound();
        }

        return spelkaart;
    }

    @Override
    public void verschuif(int id) throws SpelkaartNotFound, SpelkaartMaxPositionReached
    {
        Spelkaart spelkaart = find(id);

        if (spelkaart.getPositie() == spelkaart.getCirkelsessie().getAantalCirkels())
        {
            throw new SpelkaartMaxPositionReached();
        }

        spelkaart.setPositie(spelkaart.getPositie() + 1);

        repository.save(spelkaart);
    }

    @Override
    public void update(int id, Spelkaart spelkaart) throws SpelkaartNotFound
    {
        Spelkaart old = find(id);

        old.setCirkelsessie(spelkaart.getCirkelsessie());
        old.setKaart(spelkaart.getKaart());
        old.setPositie(spelkaart.getPositie());

        repository.save(old);
    }

    @Override
    public void delete(int id) throws SpelkaartNotFound
    {
        Spelkaart spelkaart = find(id);

        repository.delete(spelkaart);
    }
}
