package be.kdg.teamh.controllers;

import be.kdg.teamh.entities.Subthema;
import be.kdg.teamh.services.SubthemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by lollik on 18/02/2016.
 */
@RestController
@RequestMapping("/subthemas")
public class SubthemaController {

    @Autowired
    private SubthemaService subthemaService;

    @RequestMapping(value = "create", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Subthema create(@RequestBody Subthema subthema){return subthemaService.subthemaMaken(subthema);}

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<Subthema> get(){return subthemaService.subthemasOphalen();}

}
