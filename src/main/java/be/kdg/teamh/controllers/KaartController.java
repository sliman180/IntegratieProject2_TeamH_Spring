package be.kdg.teamh.controllers;

import be.kdg.teamh.dtos.request.CommentaarRequest;
import be.kdg.teamh.dtos.request.KaartRequest;
import be.kdg.teamh.dtos.request.SpelkaartRequest;
import be.kdg.teamh.dtos.request.SubthemaRequest;
import be.kdg.teamh.entities.*;
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
    public void create(@Valid @RequestBody KaartRequest kaart)
    {
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
    public void update(@PathVariable("id") int id, @Valid @RequestBody KaartRequest kaart,@RequestHeader("Authorization") String token)
    {
        service.update(id,auth.findByToken(token), kaart);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id, @RequestHeader("Authorization") String token)
    {
        service.delete(id,auth.findByToken(token));
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}/subthema", method = RequestMethod.GET)
    public Subthema subthema(@PathVariable("id") int id)
    {
        return service.getSubthema(id);
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(value = "{id}/subthemas", method = RequestMethod.POST)
    public void addSubthemaToKaart(@PathVariable("id") int id, @Valid @RequestBody SubthemaRequest subthema)
    {
        service.addSubthema(id, subthema);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}/comments", method = RequestMethod.GET)
    public List<Commentaar> comments(@PathVariable("id") int id)
    {
        return service.getCommentaren(id);
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(value = "{id}/comments", method = RequestMethod.POST)
    public void createComment(@PathVariable("id") int id, @Valid @RequestBody CommentaarRequest comment)
    {
        service.addCommentaar(id, comment);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}/spelkaarten", method = RequestMethod.GET)
    public List<Spelkaart> getSpelkaarten(@PathVariable("id") int id)
    {
        return service.getSpelkaarten(id);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}/gebruiker", method = RequestMethod.GET)
    public Gebruiker getGebruiker(@PathVariable("id") int id)
    {
        return service.getGebruiker(id);
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(value = "{id}/spelkaarten", method = RequestMethod.POST)
    public void addSpelkaartAanKaart(@PathVariable("id") int id, @Valid @RequestBody SpelkaartRequest spelkaart)
    {
        service.addSpelkaart(id, spelkaart);
    }
}
