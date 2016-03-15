package be.kdg.teamh.controllers;

import be.kdg.teamh.dtos.request.CommentaarRequest;
import be.kdg.teamh.dtos.request.KaartRequest;
import be.kdg.teamh.dtos.request.SpelkaartRequest;
import be.kdg.teamh.dtos.request.SubthemaRequest;
import be.kdg.teamh.dtos.response.CommentaarResponse;
import be.kdg.teamh.dtos.response.KaartResponse;
import be.kdg.teamh.dtos.response.SpelkaartResponse;
import be.kdg.teamh.dtos.response.SubthemaResponse;
import be.kdg.teamh.exceptions.CommentsNotAllowed;
import be.kdg.teamh.exceptions.notfound.CirkelsessieNotFound;
import be.kdg.teamh.exceptions.notfound.GebruikerNotFound;
import be.kdg.teamh.exceptions.notfound.HoofdthemaNotFound;
import be.kdg.teamh.exceptions.notfound.KaartNotFound;
import be.kdg.teamh.services.contracts.AuthService;
import be.kdg.teamh.services.contracts.KaartenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kaarten")
public class KaartController
{
    private KaartenService service;
    private AuthService auth;

    @Autowired
    public KaartController(KaartenService service, AuthService auth)
    {
        this.service = service;
        this.auth = auth;
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<KaartResponse> index()
    {
        return service.all();
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public void create(@RequestHeader(name = "Authorization") String token, @RequestBody KaartRequest kaart) throws GebruikerNotFound
    {
        service.create(kaart);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public KaartResponse show(@PathVariable("id") int id) throws KaartNotFound
    {
        return service.find(id);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void update(@PathVariable("id") int id, @RequestBody KaartRequest kaart) throws KaartNotFound, GebruikerNotFound
    {
        service.update(id, kaart);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id) throws KaartNotFound
    {
        service.delete(id);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}/subthemas", method = RequestMethod.GET)
    public List<SubthemaResponse> subthemas(@PathVariable("id") int id) throws KaartNotFound
    {
        return service.getSubthemas(id);
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(value = "{id}/subthemas", method = RequestMethod.POST)
    public void addSubthemaToKaart(@PathVariable("id") int id, @RequestBody SubthemaRequest subthema) throws KaartNotFound, HoofdthemaNotFound
    {
        service.addSubthema(id, subthema);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}/comments", method = RequestMethod.GET)
    public List<CommentaarResponse> comments(@PathVariable("id") int id) throws KaartNotFound
    {
        return service.getCommentaren(id);
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(value = "{id}/comments", method = RequestMethod.POST)
    public void createComment(@PathVariable("id") int id, @RequestBody CommentaarRequest comment) throws CommentsNotAllowed, KaartNotFound, GebruikerNotFound
    {
        service.addCommentaar(id, comment);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}/spelkaarten", method = RequestMethod.GET)
    public List<SpelkaartResponse> getSpelkaarten(@PathVariable("id") int id) throws KaartNotFound
    {
        return service.getSpelkaarten(id);
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(value = "{id}/spelkaarten", method = RequestMethod.POST)
    public void addSpelkaartAanKaart(@PathVariable("id") int id, @RequestBody SpelkaartRequest spelkaart) throws KaartNotFound, CirkelsessieNotFound
    {
        service.addSpelkaart(id, spelkaart);
    }
}
