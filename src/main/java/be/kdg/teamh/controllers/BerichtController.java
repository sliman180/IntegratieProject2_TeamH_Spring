package be.kdg.teamh.controllers;

import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.services.contracts.BerichtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/berichten")
public class BerichtController
{
    private BerichtService service;

    @Autowired
    public BerichtController(BerichtService service)
    {
        this.service = service;
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}/gebruiker", method = RequestMethod.GET)
    public Gebruiker getGebruiker(@PathVariable("id") int id)
    {
        return service.getGebruiker(id);
    }
}
