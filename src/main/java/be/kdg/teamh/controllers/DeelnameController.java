package be.kdg.teamh.controllers;

import be.kdg.teamh.entities.Deelname;
import be.kdg.teamh.exceptions.AlreadyJoinedCirkelsessie;
import be.kdg.teamh.exceptions.CirkelsessieNotFound;
import be.kdg.teamh.exceptions.DeelnameNotFound;
import be.kdg.teamh.exceptions.GebruikerNotFound;
import be.kdg.teamh.services.contracts.DeelnameService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deelnames")
public class DeelnameController {
    @Autowired
    private DeelnameService service;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Deelname> index() {
        return service.all();
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Deelname show(@PathVariable("id") int id) throws DeelnameNotFound {
        return service.find(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "{id}", method = RequestMethod.POST)
    public void create(@PathVariable("id") int id, @RequestHeader(name = "Authorization") String token) throws CirkelsessieNotFound, GebruikerNotFound, DeelnameNotFound, AlreadyJoinedCirkelsessie {
        int userId = getUserId(token);
        this.service.create(id, userId);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void update(@PathVariable("id") int id, @RequestBody Deelname deelname) throws DeelnameNotFound {
        this.service.update(id, deelname);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id) throws DeelnameNotFound {
        service.delete(id);
    }

    private int getUserId(String token) {
        Claims claims = Jwts.parser().setSigningKey("kandoe")
                .parseClaimsJws(token.substring(7)).getBody();
        return Integer.parseInt(claims.getSubject());
    }


}
