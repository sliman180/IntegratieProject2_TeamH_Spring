package be.kdg.teamh.controllers;

import be.kdg.teamh.entities.Organisatie;
import be.kdg.teamh.services.OrganisatieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/organisatie")
public class OrganisatieController {

    @Autowired
    private OrganisatieService organisatieService;

    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Organisatie> index() {
        return organisatieService.readAllOrganisaties();
    }


    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public void create(@RequestBody Organisatie organisatie) {

        organisatieService.addOrganisatie(organisatie);

    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Organisatie> getOrganisatie(@PathVariable Integer id) {
        Organisatie organisatie = organisatieService.getOrganisatie(id);
        if (organisatie == null) return new ResponseEntity<>(organisatie, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(organisatie, HttpStatus.OK);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void update(@PathVariable("id") int id, @RequestBody Organisatie organisatie) {
        organisatieService.editOrganisatie(id, organisatie);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id) {
        organisatieService.deleteOrganisatie(id);
    }
}
