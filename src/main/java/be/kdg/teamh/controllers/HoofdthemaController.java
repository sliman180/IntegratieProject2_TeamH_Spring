package be.kdg.teamh.controllers;

import be.kdg.teamh.entities.Hoofdthema;
import be.kdg.teamh.services.HoofdthemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hoofdthemas")
public class HoofdthemaController
{
    @Autowired
    private HoofdthemaService service;

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Hoofdthema> index()
    {
        return service.all();
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public void create(@RequestBody Hoofdthema hoofdthema)
    {
        service.create(hoofdthema);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "edit/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable("id") int id, @RequestBody Hoofdthema hoofdthema)
    {
        service.update(id, hoofdthema);
    }
}
