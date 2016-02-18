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
import org.springframework.test.web.servlet.MvcResult;
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

        MvcResult result = this.mvc.perform(post("/organisatie/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)).andDo(print())
                .andExpect(status().isOk()).andReturn();


        String jsonn = result.getResponse().getContentAsString();
        Organisatie organisatie = gson.fromJson(jsonn, Organisatie.class);


        this.mvc.perform(get("/organisatie").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(organisatie.getId())))
                .andExpect(jsonPath("$[0].naam", is(organisatie.getNaam())))
                .andExpect(jsonPath("$[0].beschrijving", is(organisatie.getBeschrijving())));
    }


    @Test
    public void bestaatNietOrganisatie() throws Exception {
        Integer id = 420;

        this.mvc.perform(get("/organisatie/get/" + id))
                .andExpect(status().isNotFound());
    }

    /*@Test
    public void wijzigOrganisatie() throws Exception {
        organisatie.setNaam("NieuweNaam");

        Gson gson = new Gson();
        String gewijzigdeOrganisatieString = gson.toJson(organisatie);



        this.mvc.perform(put("/organisatie/edit")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(gewijzigdeOrganisatieString))
                .andExpect(status().isOk());


        this.mvc.perform(get("/organisatie/get/"+organisatie.getId()+"")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andDo(print())
                .andExpect(jsonPath("$.naam", is("NieuweNaam")));



//        this.mvc.perform("organisatie/get/" + id)
//                .andExpect(status().);
    }*/

    @Test
    public void getOrganisatie() throws Exception {

        String json = gson.toJson(new Organisatie("NaamOrganisatieOld", "Beschrijving", testGebruiker));

        MvcResult result = this.mvc.perform(post("/organisatie/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)).andDo(print())
                .andExpect(status().isOk()).andReturn();


        String jsonn = result.getResponse().getContentAsString();
        Organisatie opgeslagenOrganisatie = gson.fromJson(jsonn, Organisatie.class);

        int id = opgeslagenOrganisatie.getId();

        this.mvc.perform(get("/organisatie/get/" + id))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void verwijderOrganisatie() throws Exception {

        Organisatie organisatie = new Organisatie("teVerwijderenOrganisatie", "Beschrijving", testGebruiker);
        String json = gson.toJson(organisatie);

        MvcResult result = this.mvc.perform(post("/organisatie/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)).andDo(print())
                .andExpect(status().isOk()).andReturn();

        String jsonn = result.getResponse().getContentAsString();
        Organisatie opgeslagenOrganisatie = gson.fromJson(jsonn, Organisatie.class);


        int id = opgeslagenOrganisatie.getId();


        this.mvc.perform(get("/organisatie/get/" + id))
                .andExpect(status().isOk());

        this.mvc.perform(delete("/organisatie/delete/" + id))
                .andExpect(status().isOk());

        this.mvc.perform(get("/organisatie/get/" + id))
                .andExpect(status().isNotFound());
    }


}
