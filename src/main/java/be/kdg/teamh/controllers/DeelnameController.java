package be.kdg.teamh.controllers;

import be.kdg.teamh.dtos.request.DeelnameRequest;
import be.kdg.teamh.entities.Cirkelsessie;
import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.exceptions.gebruiker.ToegangVerboden;
import be.kdg.teamh.services.contracts.AuthService;
import be.kdg.teamh.services.contracts.CirkelsessieService;
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
    private CirkelsessieService cirkelsessies;
    private AuthService auth;

    @Autowired
    public DeelnameController(DeelnameService service, CirkelsessieService cirkelsessies, AuthService auth)
    {
        this.service = service;
        this.cirkelsessies = cirkelsessies;
        this.auth = auth;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void update(@PathVariable("id") int id, @RequestHeader("Authorization") String token, @Valid @RequestBody DeelnameRequest deelname)
    {
        auth.isGeregistreerd(token);

        int gebruiker = auth.zoekGebruikerMetToken(token).getId();
        int eigenaar = service.find(id).getGebruiker().getId();
        int organisator = cirkelsessies.find(service.find(id).getCirkelsessie().getId()).getGebruiker().getId();

        if (gebruiker != eigenaar && gebruiker != organisator && !cirkelsessies.isMedeOrganisatorDeelname(id, gebruiker))
        {
            throw new ToegangVerboden();
        }

        service.update(id, deelname);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id, @RequestHeader("Authorization") String token)
    {
        auth.isGeregistreerd(token);

        int gebruiker = auth.zoekGebruikerMetToken(token).getId();
        int eigenaar = service.find(id).getGebruiker().getId();
        int organisator = cirkelsessies.find(service.find(id).getCirkelsessie().getId()).getGebruiker().getId();

        if (gebruiker != eigenaar && gebruiker != organisator && !cirkelsessies.isMedeOrganisatorDeelname(id, gebruiker))
        {
            throw new ToegangVerboden();
        }

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
