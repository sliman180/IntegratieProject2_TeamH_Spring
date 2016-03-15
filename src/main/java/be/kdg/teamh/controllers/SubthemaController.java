package be.kdg.teamh.controllers;

import be.kdg.teamh.dtos.request.SubthemaRequest;
import be.kdg.teamh.dtos.response.SubthemaResponse;
import be.kdg.teamh.exceptions.notfound.GebruikerNotFound;
import be.kdg.teamh.exceptions.notfound.HoofdthemaNotFound;
import be.kdg.teamh.exceptions.notfound.SubthemaNotFound;
import be.kdg.teamh.services.contracts.SubthemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subthemas")
public class SubthemaController
{
    private SubthemaService service;

    @Autowired
    public SubthemaController(SubthemaService service)
    {
        this.service = service;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<SubthemaResponse> index()
    {
        return service.all();
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public void create(@RequestBody SubthemaRequest subthema) throws HoofdthemaNotFound, GebruikerNotFound
    {
        service.create(subthema);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public SubthemaResponse show(@PathVariable Integer id) throws SubthemaNotFound
    {
        return service.find(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void update(@PathVariable("id") int id, @RequestBody SubthemaRequest subthema) throws SubthemaNotFound, HoofdthemaNotFound
    {
        service.update(id, subthema);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id) throws SubthemaNotFound
    {
        service.delete(id);
    }
}
