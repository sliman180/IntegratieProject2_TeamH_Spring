package be.kdg.teamh;


import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.entities.Organisatie;
import be.kdg.teamh.services.OrganisatieService;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class OrganisatieIT {

    @Autowired
    public OrganisatieService organisatieService;

    @Mock
    Gebruiker testGebruiker;


    @Autowired
    private WebApplicationContext context;

    @Autowired
    private Gson gson;

    private MockMvc mvc;


    @Before
    public void setUp() throws Exception {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();

        testGebruiker = new Gebruiker("Naam", "Voornaam", "test@hotmail.be", "wachtwoord");

    }


    @Test
    public void haalLijstOp() throws Exception {

        this.mvc.perform(get("/organisatie").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(print())
                .andExpect(jsonPath("$", hasSize(0)));


    }

    @Test
    public void maakOrganisatie() throws Exception {

        String json = gson.toJson(new Organisatie("NaamOrganisatie", "Beschrijving", testGebruiker));

        this.mvc.perform(post("/organisatie")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)).andDo(print())
                .andExpect(status().isCreated());


        this.mvc.perform(get("/organisatie").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].naam", is("NaamOrganisatie")))
                .andExpect(jsonPath("$[0].beschrijving", is("Beschrijving")));
    }


    @Test
    public void bestaatNietOrganisatie() throws Exception {
        Integer id = 420;

        this.mvc.perform(get("/organisatie/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    public void wijzigOrganisatie() throws Exception {
        Organisatie organisatie = new Organisatie("teWijzigenNaam", "Beschrijving", testGebruiker);
        String json = gson.toJson(organisatie);

        this.mvc.perform(post("/organisatie")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)).andDo(print())
                .andExpect(status().isCreated());

        organisatie.setNaam("NieuweNaam");


        Gson gson = new Gson();
        String gewijzigdeOrganisatieString = gson.toJson(organisatie);

        this.mvc.perform(put("/organisatie/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gewijzigdeOrganisatieString))
                .andExpect(status().isOk());


        this.mvc.perform(get("/organisatie/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(print())
                .andExpect(jsonPath("$.naam", is("NieuweNaam"))).andDo(print());
    }

    @Test
    public void getOrganisatie() throws Exception {

        String json = gson.toJson(new Organisatie("NaamOrganisatieOld", "Beschrijving", testGebruiker));
        this.mvc.perform(post("/organisatie")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)).andDo(print())
                .andExpect(status().isCreated());


        this.mvc.perform(get("/organisatie/1"))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void verwijderOrganisatie() throws Exception {

        Organisatie organisatie = new Organisatie("teVerwijderenOrganisatie", "Beschrijving", testGebruiker);
        String json = gson.toJson(organisatie);

        this.mvc.perform(post("/organisatie")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)).andDo(print())
                .andExpect(status().isCreated());

        this.mvc.perform(get("/organisatie/1"))
                .andExpect(status().isOk());

        this.mvc.perform(delete("/organisatie/1"))
                .andExpect(status().isOk());

        this.mvc.perform(get("/organisatie/1"))
                .andExpect(status().isNotFound());
    }


}
