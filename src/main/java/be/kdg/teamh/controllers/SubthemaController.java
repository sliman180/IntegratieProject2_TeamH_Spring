package be.kdg.teamh.controllers;

import be.kdg.teamh.dtos.request.KaartRequest;
import be.kdg.teamh.dtos.request.SubthemaRequest;
import be.kdg.teamh.entities.*;
import be.kdg.teamh.exceptions.notfound.GebruikerNotFound;
import be.kdg.teamh.exceptions.notfound.HoofdthemaNotFound;
import be.kdg.teamh.exceptions.notfound.SubthemaNotFound;
import be.kdg.teamh.services.contracts.SubthemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subthemas")
public class SubthemaController {
    private SubthemaService service;

    @Autowired
    public SubthemaController(SubthemaService service) {
        this.service = service;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Subthema> index() {
        return service.all();
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public void create(@RequestBody SubthemaRequest subthema) throws HoofdthemaNotFound, GebruikerNotFound {
        service.create(subthema);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Subthema show(@PathVariable Integer id) throws SubthemaNotFound {
        return service.find(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void update(@PathVariable("id") int id, @RequestBody SubthemaRequest subthema) throws SubthemaNotFound, HoofdthemaNotFound, GebruikerNotFound {
        service.update(id, subthema);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id) throws SubthemaNotFound {
        service.delete(id);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}/organisatie", method = RequestMethod.GET)
    public Organisatie getOrganisatie(@PathVariable Integer id) throws SubthemaNotFound {
        return service.findOrganisatie(id);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}/cirkelsessies", method = RequestMethod.GET)
    public List<Cirkelsessie> getCirkelsessies(@PathVariable Integer id) throws SubthemaNotFound {
        return service.findCirkelsessies(id);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}/hoofdthema", method = RequestMethod.GET)
    public Hoofdthema getHoofdthema(@PathVariable Integer id) throws SubthemaNotFound {
        return service.findHoofdthema(id);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}/kaarten", method = RequestMethod.GET)
    public List<Kaart> getKaarten(@PathVariable Integer id) throws SubthemaNotFound {
        return service.findKaarten(id);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "{id}/kaart", method = RequestMethod.POST)
    public void createKaart(@PathVariable("id") int subthemaId, @RequestHeader(name = "Authorization") String token, @RequestBody KaartRequest kaart) throws SubthemaNotFound, GebruikerNotFound {

        service.addKaart(subthemaId, kaart);
    }

}
