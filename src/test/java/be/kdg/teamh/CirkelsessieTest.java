package be.kdg.teamh;

import be.kdg.teamh.entities.Cirkelsessie;
import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.entities.Subthema;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.web.util.NestedServletException;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CirkelsessieTest
{
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Gson gson;

    @Mock
    Gebruiker gebruiker;

    @Mock
    Subthema subthema;

    @Before
    public void setUp()
    {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    public void indexCirkesessie() throws Exception
    {
        this.mvc.perform(get("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void createCirkelsessie() throws Exception
    {
        String json = objectMapper.writeValueAsString(new Cirkelsessie("Session one", 5, 10, false, LocalDateTime.now(), subthema, gebruiker));

        this.mvc.perform(post("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].id", is(1)))
            .andExpect(jsonPath("$[0].naam", is("Session one")))
            .andExpect(jsonPath("$[0].maxAantalKaarten", is(10)));
    }

    @Test(expected = NestedServletException.class)
    public void createCirkelsessie_nullInput() throws Exception
    {
        String json = objectMapper.writeValueAsString(new Cirkelsessie(null, 0, 0, false, LocalDateTime.now(), subthema, gebruiker));

        this.mvc.perform(post("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json));
    }

    @Test
    public void showCirkelsessie() throws Exception
    {
        String json = objectMapper.writeValueAsString(new Cirkelsessie("Session one", 5, 10, false, LocalDateTime.now(), subthema, gebruiker));

        this.mvc.perform(post("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/api/cirkelsessies/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.naam", is("Session one")))
            .andExpect(jsonPath("$.aantalCirkels", is(5)))
            .andExpect(jsonPath("$.maxAantalKaarten", is(10)));
    }

    @Test(expected = NestedServletException.class)
    public void showCirkelsessie_nonExistingCirkelsessie() throws Exception
    {
        String json = objectMapper.writeValueAsString(new Cirkelsessie("Session one", 5, 10, false, LocalDateTime.now(), subthema, gebruiker));

        this.mvc.perform(post("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/api/cirkelsessies/2").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(2)))
            .andExpect(jsonPath("$.naam", is("Session one")))
            .andExpect(jsonPath("$[0].aantalCirkels", is(5)))
            .andExpect(jsonPath("$.maxAantalKaarten", is(10)));
    }

    @Test
    public void updateCirkelsessie() throws Exception
    {
        String json = objectMapper.writeValueAsString(new Cirkelsessie("Session one", 5, 10, false, LocalDateTime.now(), subthema, gebruiker));

        this.mvc.perform(post("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated());

        json = objectMapper.writeValueAsString(new Cirkelsessie("Session two", 8, 15, false, LocalDateTime.now(), subthema, gebruiker));

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
    public void updateCirkelsessie_nullInput() throws Exception
    {
        String json = objectMapper.writeValueAsString(new Cirkelsessie("Session one", 5, 10, false, LocalDateTime.now(), subthema, gebruiker));

        this.mvc.perform(post("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated());

        json = objectMapper.writeValueAsString(new Cirkelsessie(null, 0, 0, false, null, subthema, gebruiker));

        this.mvc.perform(put("/api/cirkelsessies/1").contentType(MediaType.APPLICATION_JSON).content(json));
    }

    @Test(expected = NestedServletException.class)
    public void updateCirkelsessie_nonExistingCirkelsessie() throws Exception
    {
        String json = objectMapper.writeValueAsString(new Cirkelsessie("Session one", 5, 10, false, LocalDateTime.now(), subthema, gebruiker));

        this.mvc.perform(post("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated());

        json = objectMapper.writeValueAsString(new Cirkelsessie("Session one", 5, 10, false, LocalDateTime.now(), subthema, gebruiker));

        this.mvc.perform(put("/api/cirkelsessies/2").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isOk());
    }

    @Test(expected = NestedServletException.class)
    public void deleteCirkelsessie() throws Exception
    {
        String json = objectMapper.writeValueAsString(new Cirkelsessie("Session one", 5, 10, false, LocalDateTime.now(), subthema, gebruiker));

        this.mvc.perform(post("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated());

        this.mvc.perform(delete("/api/cirkelsessies/1"))
            .andExpect(status().isOk());

        this.mvc.perform(get("/api/cirkelsessies/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test(expected = NestedServletException.class)
    public void deleteCirkelsessie_nonExistingCirkelsessie() throws Exception
    {
        String json = objectMapper.writeValueAsString(new Cirkelsessie("Session one", 5, 10, false, LocalDateTime.now(), subthema, gebruiker));

        this.mvc.perform(post("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated());

        this.mvc.perform(delete("/api/cirkelsessies/2"));
    }

    @Test
    public void checkCirkelsessieLinkedToSubthema() throws Exception
    {
        subthema = new Subthema("Houffalize", "Route 6", null);
        String json = objectMapper.writeValueAsString(new Cirkelsessie("Session one", 5, 10, false, LocalDateTime.now(), subthema, gebruiker));

        this.mvc.perform(post("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/api/cirkelsessies/1/subthema").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.naam", is("Houffalize")))
            .andExpect(jsonPath("$.beschrijving", is("Route 6")));

        this.mvc.perform(get("/api/cirkelsessies/1/subthema").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.naam", is("Houffalize")))
            .andExpect(jsonPath("$.beschrijving", is("Route 6")));
    }

    @Test
    public void cloneCirkelSessie() throws Exception
    {
        Cirkelsessie cirkelsessie = new Cirkelsessie("Session one", 5, 10, false, LocalDateTime.now(), subthema, gebruiker);
        String json = objectMapper.writeValueAsString(cirkelsessie);

        this.mvc.perform(post("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated());

        this.mvc.perform(post("/api/cirkelsessies/1/clone").contentType(MediaType.APPLICATION_JSON))
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
    public void showGeplandeCirkelSessies() throws Exception
    {

        String json = objectMapper.writeValueAsString(new Cirkelsessie("Session one", 5, 10, true, LocalDateTime.now().plusMonths(3), subthema, gebruiker));
        this.mvc.perform(post("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isCreated());

        json = objectMapper.writeValueAsString(new Cirkelsessie("Session two", 5, 10, false, LocalDateTime.now(), subthema, gebruiker));
        this.mvc.perform(post("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isCreated());

        this.mvc.perform(get("/api/cirkelsessies/gepland").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].naam", is("Session one")));

    }

    @Test
    public void showActieveCirkelSessies() throws Exception
    {
        String json = objectMapper.writeValueAsString(new Cirkelsessie("Session one", 5, 10, true, LocalDateTime.now().plusMonths(3), subthema, gebruiker));
        this.mvc.perform(post("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isCreated());

        json = objectMapper.writeValueAsString(new Cirkelsessie("Session two", 5, 10, false, LocalDateTime.now().minusDays(1), subthema, gebruiker));
        this.mvc.perform(post("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isCreated());

        json = objectMapper.writeValueAsString(new Cirkelsessie("Session three", 5, 10, false, LocalDateTime.now().minusDays(1), subthema, gebruiker));
        this.mvc.perform(post("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isCreated());

        this.mvc.perform(get("/api/cirkelsessies/actief").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].naam", is("Session two")))
            .andExpect(jsonPath("$[1].naam", is("Session three")));
    }
}
