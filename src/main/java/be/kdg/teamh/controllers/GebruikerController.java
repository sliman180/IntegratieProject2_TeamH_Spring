package be.kdg.teamh.controllers;

import be.kdg.teamh.entities.*;
import be.kdg.teamh.exceptions.notfound.GebruikerNotFound;
import be.kdg.teamh.services.contracts.GebruikerService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gebruikers")
public class GebruikerController {
    @Autowired
    private GebruikerService service;

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Gebruiker> index() {
        return service.all();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public void create(@RequestBody Gebruiker gebruiker) {
        service.create(gebruiker);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Gebruiker show(@PathVariable("id") int id) throws GebruikerNotFound {
        return service.find(id);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void update(@PathVariable("id") int id, @RequestBody Gebruiker gebruiker) throws GebruikerNotFound {
        service.update(id, gebruiker);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id) throws GebruikerNotFound {
        service.delete(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}/cirkelsessies", method = RequestMethod.GET)
    public List<Cirkelsessie> showCirkelsessies(@PathVariable("id") int id) throws GebruikerNotFound {
        return service.showCirkelsessies(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/deelnames", method = RequestMethod.GET)
    public List<Deelname> getDeelnames(@RequestHeader(name = "Authorization") String token) throws GebruikerNotFound {

        return service.findDeelnames(getUserId(token));
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/hoofdthemas", method = RequestMethod.GET)
    public List<Hoofdthema> getHoofdthemas(@RequestHeader(name = "Authorization") String token) throws GebruikerNotFound {

        return service.find(getUserId(token)).getHoofdthemas();
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/subthemas", method = RequestMethod.GET)
    public List<Subthema> getSubthemas(@RequestHeader(name = "Authorization") String token) throws GebruikerNotFound {

        return service.find(getUserId(token)).getSubthemas();
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "/myinfo", method = RequestMethod.GET)
    public Gebruiker showWithToken(@RequestHeader(name = "Authorization") String token) throws GebruikerNotFound
    {
        return service.find(getUserId(token));
    }


    private boolean isAdmin(String token) {
        Claims claims = Jwts.parser().setSigningKey("kandoe").parseClaimsJws(token.substring(7)).getBody();

        return ((List) claims.get("rollen")).contains("admin");
    }

    private boolean isRegistered(String token) {
        Claims claims = Jwts.parser().setSigningKey("kandoe").parseClaimsJws(token.substring(7)).getBody();

        return ((List) claims.get("rollen")).contains("user");
    }

    private int getUserId(String token) {
        return Integer.parseInt(Jwts.parser().setSigningKey("kandoe").parseClaimsJws(token.substring(7)).getBody().getSubject());
    }
}
