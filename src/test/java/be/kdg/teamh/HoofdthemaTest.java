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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
    private Gson gson;

    @Mock
    private Organisatie organisatie;

    @Mock
    private Gebruiker gebruiker;

    @Mock
    private Tag tag;

    @Before
    public void setUp()
    {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        organisatie = new Organisatie("naam","beschrijving");
    }

    @Test
    public void indexHoofdthema() throws Exception
    {
        this.mvc.perform(get("/hoofdthemas").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void createHoofdthema() throws Exception
    {
        String json = gson.toJson(new Hoofdthema("Voetbal", "Nieuw voetbalveld", organisatie, gebruiker));

        MvcResult result = this.mvc.perform(post("/hoofdthemas").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated()).andDo(print()).andReturn();

        String returned = result.getResponse().getContentAsString();

        Hoofdthema tempHoofdthema = gson.fromJson(returned,Hoofdthema.class);

        this.mvc.perform(get("/hoofdthemas").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].id", is(tempHoofdthema.getId())))
            .andExpect(jsonPath("$[0].naam", is(tempHoofdthema.getNaam())))
            .andExpect(jsonPath("$[0].beschrijving", is(tempHoofdthema.getBeschrijving()))).andDo(print());
    }

    @Test
    public void showHoofdthema() throws Exception
    {
        String json = gson.toJson(new Hoofdthema("Voetbal", "Nieuw voetbalveld", organisatie, gebruiker));

        MvcResult result = this.mvc.perform(post("/hoofdthemas").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated()).andReturn();

        String returned = result.getResponse().getContentAsString();

        Hoofdthema tempHoofdthema = gson.fromJson(returned,Hoofdthema.class);

        this.mvc.perform(get("/hoofdthemas/1").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(tempHoofdthema.getId())))
            .andExpect(jsonPath("$.naam", is(tempHoofdthema.getNaam())))
            .andExpect(jsonPath("$.beschrijving", is(tempHoofdthema.getBeschrijving())));
    }

    @Test
    public void updateHoofdthema() throws Exception
    {
        String json = gson.toJson(new Hoofdthema("Voetbal", "Nieuw voetbalveld", organisatie, gebruiker));

        this.mvc.perform(post("/hoofdthemas").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated());

        json = gson.toJson(new Hoofdthema("Voetbal", "Vernieuwd voetbalveld", organisatie, gebruiker));

        MvcResult result = this.mvc.perform(put("/hoofdthemas/1").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isOk()).andReturn();

        String returned = result.getResponse().getContentAsString();

        Hoofdthema tempHoofdthema = gson.fromJson(returned,Hoofdthema.class);

        this.mvc.perform(get("/hoofdthemas").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].id", is(tempHoofdthema.getId())))
            .andExpect(jsonPath("$[0].naam", is(tempHoofdthema.getNaam())))
            .andExpect(jsonPath("$[0].beschrijving", is(tempHoofdthema.getBeschrijving())));
    }

    @Test
    public void deleteHoofdthema() throws Exception
    {
        String json = gson.toJson(new Hoofdthema("Voetbal", "Nieuw voetbalveld", organisatie, gebruiker));

        this.mvc.perform(post("/hoofdthemas").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated());

        this.mvc.perform(delete("/hoofdthemas/1"))
            .andExpect(status().isOk());

        this.mvc.perform(get("/hoofdthemas").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }
}
