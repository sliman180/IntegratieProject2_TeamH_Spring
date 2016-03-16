package be.kdg.teamh.controllers;

import be.kdg.teamh.dtos.request.BerichtRequest;
import be.kdg.teamh.dtos.request.CirkelsessieRequest;
import be.kdg.teamh.dtos.request.KaartRequest;
import be.kdg.teamh.entities.*;
import be.kdg.teamh.exceptions.AlreadyJoinedCirkelsessie;
import be.kdg.teamh.exceptions.notfound.CirkelsessieNotFound;
import be.kdg.teamh.exceptions.notfound.GebruikerNotFound;
import be.kdg.teamh.exceptions.notfound.SubthemaNotFound;
import be.kdg.teamh.services.contracts.AuthService;
import be.kdg.teamh.services.contracts.CirkelsessieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public void create(@RequestBody CirkelsessieRequest cirkelsessie) throws GebruikerNotFound, SubthemaNotFound
    {
        service.create(cirkelsessie);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Cirkelsessie show(@PathVariable int id) throws CirkelsessieNotFound
    {
        return service.find(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void update(@PathVariable("id") int id, @RequestBody CirkelsessieRequest cirkelsessie) throws CirkelsessieNotFound
    {
        service.update(id, cirkelsessie);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id) throws CirkelsessieNotFound
    {
        service.delete(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "{id}/clone", method = RequestMethod.POST)
    public void clone(@PathVariable("id") int id) throws CirkelsessieNotFound
    {
        service.clone(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}/subthema", method = RequestMethod.GET)
    public Subthema getSubthema(@PathVariable("id") int id) throws CirkelsessieNotFound
    {
        return service.getSubthema(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}/deelnames", method = RequestMethod.GET)
    public List<Deelname> getDeelnames(@PathVariable("id") int id) throws CirkelsessieNotFound
    {
        return service.getDeelnames(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "{id}/deelnames", method = RequestMethod.POST)
    public void addDeelname(@PathVariable("id") int id, @RequestHeader(name = "Authorization") String token) throws CirkelsessieNotFound, GebruikerNotFound, AlreadyJoinedCirkelsessie
    {
        service.addDeelname(id, auth.findByToken(token).getId());
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}/spelkaarten", method = RequestMethod.GET)
    public List<Spelkaart> getSpelkaarten(@PathVariable("id") int id) throws CirkelsessieNotFound
    {
        return service.getSpelkaarten(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "{id}/spelkaarten", method = RequestMethod.POST)
    public void addSpelkaart(@PathVariable("id") int id, @RequestBody KaartRequest kaart) throws CirkelsessieNotFound, GebruikerNotFound
    {
        service.addSpelkaart(id, kaart);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}/berichten", method = RequestMethod.GET)
    public List<Bericht> getBerichten(@PathVariable("id") int id) throws CirkelsessieNotFound
    {
        return service.getBerichten(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "{id}/berichten", method = RequestMethod.POST)
    public void addBericht(@PathVariable("id") int id, @RequestBody BerichtRequest bericht) throws CirkelsessieNotFound, GebruikerNotFound
    {
        service.addBericht(id, bericht);
    }
}
