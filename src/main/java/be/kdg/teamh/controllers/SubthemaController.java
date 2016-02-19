package be.kdg.teamh.controllers;

import be.kdg.teamh.entities.Subthema;
import be.kdg.teamh.services.SubthemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subthemas")
public class SubthemaController {

    @Autowired
    private SubthemaService subthemaService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Subthema subthema){ subthemaService.subthemaMaken(subthema);}

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<Subthema> getAll(){
        return subthemaService.subthemasOphalen();
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Subthema> getOne(@PathVariable Integer id) {
        Subthema subthema = subthemaService.subthemaOphalen(id);
        if (subthema == null) return new ResponseEntity<>(subthema, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(subthema, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") int id){subthemaService.subthemaVerwijderen(id);}

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("id") int id, @RequestBody Subthema subthema){
        subthemaService.subthemaAnpassen(id,subthema);
    }


}
