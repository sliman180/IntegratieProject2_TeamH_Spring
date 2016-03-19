package be.kdg.teamh.controllers;

import be.kdg.teamh.dtos.request.OrganisatieRequest;
import be.kdg.teamh.entities.Hoofdthema;
import be.kdg.teamh.entities.Organisatie;
import be.kdg.teamh.services.contracts.AuthService;
import be.kdg.teamh.services.contracts.OrganisatieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/organisaties")
public class OrganisatieController
{
    private OrganisatieService service;
    private AuthService auth;

    @Autowired
    public OrganisatieController(OrganisatieService service, AuthService auth)
    {
        this.service = service;
        this.auth = auth;
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Organisatie> index()
    {
        return service.all();
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public void create(@Valid @RequestBody OrganisatieRequest organisatie)
    {
        service.create(organisatie);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Organisatie show(@PathVariable int id)
    {
        return service.find(id);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void update(@PathVariable("id") int id, @Valid @RequestBody OrganisatieRequest organisatie,@RequestHeader("Authorization") String token)
    {
        service.update(id,auth.findByToken(token), organisatie);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id, @RequestHeader("Authorization") String token)
    {
        service.delete(id,auth.findByToken(token));
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}/hoofdthemas", method = RequestMethod.GET)
    public List<Hoofdthema> getHoofdthemas(@PathVariable("id") int id)
    {
        return service.getHoofdthemas(id);
    }
}
