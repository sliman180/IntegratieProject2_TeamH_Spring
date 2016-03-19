package be.kdg.teamh.controllers;

import be.kdg.teamh.dtos.request.KaartRequest;
import be.kdg.teamh.dtos.request.SubthemaRequest;
import be.kdg.teamh.entities.*;
import be.kdg.teamh.services.contracts.AuthService;
import be.kdg.teamh.services.contracts.SubthemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/subthemas")
public class SubthemaController
{
    private SubthemaService service;
    private AuthService auth;

    @Autowired
    public SubthemaController(SubthemaService service, AuthService auth)
    {
        this.service = service;
        this.auth = auth;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Subthema> index()
    {
        return service.all();
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public void create(@Valid @RequestBody SubthemaRequest subthema)
    {
        service.create(subthema);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Subthema show(@PathVariable int id)
    {
        return service.find(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void update(@PathVariable("id") int id, @Valid @RequestBody SubthemaRequest subthema,@RequestHeader("Authorization") String token)
    {
        service.update(id,auth.findByToken(token), subthema);
    }


    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id, @RequestHeader("Authorization") String token)
    {
        service.delete(id, auth.findByToken(token));
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}/organisatie", method = RequestMethod.GET)
    public Organisatie getOrganisatie(@PathVariable int id)
    {
        return service.findOrganisatie(id);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}/cirkelsessies", method = RequestMethod.GET)
    public List<Cirkelsessie> getCirkelsessies(@PathVariable int id)
    {
        return service.findCirkelsessies(id);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}/hoofdthema", method = RequestMethod.GET)
    public Hoofdthema getHoofdthema(@PathVariable int id)
    {
        return service.findHoofdthema(id);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}/kaarten", method = RequestMethod.GET)
    public List<Kaart> getKaarten(@PathVariable int id)
    {
        return service.findKaarten(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "{id}/kaart", method = RequestMethod.POST)
    public void createKaart(@PathVariable("id") int id, @Valid @RequestBody KaartRequest kaart)
    {
        service.addKaart(id, kaart);
    }
}
