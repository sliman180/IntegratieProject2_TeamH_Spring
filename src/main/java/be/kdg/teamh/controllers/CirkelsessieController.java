package be.kdg.teamh.controllers;

import be.kdg.teamh.entities.Cirkelsessie;
import be.kdg.teamh.entities.Kaart;
import be.kdg.teamh.entities.Subthema;
import be.kdg.teamh.exceptions.notfound.CirkelsessieNotFound;
import be.kdg.teamh.services.contracts.CirkelsessieService;
import org.apache.log4j.Logger;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;

@RestController
@RequestMapping("/api/cirkelsessies")
public class CirkelsessieController
{

    public final static Logger logger = Logger.getLogger(CirkelsessieController.class);

    @Autowired
    private CirkelsessieService service;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Cirkelsessie> index()
    {
        return service.all();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public void create(@RequestBody Cirkelsessie cirkelsessie, @RequestHeader(name = "Authorization") String token)
    {
        int userId = getUserId(token);
        service.create(userId, cirkelsessie);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Cirkelsessie show(@PathVariable int id) throws CirkelsessieNotFound
    {
        return service.find(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void update(@PathVariable("id") int id, @RequestBody Cirkelsessie cirkelsessie) throws CirkelsessieNotFound
    {
        service.update(id, cirkelsessie);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id) throws CirkelsessieNotFound
    {
        service.delete(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}/subthema", method = RequestMethod.GET)
    public Subthema getSubthema(@PathVariable("id") int id) throws CirkelsessieNotFound
    {
        return service.find(id).getSubthema();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "{id}/clone", method = RequestMethod.POST)
    public void clone(@PathVariable("id") int id) throws CirkelsessieNotFound
    {
        service.clone(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "gepland", method = RequestMethod.GET)
    public List<Cirkelsessie> gepland()
    {
        return service.gepland();
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "actief", method = RequestMethod.GET)
    public List<Cirkelsessie> actief()
    {
        return service.actief();
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}/invite",method = RequestMethod.POST)
    public void invite(@RequestBody List<String> emails) {
        try {
            service.invite(emails);
        } catch (MessagingException e) {
            logger.error("*********ERROR inviting guests: " + e);
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "{id}/spelkaart", method = RequestMethod.POST)
    public void createKaart(@PathVariable("id") int id, @RequestBody Kaart kaart) throws CirkelsessieNotFound
    {
        service.addSpelkaart(id, kaart);
    }

    private int getUserId(String token)
    {
        return Integer.parseInt(Jwts.parser().setSigningKey("kandoe").parseClaimsJws(token.substring(7)).getBody().getSubject());
    }
}
