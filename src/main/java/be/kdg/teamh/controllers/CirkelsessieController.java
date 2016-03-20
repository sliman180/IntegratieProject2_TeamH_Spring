package be.kdg.teamh.controllers;

import be.kdg.teamh.dtos.request.BerichtRequest;
import be.kdg.teamh.dtos.request.CirkelsessieCloneRequest;
import be.kdg.teamh.dtos.request.CirkelsessieRequest;
import be.kdg.teamh.dtos.request.KaartRequest;
import be.kdg.teamh.entities.*;
import be.kdg.teamh.services.contracts.AuthService;
import be.kdg.teamh.services.contracts.CirkelsessieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/cirkelsessies")
public class CirkelsessieController
{
    private CirkelsessieService service;
    private AuthService auth;

    @Autowired
    public CirkelsessieController(CirkelsessieService service, AuthService auth)
    {
        this.service = service;
        this.auth = auth;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Cirkelsessie> index()
    {
        return service.all();
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "actief", method = RequestMethod.GET)
    public List<Cirkelsessie> actief()
    {
        return service.actief();
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "gesloten", method = RequestMethod.GET)
    public List<Cirkelsessie> gesloten()
    {
        return service.gesloten();
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "beindigd", method = RequestMethod.GET)
    public List<Cirkelsessie> beindigd()
    {
        return service.beindigd();
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "gepland", method = RequestMethod.GET)
    public List<Cirkelsessie> gepland()
    {
        return service.gepland();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public void create(@RequestHeader("Authorization") String token, @Valid @RequestBody CirkelsessieRequest cirkelsessie)
    {
        auth.isGeregistreerd(token);

        service.create(cirkelsessie);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Cirkelsessie show(@PathVariable int id)
    {
        return service.find(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void update(@PathVariable("id") int id, @RequestHeader("Authorization") String token, @Valid @RequestBody CirkelsessieRequest cirkelsessie)
    {
        auth.isGeregistreerd(token);
        auth.isToegelaten(token, service.find(id).getGebruiker());

        service.update(id, cirkelsessie);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id, @RequestHeader("Authorization") String token)
    {
        auth.isGeregistreerd(token);
        auth.isToegelaten(token, service.find(id).getGebruiker());

        service.delete(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "{id}/clone", method = RequestMethod.POST)
    public void clone(@PathVariable("id") int id, @RequestHeader("Authorization") String token, @Valid @RequestBody CirkelsessieCloneRequest cirkelsessie)
    {
        auth.isGeregistreerd(token);
        auth.isToegelaten(token, service.find(id).getGebruiker());

        service.clone(id, cirkelsessie);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}/subthema", method = RequestMethod.GET)
    public Subthema findSubthema(@PathVariable("id") int id)
    {
        return service.findSubthema(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}/gebruiker", method = RequestMethod.GET)
    public Gebruiker findGebruiker(@PathVariable("id") int id)
    {
        return service.findGebruiker(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}/deelnames", method = RequestMethod.GET)
    public List<Deelname> findDeelnames(@PathVariable("id") int id)
    {
        return service.findDeelnames(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "{id}/deelnames", method = RequestMethod.POST)
    public void addDeelname(@PathVariable("id") int id, @RequestHeader("Authorization") String token)
    {
        auth.isGeregistreerd(token);

        service.addDeelname(id, auth.zoekGebruikerMetToken(token).getId());
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}/spelkaarten", method = RequestMethod.GET)
    public List<Spelkaart> findSpelkaarten(@PathVariable("id") int id)
    {
        return service.findSpelkaarten(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "{id}/spelkaarten", method = RequestMethod.POST)
    public void addSpelkaart(@PathVariable("id") int id, @RequestHeader("Authorization") String token, @Valid @RequestBody KaartRequest kaart)
    {
        auth.isGeregistreerd(token);

        service.addSpelkaart(id, kaart);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}/berichten", method = RequestMethod.GET)
    public List<Bericht> findBerichten(@PathVariable("id") int id)
    {
        return service.findBerichten(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "{id}/berichten", method = RequestMethod.POST)
    public void addBericht(@PathVariable("id") int id, @RequestHeader("Authorization") String token, @Valid @RequestBody BerichtRequest bericht)
    {
        auth.isGeregistreerd(token);

        service.addBericht(id, bericht);
    }
}
