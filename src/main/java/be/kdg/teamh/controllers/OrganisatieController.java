package be.kdg.teamh.controllers;

import be.kdg.teamh.entities.Organisatie;
import be.kdg.teamh.services.OrganisatieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
}
