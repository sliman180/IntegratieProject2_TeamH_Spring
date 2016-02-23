package be.kdg.teamh.controllers;

import be.kdg.teamh.entities.Subthema;
import be.kdg.teamh.exceptions.SubthemaNotFoundException;
import be.kdg.teamh.services.SubthemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subthemas")
public class SubthemaController {
    @Autowired
    private SubthemaService service;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Subthema> index() {
        return service.all();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public void create(@RequestBody Subthema subthema) {
        service.create(subthema);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Subthema show(@PathVariable Integer id) throws SubthemaNotFoundException {
        return service.find(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void update(@PathVariable("id") int id, @RequestBody Subthema subthema) throws SubthemaNotFoundException {
        service.update(id, subthema);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id) throws SubthemaNotFoundException {
        service.delete(id);
    }
}