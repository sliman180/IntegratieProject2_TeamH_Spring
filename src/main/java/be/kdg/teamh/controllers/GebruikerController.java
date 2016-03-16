package be.kdg.teamh.controllers;

import be.kdg.teamh.dtos.request.GebruikerRequest;
import be.kdg.teamh.entities.Cirkelsessie;
import be.kdg.teamh.entities.Deelname;
import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.exceptions.notfound.GebruikerNotFound;
import be.kdg.teamh.exceptions.notfound.RolNotFound;
import be.kdg.teamh.services.contracts.GebruikerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gebruikers")
public class GebruikerController
{
    private GebruikerService service;

    @Autowired
    public GebruikerController(GebruikerService service)
    {
        this.service = service;
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Gebruiker> index()
    {
        return service.all();
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public void create(@RequestBody GebruikerRequest gebruiker) throws RolNotFound
    {
        service.create(gebruiker);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Gebruiker show(@PathVariable("id") int id) throws GebruikerNotFound
    {
        return service.find(id);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void update(@PathVariable("id") int id, @RequestBody GebruikerRequest gebruiker) throws GebruikerNotFound
    {
        service.update(id, gebruiker);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id) throws GebruikerNotFound
    {
        service.delete(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{id}/organisaties", method = RequestMethod.GET)
    public List<Deelname> getOrganisaties(@PathVariable("id") int id) throws GebruikerNotFound
    {
        return service.getDeelnames(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}/cirkelsessies", method = RequestMethod.GET)
    public List<Cirkelsessie> getCirkelsessies(@PathVariable("id") int id) throws GebruikerNotFound
    {
        return service.getCirkelsessies(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{id}/deelnames", method = RequestMethod.GET)
    public List<Deelname> getDeelnames(@PathVariable("id") int id) throws GebruikerNotFound
    {
        return service.getDeelnames(id);
    }
}
