package be.kdg.teamh.controllers;

import be.kdg.teamh.dtos.request.CommentaarRequest;
import be.kdg.teamh.dtos.request.KaartRequest;
import be.kdg.teamh.entities.Commentaar;
import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.entities.Kaart;
import be.kdg.teamh.entities.Subthema;
import be.kdg.teamh.services.contracts.AuthService;
import be.kdg.teamh.services.contracts.KaartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/kaarten")
public class KaartController
{
    private KaartService service;
    private AuthService auth;

    @Autowired
    public KaartController(KaartService service, AuthService auth)
    {
        this.service = service;
        this.auth = auth;
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Kaart> index()
    {
        return service.all();
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public void create(@RequestHeader("Authorization") String token, @Valid @RequestBody KaartRequest kaart)
    {
        auth.isGeregistreerd(token);

        service.create(kaart);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Kaart show(@PathVariable("id") int id)
    {
        return service.find(id);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void update(@PathVariable("id") int id, @RequestHeader("Authorization") String token, @Valid @RequestBody KaartRequest kaart)
    {
        auth.isGeregistreerd(token);
        auth.isToegelaten(token, service.find(id).getGebruiker());

        service.update(id, kaart);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id, @RequestHeader("Authorization") String token)
    {
        auth.isGeregistreerd(token);
        auth.isToegelaten(token, service.find(id).getGebruiker());

        service.delete(id);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}/subthema", method = RequestMethod.GET)
    public Subthema getSubthema(@PathVariable("id") int id)
    {
        return service.getSubthema(id);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}/gebruiker", method = RequestMethod.GET)
    public Gebruiker getGebruiker(@PathVariable("id") int id)
    {
        return service.getGebruiker(id);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}/commentaren", method = RequestMethod.GET)
    public List<Commentaar> getCommentaren(@PathVariable("id") int id)
    {
        return service.getCommentaren(id);
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(value = "{id}/commentaren", method = RequestMethod.POST)
    public void addCommentaar(@PathVariable("id") int id, @RequestHeader("Authorization") String token, @Valid @RequestBody CommentaarRequest comment)
    {
        auth.isGeregistreerd(token);

        service.addCommentaar(id, comment);
    }
}
