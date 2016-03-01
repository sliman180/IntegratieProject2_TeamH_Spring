package be.kdg.teamh.controllers;

import be.kdg.teamh.entities.Organisatie;
import be.kdg.teamh.entities.Rol;
import be.kdg.teamh.exceptions.IsForbidden;
import be.kdg.teamh.services.OrganisatieService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/organisaties")
public class OrganisatieController {
    @Autowired
    private OrganisatieService service;

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Organisatie> index(@RequestHeader(name = "Authorization") String token) throws IsForbidden {
        if (checkUser(token)) return service.all();
        else throw new IsForbidden();
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public void create(@RequestBody Organisatie organisatie,
                       @RequestHeader(name = "Authorization") String token) throws IsForbidden{
        if (checkAdmin(token)) service.create(organisatie);
        else throw new IsForbidden();
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Organisatie show(@PathVariable int id,
                            @RequestHeader(name = "Authorization") String token) throws IsForbidden{
        if (checkUser(token)) return service.find(id);
        else throw new IsForbidden();
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void update(@PathVariable("id") int id, @RequestBody Organisatie organisatie,
                       @RequestHeader(name = "Authorization") String token) throws IsForbidden{
        if (checkAdmin(token)) service.update(id, organisatie);
        else throw new IsForbidden();
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id,
                       @RequestHeader(name = "Authorization") String token) throws IsForbidden{
        if (checkAdmin(token)) service.delete(id);
        else throw new IsForbidden();
    }

    private Boolean checkAdmin(String token) {
        token = token.substring(7);
        final Claims claims = Jwts.parser().setSigningKey("secretKey")
                .parseClaimsJws(token)
                .getBody();

        return ((List<Rol>) claims.get("roles")).contains("admin");
    }

    private Boolean checkUser(String token) {
        token = token.substring(7);
        final Claims claims = Jwts.parser().setSigningKey("secretKey")
                .parseClaimsJws(token)
                .getBody();

        return ((List<Rol>) claims.get("roles")).contains("user");
    }
}
