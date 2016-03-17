package be.kdg.teamh.controllers;

import be.kdg.teamh.dtos.request.DeelnameRequest;
import be.kdg.teamh.entities.Cirkelsessie;
import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.exceptions.notfound.CirkelsessieNotFound;
import be.kdg.teamh.exceptions.notfound.DeelnameNotFound;
import be.kdg.teamh.exceptions.notfound.GebruikerNotFound;
import be.kdg.teamh.services.contracts.DeelnameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/deelnames")
public class DeelnameController {
    private DeelnameService service;

    @Autowired
    public DeelnameController(DeelnameService service) {
        this.service = service;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void update(@PathVariable("id") int id, @RequestBody DeelnameRequest deelname) throws DeelnameNotFound, GebruikerNotFound, CirkelsessieNotFound {
        service.update(id, deelname);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id) throws DeelnameNotFound {
        service.delete(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}/gebruiker", method = RequestMethod.GET)
    public Gebruiker getGebruiker(@PathVariable("id") int id) throws DeelnameNotFound {
        return service.getGebruiker(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}/cirkelsessie", method = RequestMethod.GET)
    public Cirkelsessie getCirkelsessie(@PathVariable("id") int id) throws DeelnameNotFound {
        return service.getCirkelsessie(id);
    }
}
