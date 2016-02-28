package be.kdg.teamh.controllers;

import be.kdg.teamh.entities.Spelkaart;
import be.kdg.teamh.exceptions.KaartMaxPositieReached;
import be.kdg.teamh.exceptions.KaartNotFoundException;
import be.kdg.teamh.services.SpelkaartenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/spelkaarten")
public class SpelkaartController {

    @Autowired
    private SpelkaartenService service;


    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Spelkaart> index() {
        return service.all();
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public void create(@RequestBody Spelkaart spelkaart) {
        service.create(spelkaart);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Spelkaart show(@PathVariable("id") int id) throws KaartNotFoundException {
        return service.find(id);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void update(@PathVariable("id") int id, @RequestBody Spelkaart kaart) throws KaartNotFoundException {
        service.update(id, kaart);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id) throws KaartNotFoundException {
        service.delete(id);
    }


    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "/verschuif/{id}", method = RequestMethod.PATCH)
    public void verschuifKaart(@PathVariable("id") int id) throws KaartNotFoundException, KaartMaxPositieReached {
        service.verschuif(id);
    }


}
