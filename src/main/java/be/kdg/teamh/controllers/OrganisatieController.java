package be.kdg.teamh.controllers;

import be.kdg.teamh.entities.Organisatie;
import be.kdg.teamh.exceptions.notfound.GebruikerNotFound;
import be.kdg.teamh.exceptions.IsForbidden;
import be.kdg.teamh.exceptions.notfound.OrganisatieNotFound;
import be.kdg.teamh.services.contracts.OrganisatieService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organisaties")
public class OrganisatieController {
    @Autowired
    private OrganisatieService service;

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Organisatie> index(@RequestHeader(name = "Authorization") String token) throws IsForbidden {
        if (!isRegistered(token)) {
            throw new IsForbidden();
        }

        return service.all();
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public void create(@RequestBody Organisatie organisatie, @RequestHeader(name = "Authorization") String token) throws IsForbidden {
        if (!isRegistered(token)) {
            throw new IsForbidden();
        }

        int userId = getUserId(token);

        service.create(userId, organisatie);
    }


    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Organisatie show(@PathVariable int id, @RequestHeader(name = "Authorization") String token) throws OrganisatieNotFound, IsForbidden {
        if (!isRegistered(token)) {
            throw new IsForbidden();
        }

        return service.find(id);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void update(@PathVariable("id") int id, @RequestBody Organisatie organisatie, @RequestHeader(name = "Authorization") String token) throws OrganisatieNotFound, IsForbidden {
        if (!isAdmin(token)) {
            throw new IsForbidden();
        }

        service.update(id, organisatie);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id, @RequestHeader(name = "Authorization") String token) throws OrganisatieNotFound, IsForbidden {
        if (!isRegistered(token)) {
            throw new IsForbidden();
        }

        service.delete(id);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "/my", method = RequestMethod.GET)
    public List<Organisatie> getOrganisaties(@RequestHeader(name = "Authorization") String token) throws IsForbidden, GebruikerNotFound {

        if (!isRegistered(token)) {
            throw new IsForbidden();
        }

        int userId = getUserId(token);

        return service.getMyOrganisaties(userId);
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

