package be.kdg.teamh;

import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.entities.Hoofdthema;
import be.kdg.teamh.entities.Organisatie;
import be.kdg.teamh.entities.Tag;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import javax.servlet.Filter;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class HoofdthemaTest
{
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private FilterChainProxy filterChainProxy;

    @Autowired
    private Gson gson;

    @Mock
    private Organisatie organisatie;

    @Mock
    private Gebruiker gebruiker;


    public static RequestPostProcessor login() {
        return httpBasic("user", "user");
    }

    @Before
    public void setUp() throws Exception {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).addFilter(filterChainProxy).build();


    }

    @Test
    public void indexHoofdthema() throws Exception
    {
        //.with(user("user").password("password").roles("USER"))
        this.mvc.perform(get("/hoofdthemas").accept(MediaType.APPLICATION_JSON).with(login()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void createHoofdthema() throws Exception
    {
        String json = gson.toJson(new Hoofdthema("Voetbal", "Nieuw voetbalveld", organisatie, gebruiker));

        this.mvc.perform(post("/hoofdthemas").contentType(MediaType.APPLICATION_JSON).content(json).with(login()))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/hoofdthemas").accept(MediaType.APPLICATION_JSON).with(login()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].id", is(1)))
            .andExpect(jsonPath("$[0].naam", is("Voetbal")))
            .andExpect(jsonPath("$[0].beschrijving", is("Nieuw voetbalveld")));
    }

    @Test(expected = NestedServletException.class)
    public void createHoofdthema_nullInput() throws Exception
    {
        String json = gson.toJson(new Hoofdthema(null, null, organisatie, gebruiker));

        this.mvc.perform(post("/hoofdthemas").contentType(MediaType.APPLICATION_JSON).content(json).with(login()));
    }

    @Test
    public void showHoofdthema() throws Exception
    {
        String json = gson.toJson(new Hoofdthema("Voetbal", "Nieuw voetbalveld", organisatie, gebruiker));

        this.mvc.perform(post("/hoofdthemas").contentType(MediaType.APPLICATION_JSON).content(json).with(login()))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/hoofdthemas/1").accept(MediaType.APPLICATION_JSON).with(login()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.naam", is("Voetbal")))
            .andExpect(jsonPath("$.beschrijving", is("Nieuw voetbalveld")));
    }

    @Test(expected = NestedServletException.class)
    public void showHoofdthema_nonExistingHoofdthema() throws Exception
    {
        String json = gson.toJson(new Hoofdthema("Voetbal", "Nieuw voetbalveld", organisatie, gebruiker));

        this.mvc.perform(post("/hoofdthemas").contentType(MediaType.APPLICATION_JSON).content(json).with(login()))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/hoofdthemas/2").accept(MediaType.APPLICATION_JSON).with(login()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.naam", is("Voetbal")))
            .andExpect(jsonPath("$.beschrijving", is("Nieuw voetbalveld")));
    }

    @Test
    public void updateHoofdthema() throws Exception
    {
        String json = gson.toJson(new Hoofdthema("Voetbal", "Nieuw voetbalveld", organisatie, gebruiker));

        this.mvc.perform(post("/hoofdthemas").contentType(MediaType.APPLICATION_JSON).content(json).with(login()))
            .andExpect(status().isCreated());

        json = gson.toJson(new Hoofdthema("Voetbal", "Vernieuwd voetbalveld", organisatie, gebruiker));

        this.mvc.perform(put("/hoofdthemas/1").contentType(MediaType.APPLICATION_JSON).content(json).with(login()))
            .andExpect(status().isOk());

        this.mvc.perform(get("/hoofdthemas/1").accept(MediaType.APPLICATION_JSON).with(login()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.naam", is("Voetbal")))
            .andExpect(jsonPath("$.beschrijving", is("Vernieuwd voetbalveld")));
    }

    @Test(expected = NestedServletException.class)
    public void updateHoofdthema_nullInput() throws Exception
    {
        String json = gson.toJson(new Hoofdthema("Voetbal", "Nieuw voetbalveld", organisatie, gebruiker));

        this.mvc.perform(post("/hoofdthemas").contentType(MediaType.APPLICATION_JSON).content(json).with(login()))
            .andExpect(status().isCreated());

        json = gson.toJson(new Hoofdthema(null, null, organisatie, gebruiker));

        this.mvc.perform(put("/hoofdthemas/1").contentType(MediaType.APPLICATION_JSON).content(json).with(login()));
    }

    @Test(expected = NestedServletException.class)
    public void updateHoofdthema_nonExistingHoofdthema() throws Exception
    {
        String json = gson.toJson(new Hoofdthema("Voetbal", "Nieuw voetbalveld", organisatie, gebruiker));

        this.mvc.perform(post("/hoofdthemas").contentType(MediaType.APPLICATION_JSON).content(json).with(login()))
            .andExpect(status().isCreated());

        json = gson.toJson(new Hoofdthema("Voetbal", "Vernieuwd voetbalveld", organisatie, gebruiker));

        this.mvc.perform(put("/hoofdthemas/2").contentType(MediaType.APPLICATION_JSON).content(json).with(login()));
    }

    @Test
    public void deleteHoofdthema() throws Exception
    {
        String json = gson.toJson(new Hoofdthema("Voetbal", "Nieuw voetbalveld", organisatie, gebruiker));

        this.mvc.perform(post("/hoofdthemas").contentType(MediaType.APPLICATION_JSON).content(json).with(login()))
            .andExpect(status().isCreated());

        this.mvc.perform(delete("/hoofdthemas/1").with(login()))
            .andExpect(status().isOk());

        this.mvc.perform(get("/hoofdthemas").accept(MediaType.APPLICATION_JSON).with(login()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test(expected = NestedServletException.class)
    public void deleteHoofdthema_nonExistingHoofdthema() throws Exception
    {
        String json = gson.toJson(new Hoofdthema("Voetbal", "Nieuw voetbalveld", organisatie, gebruiker));

        this.mvc.perform(post("/hoofdthemas").contentType(MediaType.APPLICATION_JSON).content(json).with(login()))
            .andExpect(status().isCreated());

        this.mvc.perform(delete("/hoofdthemas/2").with(login()));
    }
}
