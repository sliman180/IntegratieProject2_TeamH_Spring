package be.kdg.teamh.controllers;

import be.kdg.teamh.dtos.request.DeelnameRequest;
import be.kdg.teamh.entities.Cirkelsessie;
import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.services.contracts.AuthService;
import be.kdg.teamh.services.contracts.DeelnameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/deelnames")
public class DeelnameController
{
    private DeelnameService service;
    private AuthService auth;

    @Autowired
    public DeelnameController(DeelnameService service, AuthService auth)
    {
        this.service = service;
        this.auth = auth;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void update(@PathVariable("id") int id, @Valid @RequestBody DeelnameRequest deelname)
    {
        service.update(id, deelname);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id)
    {
        service.delete(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}/gebruiker", method = RequestMethod.GET)
    public Gebruiker getGebruiker(@PathVariable("id") int id)
    {
        return service.getGebruiker(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}/cirkelsessie", method = RequestMethod.GET)
    public Cirkelsessie getCirkelsessie(@PathVariable("id") int id)
    {
        return service.getCirkelsessie(id);
    }
}
