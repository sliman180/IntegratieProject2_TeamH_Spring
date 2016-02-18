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

    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Hoofdthema> index()
    {
        return service.all();
    }

    @ResponseBody
    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Hoofdthema create(@RequestBody Hoofdthema hoofdthema)
    {
        return service.create(hoofdthema);
    }

    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Hoofdthema show(@PathVariable("id") int id)
    {
        return service.find(id);
    }

    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Hoofdthema update(@PathVariable("id") int id, @RequestBody Hoofdthema hoofdthema)
    {
        return service.update(id, hoofdthema);
    }

    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id)
    {
        service.delete(id);
    }
}
