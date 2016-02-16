package be.kdg.teamh.controllers;

import be.kdg.teamh.entities.Organisatie;
import be.kdg.teamh.services.OrganisatieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/organisatie")
public class OrganisatieController {

    @Autowired
    private OrganisatieService organisatieService;

    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void maakNieuweOrganisatie(Organisatie organisatie) {

        organisatieService.addOrganisatie(organisatie);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseEntity<Organisatie> getOrganisatie(@PathVariable Integer id) {
        Organisatie organisatie = organisatieService.getOrganisatie(id);
        if (organisatie == null) return new ResponseEntity<Organisatie>(organisatie, HttpStatus.NOT_FOUND);
        return new ResponseEntity<Organisatie>(organisatie, HttpStatus.OK);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.PUT)
    public void wijzigOrganisatie(Organisatie organisatie) {
        organisatieService.editOrganisatie(organisatie);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void verwijderOrganisatie(@PathVariable Integer id){
        organisatieService.deleteOrganisatie(id);
    }
}
