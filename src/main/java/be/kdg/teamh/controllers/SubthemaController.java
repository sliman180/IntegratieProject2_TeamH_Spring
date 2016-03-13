package be.kdg.teamh.controllers;

import be.kdg.teamh.entities.Kaart;
import be.kdg.teamh.entities.Subthema;
import be.kdg.teamh.exceptions.GebruikerNotFound;
import be.kdg.teamh.exceptions.HoofdthemaNotFound;
import be.kdg.teamh.exceptions.IsForbidden;
import be.kdg.teamh.exceptions.SubthemaNotFound;
import be.kdg.teamh.services.contracts.SubthemaService;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subthemas")
public class SubthemaController {
    @Autowired
    private SubthemaService service;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Subthema> index() {
        return service.all();
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public void create(@RequestBody Subthema subthema, @RequestHeader(name = "Authorization") String token) throws GebruikerNotFound {
        service.create(getUserId(token), subthema);
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(value = "hoofdthema={id}", method = RequestMethod.POST)
    public void create(@RequestBody Subthema subthema, @PathVariable("id") int hoofdthemaId, @RequestHeader(name = "Authorization") String token) throws GebruikerNotFound, HoofdthemaNotFound {
        service.create(getUserId(token), hoofdthemaId, subthema);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Subthema show(@PathVariable Integer id) throws SubthemaNotFound {
        return service.find(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void update(@PathVariable("id") int id, @RequestBody Subthema subthema) throws SubthemaNotFound {
        service.update(id, subthema);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id) throws SubthemaNotFound {
        service.delete(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "{id}/kaart", method = RequestMethod.POST)
    public void createKaart(@PathVariable("id") int subthemaId, @RequestHeader(name = "Authorization") String token, @RequestBody Kaart kaart) throws SubthemaNotFound, GebruikerNotFound {

        service.addKaart(subthemaId, getUserId(token), kaart);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}/kaarten", method = RequestMethod.GET)
    public List<Kaart> getKaarten(@PathVariable("id") int subthemaId, @RequestHeader(name = "Authorization") String token) throws SubthemaNotFound, GebruikerNotFound, IsForbidden {

        return service.getKaarten(getUserId(token), subthemaId);
    }


    private int getUserId(String token) {
        return Integer.parseInt(Jwts.parser().setSigningKey("kandoe").parseClaimsJws(token.substring(7)).getBody().getSubject());
    }
}
