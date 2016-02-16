package be.kdg.teamh.controllers;

import be.kdg.teamh.entities.Hoofdthema;
import be.kdg.teamh.services.HoofdthemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/hoofdthemas")
public class HoofdthemaController
{
    @Autowired
    private HoofdthemaService service;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Hoofdthema> index()
    {
        return service.all();
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public void create(Hoofdthema hoofdthema)
    {
        service.create(hoofdthema);
    }
}
