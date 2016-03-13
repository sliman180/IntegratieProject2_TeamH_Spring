package be.kdg.teamh;

import be.kdg.teamh.dtos.Token;
import be.kdg.teamh.entities.Cirkelsessie;
import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.entities.Rol;
import be.kdg.teamh.entities.Subthema;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.DateTime;
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
import java.util.Date;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CirkelsessieTest {
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
       //objectMapper.registerModule(new JodaModule());
    }

    @Test
    public void indexCirkesessie() throws Exception {
        this.mvc.perform(get("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void createCirkelsessie() throws Exception {
        Cirkelsessie cirkelsessie = new Cirkelsessie("Session one", 5, 10, false, new DateTime(), null, null, null);
        String json = objectMapper.writeValueAsString(cirkelsessie);

        this.mvc.perform(post("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getUserToken()))
                .andExpect(status().isCreated());

        this.mvc.perform(get("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].naam", is("Session one")))
                .andExpect(jsonPath("$[0].maxAantalKaarten", is(10)));
    }

    @Test(expected = NestedServletException.class)
    public void createCirkelsessie_nullInput() throws Exception {
        Cirkelsessie cirkelsessie = new Cirkelsessie(null, 0, 0, false, null, null, null, null);
        String json = objectMapper.writeValueAsString(cirkelsessie);

        this.mvc.perform(post("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getUserToken()));
    }

    @Test
    public void showCirkelsessie() throws Exception {
        Cirkelsessie cirkelsessie = new Cirkelsessie("Session one", 5, 10, false, new DateTime(), null, null, null);
        String json = objectMapper.writeValueAsString(cirkelsessie);

        this.mvc.perform(post("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getUserToken()))
                .andExpect(status().isCreated());

        this.mvc.perform(get("/api/cirkelsessies/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.naam", is("Session one")))
                .andExpect(jsonPath("$.aantalCirkels", is(5)))
                .andExpect(jsonPath("$.maxAantalKaarten", is(10)));
    }

    @Test(expected = NestedServletException.class)
    public void showCirkelsessie_nonExistingCirkelsessie() throws Exception {
        Cirkelsessie cirkelsessie = new Cirkelsessie("Session one", 5, 10, false, new DateTime(), null, null, null);
        String json = objectMapper.writeValueAsString(cirkelsessie);

        this.mvc.perform(post("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getUserToken()))
                .andExpect(status().isCreated());

        this.mvc.perform(get("/api/cirkelsessies/2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.naam", is("Session one")))
                .andExpect(jsonPath("$[0].aantalCirkels", is(5)))
                .andExpect(jsonPath("$.maxAantalKaarten", is(10)));
    }

    @Test
    public void updateCirkelsessie() throws Exception {
        Cirkelsessie cirkelsessie = new Cirkelsessie("Session one", 5, 10, false, new DateTime(), null, null, null);
        String json = objectMapper.writeValueAsString(cirkelsessie);

        this.mvc.perform(post("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getUserToken()))
                .andExpect(status().isCreated());

        json = objectMapper.writeValueAsString(new Cirkelsessie("Session two", 8, 15, false, new DateTime(), null, null, null));

        this.mvc.perform(put("/api/cirkelsessies/1").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());

        this.mvc.perform(get("/api/cirkelsessies/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.naam", is("Session two")))
                .andExpect(jsonPath("$.aantalCirkels", is(8)))
                .andExpect(jsonPath("$.maxAantalKaarten", is(15)));
    }

    @Test(expected = NestedServletException.class)
    public void updateCirkelsessie_nullInput() throws Exception {
        Cirkelsessie cirkelsessie = new Cirkelsessie("Session one", 5, 10, false, new DateTime(), null, null, null);
        String json = objectMapper.writeValueAsString(cirkelsessie);

        this.mvc.perform(post("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getUserToken()))
                .andExpect(status().isCreated());

        json = objectMapper.writeValueAsString(new Cirkelsessie(null, 0, 0, false, new DateTime(), null, null, null));

        this.mvc.perform(put("/api/cirkelsessies/1").contentType(MediaType.APPLICATION_JSON).content(json));
    }

    @Test(expected = NestedServletException.class)
    public void updateCirkelsessie_nonExistingCirkelsessie() throws Exception {
        Cirkelsessie cirkelsessie = new Cirkelsessie("Session one", 5, 10, false, new DateTime(), null, null, null);
        String json = objectMapper.writeValueAsString(cirkelsessie);

        this.mvc.perform(post("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getUserToken()))
                .andExpect(status().isCreated());

        json = objectMapper.writeValueAsString(new Cirkelsessie("Session one", 5, 10, false, new DateTime(), null, null, null));

        this.mvc.perform(put("/api/cirkelsessies/2").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }

    @Test(expected = NestedServletException.class)
    public void deleteCirkelsessie() throws Exception {
        Cirkelsessie cirkelsessie = new Cirkelsessie("Session one", 5, 10, false, new DateTime(), null, null, null);
        String json = objectMapper.writeValueAsString(cirkelsessie);

        this.mvc.perform(post("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getUserToken()))
                .andExpect(status().isCreated());

        this.mvc.perform(delete("/api/cirkelsessies/1"))
                .andExpect(status().isOk());

        this.mvc.perform(get("/api/cirkelsessies/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test(expected = NestedServletException.class)
    public void deleteCirkelsessie_nonExistingCirkelsessie() throws Exception {
        this.mvc.perform(delete("/api/cirkelsessies/1"));
    }

    @Test
    public void checkCirkelsessieLinkedToSubthema() throws Exception {
        Subthema subthema = new Subthema("Houffalize", "Route 6", null);
        Cirkelsessie cirkelsessie = new Cirkelsessie("Session one", 5, 10, false, new DateTime(), subthema, null, null);
        String json = objectMapper.writeValueAsString(cirkelsessie);

        this.mvc.perform(post("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getUserToken()))
                .andExpect(status().isCreated());

        this.mvc.perform(get("/api/cirkelsessies/1/subthema").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.naam", is("Houffalize")))
                .andExpect(jsonPath("$.beschrijving", is("Route 6")));
    }

    @Test
    public void cloneCirkelSessie() throws Exception {
        Cirkelsessie cirkelsessie = new Cirkelsessie("Session one", 5, 10, false, new DateTime(), null, null, null);
        String json = objectMapper.writeValueAsString(cirkelsessie);

        this.mvc.perform(post("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getUserToken()))
                .andExpect(status().isCreated());

        this.mvc.perform(post("/api/cirkelsessies/1/clone").contentType(MediaType.APPLICATION_JSON).header("Authorization", getUserToken()))
                .andExpect(status().isCreated());

        this.mvc.perform(get("/api/cirkelsessies/2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.naam", is("Session one")))
                .andExpect(jsonPath("$.maxAantalKaarten", is(5)))
                .andExpect(jsonPath("$.aantalCirkels", is(10)))
                .andExpect(jsonPath("$.deelnames", hasSize(0)));
    }

    @Test
    public void showGeplandeCirkelSessies() throws Exception {
        Cirkelsessie cirkelsessie = new Cirkelsessie("Session one", 5, 10, true, new DateTime().plusMonths(2), null, null, null);
        String json = objectMapper.writeValueAsString(cirkelsessie);

        this.mvc.perform(post("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getUserToken())).andExpect(status().isCreated());

        cirkelsessie = new Cirkelsessie("Session two", 5, 10, false, new DateTime(), null, null, null);
        json = objectMapper.writeValueAsString(cirkelsessie);

        this.mvc.perform(post("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getUserToken())).andExpect(status().isCreated());

        this.mvc.perform(get("/api/cirkelsessies/gepland").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].naam", is("Session one")));

    }

    @Test
    public void showActieveCirkelSessies() throws Exception {
        Cirkelsessie cirkelsessie = new Cirkelsessie("Session one", 5, 10, true, new DateTime().plusMonths(2), null, null, null);
        String json = objectMapper.writeValueAsString(cirkelsessie);

        this.mvc.perform(post("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getUserToken())).andExpect(status().isCreated());

        cirkelsessie = new Cirkelsessie("Session two", 5, 10, false, new DateTime().minusMonths(2), null, null, null);
        json = objectMapper.writeValueAsString(cirkelsessie);

        this.mvc.perform(post("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getUserToken())).andExpect(status().isCreated());

        cirkelsessie = new Cirkelsessie("Session three", 5, 10, false, new DateTime().minusMonths(2), null, null, null);
        json = objectMapper.writeValueAsString(cirkelsessie);

        this.mvc.perform(post("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getUserToken())).andExpect(status().isCreated());

        this.mvc.perform(get("/api/cirkelsessies/actief").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].naam", is("Session two")))
                .andExpect(jsonPath("$[1].naam", is("Session three")));
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
