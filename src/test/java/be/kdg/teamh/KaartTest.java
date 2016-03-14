package be.kdg.teamh;

import be.kdg.teamh.dtos.Token;
import be.kdg.teamh.entities.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import org.springframework.web.util.NestedServletException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class KaartTest {
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    public void indexKaarten() throws Exception {
        this.mvc.perform(get("/api/kaarten").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void createKaart() throws Exception {
        String json = objectMapper.writeValueAsString(new Kaart("Een kaartje", "http://www.afbeeldingurl.be", true, null));

        this.mvc.perform(post("/api/kaarten").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getUserToken()))
                .andExpect(status().isCreated());

        this.mvc.perform(get("/api/kaarten").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].tekst", is("Een kaartje")))
                .andExpect(jsonPath("$[0].imageUrl", is("http://www.afbeeldingurl.be")));
    }

    @Test(expected = NestedServletException.class)
    public void createKaart_nullInput() throws Exception {
        String json = objectMapper.writeValueAsString(new Kaart(null, null, true, null));

        this.mvc.perform(post("/api/kaarten").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getUserToken()));
    }

    @Test
    public void showKaart() throws Exception {
        String json = objectMapper.writeValueAsString(new Kaart("Een kaartje", "http://www.afbeeldingurl.be", true, null));

        this.mvc.perform(post("/api/kaarten").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getUserToken()))
                .andExpect(status().isCreated());

        this.mvc.perform(get("/api/kaarten/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.imageUrl", is("http://www.afbeeldingurl.be")))
                .andExpect(jsonPath("$.tekst", is("Een kaartje")));
    }

    @Test(expected = NestedServletException.class)
    public void showKaart_nonExistingKaart() throws Exception {
        this.mvc.perform(get("/api/kaarten/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateKaart() throws Exception {
        Kaart kaart = new Kaart("Een kaartje", "http://www.afbeeldingurl.be", true, null);
        String json = objectMapper.writeValueAsString(kaart);

        this.mvc.perform(post("/api/kaarten").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getUserToken()))
                .andExpect(status().isCreated());

        kaart = new Kaart("Een gewijzigde kaartje", "http://www.gewijzigdeafbeeldingurl.be", true, null);
        json = objectMapper.writeValueAsString(kaart);

        this.mvc.perform(put("/api/kaarten/1").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());

        this.mvc.perform(get("/api/kaarten/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tekst", is("Een gewijzigde kaartje")))
                .andExpect(jsonPath("$.imageUrl", is("http://www.gewijzigdeafbeeldingurl.be")));
    }

    @Test(expected = NestedServletException.class)
    public void updateKaart_nullInput() throws Exception {
        Kaart kaart = new Kaart("Een kaartje", "http://www.afbeeldingurl.be", true, null);
        String json = objectMapper.writeValueAsString(kaart);

        this.mvc.perform(post("/api/kaarten").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getUserToken()))
                .andExpect(status().isCreated());

        kaart = new Kaart(null, null, true, null);
        json = objectMapper.writeValueAsString(kaart);

        this.mvc.perform(put("/api/kaarten/1").contentType(MediaType.APPLICATION_JSON).content(json));
    }

    @Test(expected = NestedServletException.class)
    public void updateKaart_nonExistingKaart() throws Exception {
        Kaart kaart = new Kaart("Een kaartje", "http://www.afbeeldingurl.be", true, null);
        String json = objectMapper.writeValueAsString(kaart);

        this.mvc.perform(post("/api/kaarten").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getUserToken()))
                .andExpect(status().isCreated());

        kaart = new Kaart("Een gewijzigde kaartje", "http://www.gewijzigdeafbeeldingurl.be", true, null);
        json = objectMapper.writeValueAsString(kaart);

        this.mvc.perform(put("/api/kaarten/2").contentType(MediaType.APPLICATION_JSON).content(json));
    }

    @Test
    public void deleteKaart() throws Exception {
        Kaart kaart = new Kaart("Een kaartje", "http://www.afbeeldingurl.be", true, null);
        String json = objectMapper.writeValueAsString(kaart);

        this.mvc.perform(post("/api/kaarten").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getUserToken()))
                .andExpect(status().isCreated());

        this.mvc.perform(get("/api/kaarten").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        this.mvc.perform(delete("/api/kaarten/1"))
                .andExpect(status().isOk());

        this.mvc.perform(get("/api/kaarten").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test(expected = NestedServletException.class)
    public void deleteKaart_nonExistingKaart() throws Exception {
        Kaart kaart = new Kaart("Een kaartje", "http://www.afbeeldingurl.be", true, null);
        String json = objectMapper.writeValueAsString(kaart);

        this.mvc.perform(post("/api/kaarten").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getUserToken()))
                .andExpect(status().isCreated());

        this.mvc.perform(delete("/api/kaarten/2"));
    }

    @Test
    public void commentToevoegenAanKaart() throws Exception {
        Kaart kaart = new Kaart("Een kaartje", "http://www.afbeeldingurl.be", true, null);
        String json = objectMapper.writeValueAsString(kaart);

        this.mvc.perform(post("/api/kaarten").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getUserToken()))
                .andExpect(status().isCreated());

        this.mvc.perform(get("/api/kaarten").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].tekst", is("Een kaartje")))
                .andExpect(jsonPath("$[0].imageUrl", is("http://www.afbeeldingurl.be")));

        String commentJson = objectMapper.writeValueAsString(new Commentaar("Een comment", null));

        this.mvc.perform(post("/api/kaarten/1/comments").contentType(MediaType.APPLICATION_JSON).content(commentJson).header("Authorization", getUserToken()))
                .andExpect(status().isCreated());

        this.mvc.perform(get("/api/kaarten/1/comments").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test(expected = NestedServletException.class)
    public void commentToevoegenAanKaart_nietToegelaten() throws Exception {
        String json = objectMapper.writeValueAsString(new Kaart("Een kaartje", "http://www.afbeeldingurl.be", false, null));

        this.mvc.perform(post("/api/kaarten").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getUserToken()))
                .andExpect(status().isCreated());

        this.mvc.perform(get("/api/kaarten").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].tekst", is("Een kaartje")))
                .andExpect(jsonPath("$[0].imageUrl", is("http://www.afbeeldingurl.be")));

        json = objectMapper.writeValueAsString(new Commentaar("Een comment", null));

        this.mvc.perform(post("/api/kaarten/1/comments").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getUserToken()));
    }

    @Test // TODO: change url
    public void koppelKaartAanSubthema() throws Exception {
        String json = objectMapper.writeValueAsString(new Kaart("Een kaartje", "http://www.afbeeldingurl.be", false, null));

        this.mvc.perform(post("/api/kaarten").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getUserToken()))
                .andExpect(status().isCreated());

        json = objectMapper.writeValueAsString(new Subthema("Een subthema", "beschrijving", null));

        this.mvc.perform(post("/api/kaarten/1/subthemas").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getUserToken()))
                .andExpect(status().isCreated());

        this.mvc.perform(get("/api/kaarten").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].subthema", is(1)));
    }

    private String getUserToken() throws Exception {
        String json = objectMapper.writeValueAsString(new Gebruiker("user", "user", new ArrayList<>(Collections.singletonList(new Rol("user", "user")))));
        MvcResult mvcResult = mvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(json)).andReturn();

        return "Bearer " + objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Token.class).getToken();
    }

    private String getAdminToken() throws Exception {
        String json = objectMapper.writeValueAsString(new Gebruiker("admin", "admin", new ArrayList<>(Arrays.asList(new Rol("admin", "admin"), new Rol("user", "user")))));
        MvcResult mvcResult = mvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(json)).andReturn();

        return "Bearer " + objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Token.class).getToken();
    }

    private String getTokenAsInexistent() throws Exception {
        String json = objectMapper.writeValueAsString(new Gebruiker("wrong", "wrong", new ArrayList<>(Collections.singletonList(new Rol("wrong", "wrong")))));
        MvcResult mvcResult = mvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(json)).andReturn();

        return "Bearer " + objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Token.class).getToken();
    }
}
