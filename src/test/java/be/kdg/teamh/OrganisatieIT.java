package be.kdg.teamh;


import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.entities.Organisatie;
import be.kdg.teamh.services.OrganisatieService;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class OrganisatieIT {

    public OrganisatieService organisatieService;
    Gebruiker organisator;
    Organisatie organisatie;

    @Before
    public void initialize(){
        organisatieService = new OrganisatieService();
        organisator = new Gebruiker("TestNaam","TestVoornaam","test@hotmail.be","wachtwoord");
        organisatie = new Organisatie("NaamOrganisatie","Beschrijving", organisator);
    }

    @Test
    public void maakOrganisatie(){
        organisatieService.addOrganisatie(organisatie);
        Organisatie nieuweOrganisatie = organisatieService.getOrganisatie(organisatie.getId());
        assertNotNull(nieuweOrganisatie);
    }

    @Test
    public void bestaatNietOrganisatie(){
        Organisatie nieuweOrganisatie = new Organisatie("KdG","Applicatieontwikkeling", organisator);
        Organisatie o = organisatieService.getOrganisatie(nieuweOrganisatie.getId());
        assertNull(o);
    }
}
