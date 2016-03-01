package be.kdg.teamh.controllers;

import be.kdg.teamh.entities.*;
import be.kdg.teamh.exceptions.CommentsNotAllowed;
import be.kdg.teamh.exceptions.KaartNotFoundException;
import be.kdg.teamh.services.KaartenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/kaarten")
public class KaartenController {

    @Autowired
    private KaartenService service;

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Kaart> index() {
        return service.all();
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public void create(@RequestBody Kaart kaart) {
        service.create(kaart);
    }


    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Kaart show(@PathVariable("id") int id) throws KaartNotFoundException {
        return service.find(id);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void update(@PathVariable("id") int id, @RequestBody Kaart kaart) throws KaartNotFoundException {
        service.update(id, kaart);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id) throws KaartNotFoundException {
        service.delete(id);
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(value = "{id}/comments", method = RequestMethod.POST)
    public void createComment(@PathVariable("id") int id, @RequestBody Comment comment) throws CommentsNotAllowed {
        service.createComment(id, comment);
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(value = "{id}/subthemas", method = RequestMethod.POST)
    public void addSubthemaAanKaart(@PathVariable("id") int id, @RequestBody Subthema subthema) {
        service.addSubthema(id, subthema);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}/subthemas", method = RequestMethod.GET)
    public List<Subthema> getSubthemaFromKaart(@PathVariable("id") int id) {
        return service.getSubthemas(id);
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(value = "{id}/spelkaarten", method = RequestMethod.POST)
    public void addSpelkaartAanKaart(@PathVariable("id") int id, @RequestBody Spelkaart spelkaart) {
        service.addSpelkaart(id, spelkaart);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}/spelkaarten", method = RequestMethod.GET)
    public List<Spelkaart> getSpelkaarten(@PathVariable("id") int id) {
        return service.getSpelkaarten(id);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}/comments", method = RequestMethod.GET)

    public List<Comment> allComments(@PathVariable("id") int id) {
        return service.allComments(id);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "/importCards/{csvPath}/", method = RequestMethod.POST)
    public void importCards(@PathVariable("csvPath") String csvPath, @RequestBody Gebruiker gebruiker) throws IOException {
        service.importCards(csvPath, gebruiker);
    }
}
