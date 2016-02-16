package be.kdg.teamh;


import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.entities.Organisatie;
import be.kdg.teamh.services.OrganisatieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class OrganisatieIT {

    @Autowired
    public OrganisatieService organisatieService;

    Gebruiker testGebruiker;
    Organisatie organisatie;


   /* @Before
    public void initialize(){
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

    */

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;


    @Before
    public void setUp() throws Exception{
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();

        testGebruiker = new Gebruiker("Naam", "Voornaam", "test@hotmail.be", "wachtwoord");
        organisatie = new Organisatie("NaamOrganisatie", "Beschrijving", testGebruiker);


        String nieuwOrganisatieString = new ObjectMapper().writeValueAsString(organisatie);
        this.mvc.perform(post("/organisatie/create")
                .accept(MediaType.APPLICATION_JSON)
                .content(nieuwOrganisatieString));
    }


    @Test
    public void maakOrganisatie() throws Exception {

        Organisatie organisatie = new Organisatie("NaamOrganisatie", "Beschrijving", testGebruiker);


        String nieuwOrganisatieString = new ObjectMapper().writeValueAsString(organisatie);
        this.mvc.perform(post("/organisatie/create")
                .accept(MediaType.APPLICATION_JSON)
                .content(nieuwOrganisatieString))
                .andExpect(status().isCreated());
    }


    @Test
    public void bestaatNietOrganisatie() throws Exception {
        int id = 420;

        this.mvc.perform(get("/organisatie/get/" + id))
            .andExpect(status().isNotFound());
    }

    @Test
    public void wijzigOrganisatie() throws Exception {
        organisatie.setNaam("NieuweNaam");
        String gewijzigdeOrganisatieString = new ObjectMapper().writeValueAsString(organisatie);


        this.mvc.perform(put("/organisatie/edit")
                .accept(MediaType.APPLICATION_JSON)
                .content(gewijzigdeOrganisatieString))
                .andExpect(status().isOk());


//        this.mvc.perform("organisatie/get/" + id)
//                .andExpect(status().);
    }

    @Test
    public void verwijderOrganisatie() throws Exception {
        int id = 1;

        this.mvc.perform(delete("/organisatie/delete/" + id))
                .andExpect(status().isOk());
    }
}
