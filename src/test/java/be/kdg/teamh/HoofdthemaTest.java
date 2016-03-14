package be.kdg.teamh;

import be.kdg.teamh.dtos.Token;
import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.entities.Hoofdthema;
import be.kdg.teamh.entities.Rol;
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
public class HoofdthemaTest {
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
    public void indexHoofdthema() throws Exception {
        this.mvc.perform(get("/api/hoofdthemas").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void createHoofdthema() throws Exception {
        String json = objectMapper.writeValueAsString(new Hoofdthema("Voetbal", "Nieuw voetbalveld", null, null));

        this.mvc.perform(post("/api/hoofdthemas").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getUserToken()))
                .andExpect(status().isCreated());

        this.mvc.perform(get("/api/hoofdthemas").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].naam", is("Voetbal")))
                .andExpect(jsonPath("$[0].beschrijving", is("Nieuw voetbalveld")));
    }

    @Test(expected = NestedServletException.class)
    public void createHoofdthema_nullInput() throws Exception {
        String json = objectMapper.writeValueAsString(new Hoofdthema(null, null, null, null));

        this.mvc.perform(post("/api/hoofdthemas").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getUserToken()));
    }

    @Test
    public void showHoofdthema() throws Exception {
        String json = objectMapper.writeValueAsString(new Hoofdthema("Voetbal", "Nieuw voetbalveld", null, null));

        this.mvc.perform(post("/api/hoofdthemas").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getUserToken()))
                .andExpect(status().isCreated());

        this.mvc.perform(get("/api/hoofdthemas/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.naam", is("Voetbal")))
                .andExpect(jsonPath("$.beschrijving", is("Nieuw voetbalveld")));
    }

    @Test(expected = NestedServletException.class)
    public void showHoofdthema_nonExistingHoofdthema() throws Exception {
        String json = objectMapper.writeValueAsString(new Hoofdthema("Voetbal", "Nieuw voetbalveld", null, null));

        this.mvc.perform(post("/api/hoofdthemas").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getUserToken()))
                .andExpect(status().isCreated());

        this.mvc.perform(get("/api/hoofdthemas/2").accept(MediaType.APPLICATION_JSON));
    }

    @Test
    public void updateHoofdthema() throws Exception {
        String json = objectMapper.writeValueAsString(new Hoofdthema("Voetbal", "Nieuw voetbalveld", null, null));

        this.mvc.perform(post("/api/hoofdthemas").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getUserToken()))
                .andExpect(status().isCreated());

        json = objectMapper.writeValueAsString(new Hoofdthema("Voetbal", "Vernieuwd voetbalveld", null, null));

        this.mvc.perform(put("/api/hoofdthemas/1").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());

        this.mvc.perform(get("/api/hoofdthemas/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.naam", is("Voetbal")))
                .andExpect(jsonPath("$.beschrijving", is("Vernieuwd voetbalveld")));
    }

    @Test(expected = NestedServletException.class)
    public void updateHoofdthema_nullInput() throws Exception {
        String json = objectMapper.writeValueAsString(new Hoofdthema("Voetbal", "Nieuw voetbalveld", null, null));

        this.mvc.perform(post("/api/hoofdthemas").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getUserToken()))
                .andExpect(status().isCreated());

        json = objectMapper.writeValueAsString(new Hoofdthema(null, null, null, null));

        this.mvc.perform(put("/api/hoofdthemas/1").contentType(MediaType.APPLICATION_JSON).content(json));
    }

    @Test(expected = NestedServletException.class)
    public void updateHoofdthema_nonExistingHoofdthema() throws Exception {
        String json = objectMapper.writeValueAsString(new Hoofdthema("Voetbal", "Nieuw voetbalveld", null, null));

        this.mvc.perform(post("/api/hoofdthemas").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getUserToken()))
                .andExpect(status().isCreated());

        json = objectMapper.writeValueAsString(new Hoofdthema("Voetbal", "Vernieuwd voetbalveld", null, null));

        this.mvc.perform(put("/api/hoofdthemas/2").contentType(MediaType.APPLICATION_JSON).content(json));
    }

    @Test
    public void deleteHoofdthema() throws Exception {
        String json = objectMapper.writeValueAsString(new Hoofdthema("Voetbal", "Nieuw voetbalveld", null, null));

        this.mvc.perform(post("/api/hoofdthemas").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getUserToken()))
                .andExpect(status().isCreated());

        this.mvc.perform(delete("/api/hoofdthemas/1"))
                .andExpect(status().isOk());

        this.mvc.perform(get("/api/hoofdthemas").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test(expected = NestedServletException.class)
    public void deleteHoofdthema_nonExistingHoofdthema() throws Exception {
        String json = objectMapper.writeValueAsString(new Hoofdthema("Voetbal", "Nieuw voetbalveld", null, null));

        this.mvc.perform(post("/api/hoofdthemas").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getUserToken()))
                .andExpect(status().isCreated());

        this.mvc.perform(delete("/api/hoofdthemas/2"));
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
