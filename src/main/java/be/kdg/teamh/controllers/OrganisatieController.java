package be.kdg.teamh.controllers;

import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.entities.Organisatie;
import be.kdg.teamh.exceptions.GebruikerNotFound;
import be.kdg.teamh.exceptions.IsForbidden;
import be.kdg.teamh.exceptions.OrganisatieNotFound;
import be.kdg.teamh.services.contracts.AuthService;
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

    @Autowired
    private AuthService auth;

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Organisatie> index(@RequestHeader(name = "Authorization") String token) throws IsForbidden
    {
        if (!auth.isRegistered(token)) {
            throw new IsForbidden();
        }
        return service.all();
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public void create(@RequestBody Organisatie organisatie, @RequestHeader(name = "Authorization") String token) throws IsForbidden, GebruikerNotFound {
        if (!auth.isRegistered(token)) {
            throw new IsForbidden();
        }

        Gebruiker gebruiker = auth.findByToken(token);

        service.create(gebruiker.getId(), organisatie);
    }


    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Organisatie show(@PathVariable int id, @RequestHeader(name = "Authorization") String token) throws OrganisatieNotFound, IsForbidden {
        if (!auth.isRegistered(token)) {
            throw new IsForbidden();
        }

        return service.find(id);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void update(@PathVariable("id") int id, @RequestBody Organisatie organisatie, @RequestHeader(name = "Authorization") String token) throws OrganisatieNotFound, IsForbidden {
        if (!auth.isAdmin(token)) {
            throw new IsForbidden();
        }

        service.update(id, organisatie);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id, @RequestHeader(name = "Authorization") String token) throws OrganisatieNotFound, IsForbidden {
        if (!auth.isRegistered(token)) {
            throw new IsForbidden();
        }

        service.delete(id);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "/my", method = RequestMethod.GET)
    public List<Organisatie> getOrganisaties(@RequestHeader(name = "Authorization") String token) throws IsForbidden, GebruikerNotFound {

        if (!auth.isRegistered(token)) {
            throw new IsForbidden();
        }

        Gebruiker gebruiker = auth.findByToken(token);

        return service.getMyOrganisaties(gebruiker.getId());
    }

}

