package be.kdg.teamh.controllers;

import be.kdg.teamh.dtos.request.HoofdthemaRequest;
import be.kdg.teamh.entities.Hoofdthema;
import be.kdg.teamh.entities.Organisatie;
import be.kdg.teamh.entities.Subthema;
import be.kdg.teamh.exceptions.notfound.GebruikerNotFound;
import be.kdg.teamh.exceptions.notfound.HoofdthemaNotFound;
import be.kdg.teamh.exceptions.notfound.OrganisatieNotFound;
import be.kdg.teamh.services.contracts.HoofdthemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hoofdthemas")
public class HoofdthemaController {
    private HoofdthemaService service;

    @Autowired
    public HoofdthemaController(HoofdthemaService service) {
        this.service = service;
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Hoofdthema> index() {
        return service.all();
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public void create(@RequestBody HoofdthemaRequest hoofdthema) throws GebruikerNotFound, OrganisatieNotFound {
        service.create(hoofdthema);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Hoofdthema show(@PathVariable("id") int id) throws HoofdthemaNotFound {
        return service.find(id);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void update(@PathVariable("id") int id, @RequestBody HoofdthemaRequest hoofdthema) throws HoofdthemaNotFound, GebruikerNotFound, OrganisatieNotFound {
        service.update(id, hoofdthema);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id) throws HoofdthemaNotFound {
        service.delete(id);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}/organisatie", method = RequestMethod.GET)
    public Organisatie getOrganisatie(@PathVariable("id") int id) throws HoofdthemaNotFound {
        return service.findOrganisatie(id);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}/subthemas", method = RequestMethod.GET)
    public List<Subthema> showSubthemas(@PathVariable("id") int id) throws HoofdthemaNotFound {
        return service.showSubthemas(id);
    }
}
