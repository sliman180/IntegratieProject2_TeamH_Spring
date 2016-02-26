package be.kdg.teamh.controllers;

import be.kdg.teamh.entities.Comment;
import be.kdg.teamh.entities.Kaart;
import be.kdg.teamh.exceptions.CommentsNotAllowed;
import be.kdg.teamh.exceptions.KaartNotFoundException;
import be.kdg.teamh.services.KaartenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kaarten")
public class KaartenController
{
    @Autowired
    private KaartenService kaartenService;

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Kaart> index()
    {
        return kaartenService.all();
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public void create(@RequestBody Kaart kaart)
    {
        kaartenService.create(kaart);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Kaart show(@PathVariable("id") int id) throws KaartNotFoundException
    {
        return kaartenService.find(id);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void update(@PathVariable("id") int id, @RequestBody Kaart kaart) throws KaartNotFoundException
    {
        kaartenService.update(id, kaart);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id) throws KaartNotFoundException
    {
        kaartenService.delete(id);
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(value = "{id}/addComment", method = RequestMethod.POST)
    public void createComment(@PathVariable("id") int id, @RequestBody Comment comment) throws CommentsNotAllowed
    {
        kaartenService.createComment(id, comment);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "/{id}/getComments", method = RequestMethod.GET)
    public List<Comment> allComments(@PathVariable("id") int id)
    {
        return kaartenService.allComments(id);
    }
}
