package be.kdg.teamh;

import be.kdg.teamh.entities.Cirkelsessie;
import be.kdg.teamh.entities.Hoofdthema;
import be.kdg.teamh.entities.Subthema;
import be.kdg.teamh.entities.SubthemaKaart;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SubthemaTest {
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private FilterChainProxy filterChainProxy;

    @Autowired
    private Gson gson;

    @Mock
    private Hoofdthema hoofdthema;

    @Mock
    private List<Cirkelsessie> cirkelsessies;

    @Mock
    private List<SubthemaKaart> subthemaKaarten;

    @Before
    public void setUp() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).addFilter(filterChainProxy).build();
    }

    @Test
    public void indexSubthema() throws Exception {
        this.mvc.perform(get("/subthemas").accept(MediaType.APPLICATION_JSON)
                .with(loginAsUser()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void createSubthema() throws Exception {
        String json = gson.toJson(new Subthema("Houffalize", "Route 6", hoofdthema, cirkelsessies, subthemaKaarten));

        this.mvc.perform(post("/subthemas").contentType(MediaType.APPLICATION_JSON).content(json)
                .with(loginAsAdmin()))
                .andExpect(status().isCreated());

        this.mvc.perform(get("/subthemas").contentType(MediaType.APPLICATION_JSON)
                .with(loginAsAdmin()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].naam", is("Houffalize")))
                .andExpect(jsonPath("$[0].beschrijving", is("Route 6")));
    }

    @Test(expected = NestedServletException.class)
    public void createSubthema_nullInput() throws Exception {
        String json = gson.toJson(new Subthema(null, null, hoofdthema, cirkelsessies, subthemaKaarten));

        this.mvc.perform(post("/subthemas").contentType(MediaType.APPLICATION_JSON).content(json)
                .with(loginAsAdmin()));
    }

    @Test
    public void showSubthema() throws Exception {
        String json = gson.toJson(new Subthema("Houffalize", "Route 6", hoofdthema, cirkelsessies, subthemaKaarten));

        this.mvc.perform(post("/subthemas").contentType(MediaType.APPLICATION_JSON).content(json)
                .with(loginAsAdmin()))
                .andExpect(status().isCreated());

        this.mvc.perform(get("/subthemas/1").contentType(MediaType.APPLICATION_JSON)
                .with(loginAsUser()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.naam", is("Houffalize")))
                .andExpect(jsonPath("$.beschrijving", is("Route 6")));
    }

    @Test(expected = NestedServletException.class)
    public void showSubthema_nonExistingHoofdthema() throws Exception {
        String json = gson.toJson(new Subthema("Houffalize", "Route 6", hoofdthema, cirkelsessies, subthemaKaarten));

        this.mvc.perform(post("/subthemas").contentType(MediaType.APPLICATION_JSON).content(json)
                .with(loginAsAdmin()))
                .andExpect(status().isCreated());

        this.mvc.perform(get("/subthemas/2").accept(MediaType.APPLICATION_JSON)
                .with(loginAsUser()));
    }

    @Test
    public void updateSubthema() throws Exception {
        String json = gson.toJson(new Subthema("Houffalize", "Route 6", hoofdthema, cirkelsessies, subthemaKaarten));

        this.mvc.perform(post("/subthemas").contentType(MediaType.APPLICATION_JSON).content(json)
                .with(loginAsAdmin()))
                .andExpect(status().isCreated());

        json = gson.toJson(new Subthema("Houffalize", "Route 3", hoofdthema, cirkelsessies, subthemaKaarten));

        this.mvc.perform(put("/subthemas/1").contentType(MediaType.APPLICATION_JSON).content(json)
                .with(loginAsAdmin()))
                .andExpect(status().isOk());

        this.mvc.perform(get("/subthemas/1").contentType(MediaType.APPLICATION_JSON)
                .with(loginAsAdmin()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.naam", is("Houffalize")))
                .andExpect(jsonPath("$.beschrijving", is("Route 3")));
    }

    @Test(expected = NestedServletException.class)
    public void updateHoofdthema_nullInput() throws Exception {
        String json = gson.toJson(new Subthema("Houffalize", "Route 6", hoofdthema, cirkelsessies, subthemaKaarten));

        this.mvc.perform(post("/subthemas").contentType(MediaType.APPLICATION_JSON).content(json)
                .with(loginAsAdmin()))
                .andExpect(status().isCreated());

        json = gson.toJson(new Subthema(null, null, hoofdthema, cirkelsessies, subthemaKaarten));

        this.mvc.perform(put("/subthemas/1").contentType(MediaType.APPLICATION_JSON).content(json)
                .with(loginAsAdmin()));
    }

    @Test(expected = NestedServletException.class)
    public void updateHoofdthema_nonExistingHoofdthema() throws Exception {
        String json = gson.toJson(new Subthema("Houffalize", "Route 6", hoofdthema, cirkelsessies, subthemaKaarten));

        this.mvc.perform(post("/subthemas").contentType(MediaType.APPLICATION_JSON).content(json)
                .with(loginAsAdmin()))
                .andExpect(status().isCreated());

        json = gson.toJson(new Subthema("Houffalize", "Route 3", hoofdthema, cirkelsessies, subthemaKaarten));

        this.mvc.perform(put("/subthemas/2").contentType(MediaType.APPLICATION_JSON).content(json)
                .with(loginAsAdmin()));
    }

    @Test
    public void deleteSubthema() throws Exception {
        String json = gson.toJson(new Subthema("Houffalize", "Route 6", hoofdthema, cirkelsessies, subthemaKaarten));

        this.mvc.perform(post("/subthemas").contentType(MediaType.APPLICATION_JSON).content(json)
                .with(loginAsAdmin()))
                .andExpect(status().isCreated());

        this.mvc.perform(delete("/subthemas/1")
                .with(loginAsAdmin()))
                .andExpect(status().isOk());

        this.mvc.perform(get("/subthemas").accept(MediaType.APPLICATION_JSON)
                .with(loginAsAdmin()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test(expected = NestedServletException.class)
    public void deleteHoofdthema_nonExistingHoofdthema() throws Exception {
        String json = gson.toJson(new Subthema("Houffalize", "Route 6", hoofdthema, cirkelsessies, subthemaKaarten));

        this.mvc.perform(post("/subthemas").contentType(MediaType.APPLICATION_JSON).content(json)
                .with(loginAsAdmin()))
                .andExpect(status().isCreated());

        this.mvc.perform(delete("/subthemas/2")
                .with(loginAsAdmin()));
    }


    private RequestPostProcessor loginAsUser() {
        return httpBasic("user", "user");
    }

    private RequestPostProcessor loginAsAdmin() {
        return httpBasic("admin", "admin");
    }

    private RequestPostProcessor loginWithWrongCredentials() {
        return httpBasic("wrong", "wrong");
    }
}
