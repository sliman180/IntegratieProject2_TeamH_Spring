package be.kdg.teamh.controllers;

import be.kdg.teamh.dtos.request.GebruikerRequest;
import be.kdg.teamh.entities.*;
import be.kdg.teamh.services.contracts.AuthService;
import be.kdg.teamh.services.contracts.GebruikerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/gebruikers")
public class GebruikerController
{
    private GebruikerService service;
    private AuthService auth;

    @Autowired
    public GebruikerController(GebruikerService service, AuthService auth)
    {
        this.service = service;
        this.auth = auth;
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Gebruiker> index()
    {
        return service.all();
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public void create(@RequestHeader("Authorization") String token, @Valid @RequestBody GebruikerRequest gebruiker)
    {
        auth.isGeregistreerd(token);

        service.create(gebruiker);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Gebruiker show(@PathVariable("id") int id)
    {
        return service.find(id);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void update(@PathVariable("id") int id, @RequestHeader("Authorization") String token, @Valid @RequestBody GebruikerRequest gebruiker)
    {
        auth.isGeregistreerd(token);

        service.update(id, gebruiker);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id, @RequestHeader("Authorization") String token)
    {
        auth.isGeregistreerd(token);

        service.delete(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{id}/organisaties", method = RequestMethod.GET)
    public List<Organisatie> getOrganisaties(@PathVariable("id") int id)
    {
        return service.getOrganisaties(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{id}/subthemas", method = RequestMethod.GET)
    public List<Subthema> getSubthemas(@PathVariable("id") int id)
    {
        return service.getSubthemas(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{id}/hoofdthemas", method = RequestMethod.GET)
    public List<Hoofdthema> getHoofdthemas(@PathVariable("id") int id)
    {
        return service.getHoofdthemas(id);
    }


    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}/cirkelsessies", method = RequestMethod.GET)
    public List<Cirkelsessie> getCirkelsessies(@PathVariable("id") int id)
    {
        return service.getCirkelsessies(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{id}/deelnames", method = RequestMethod.GET)
    public List<Deelname> getDeelnames(@PathVariable("id") int id)
    {
        return service.getDeelnames(id);
    }
}
