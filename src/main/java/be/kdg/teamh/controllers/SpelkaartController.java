package be.kdg.teamh.controllers;

import be.kdg.teamh.dtos.request.SpelkaartRequest;
import be.kdg.teamh.entities.Spelkaart;
import be.kdg.teamh.exceptions.SpelkaartMaxPositionReached;
import be.kdg.teamh.exceptions.notfound.CirkelsessieNotFound;
import be.kdg.teamh.exceptions.notfound.KaartNotFound;
import be.kdg.teamh.exceptions.notfound.SpelkaartNotFound;
import be.kdg.teamh.services.contracts.SpelkaartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/spelkaarten")
public class SpelkaartController
{
    private SpelkaartService service;

    @Autowired
    public SpelkaartController(SpelkaartService service)
    {
        this.service = service;
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Spelkaart> index()
    {
        return service.all();
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public void create(@RequestBody SpelkaartRequest spelkaart) throws CirkelsessieNotFound, KaartNotFound
    {
        service.create(spelkaart);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Spelkaart show(@PathVariable("id") int id) throws SpelkaartNotFound
    {
        return service.find(id);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void update(@PathVariable("id") int id, @RequestBody SpelkaartRequest kaart) throws SpelkaartNotFound, CirkelsessieNotFound, KaartNotFound
    {
        service.update(id, kaart);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id) throws SpelkaartNotFound
    {
        service.delete(id);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "/{id}/verschuif", method = RequestMethod.POST)
    public void verschuifKaart(@PathVariable("id") int id) throws SpelkaartNotFound, SpelkaartMaxPositionReached
    {
        service.verschuif(id);
    }
}
