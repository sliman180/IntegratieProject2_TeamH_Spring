package be.kdg.teamh.controllers;

import be.kdg.teamh.entities.Organisatie;
import be.kdg.teamh.services.OrganisatieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Organisatie getOrganisatie(@PathVariable Integer id) {
        return organisatieService.getOrganisatie(id);
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
