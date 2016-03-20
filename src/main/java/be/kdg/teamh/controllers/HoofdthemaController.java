package be.kdg.teamh.controllers;

import be.kdg.teamh.dtos.request.HoofdthemaRequest;
import be.kdg.teamh.entities.Hoofdthema;
import be.kdg.teamh.entities.Organisatie;
import be.kdg.teamh.entities.Subthema;
import be.kdg.teamh.services.contracts.AuthService;
import be.kdg.teamh.services.contracts.HoofdthemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/hoofdthemas")
public class HoofdthemaController
{
    private HoofdthemaService service;
    private AuthService auth;

    @Autowired
    public HoofdthemaController(HoofdthemaService service, AuthService auth)
    {
        this.service = service;
        this.auth = auth;
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Hoofdthema> index()
    {
        return service.all();
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public void create(@RequestHeader(name = "Authorization") String token, @Valid @RequestBody HoofdthemaRequest hoofdthema)
    {
        auth.checkUserIsRegistered(token);

        service.create(hoofdthema);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Hoofdthema show(@PathVariable("id") int id)
    {
        return service.find(id);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void update(@PathVariable("id") int id, @RequestHeader(name = "Authorization") String token, @Valid @RequestBody HoofdthemaRequest hoofdthema)
    {
        auth.checkUserIsRegistered(token);
        auth.checkUserIsAllowed(token, service.find(id).getGebruiker());

        service.update(id, hoofdthema);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id, @RequestHeader(name = "Authorization") String token)
    {
        auth.checkUserIsRegistered(token);
        auth.checkUserIsAllowed(token, service.find(id).getGebruiker());

        service.delete(id);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}/organisatie", method = RequestMethod.GET)
    public Organisatie getOrganisatie(@PathVariable("id") int id)
    {
        return service.findOrganisatie(id);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}/subthemas", method = RequestMethod.GET)
    public List<Subthema> getSubthemas(@PathVariable("id") int id)
    {
        return service.findSubthemas(id);
    }


}
