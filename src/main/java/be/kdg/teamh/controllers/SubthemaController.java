package be.kdg.teamh.controllers;

import be.kdg.teamh.entities.Subthema;
import be.kdg.teamh.services.SubthemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subthemas")
public class SubthemaController {

    @Autowired
    private SubthemaService subthemaService;

    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public void create(@RequestBody Subthema subthema){
        this.subthemaService.createSubthema(subthema);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Subthema> getAll(){
        return this.subthemaService.findAll();
    }
}
