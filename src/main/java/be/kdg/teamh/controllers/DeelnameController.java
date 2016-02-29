package be.kdg.teamh.controllers;

import be.kdg.teamh.entities.Deelname;
import be.kdg.teamh.exceptions.DeelnameNotFound;
import be.kdg.teamh.repositories.DeelnameRepository;
import be.kdg.teamh.services.DeelnameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/deelnames")
public class DeelnameController {

    @Autowired
    private DeelnameService service;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}",method = RequestMethod.GET)
    public Deelname show(@PathVariable("id") int id) throws DeelnameNotFound {
        return service.find(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "",method = RequestMethod.POST)
    public void create(@RequestBody Deelname deelname){
        this.service.create(deelname);
    }

}
