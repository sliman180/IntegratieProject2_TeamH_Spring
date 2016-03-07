package be.kdg.teamh;

import be.kdg.teamh.entities.*;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class SpelkaartTest
{
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception
    {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    public void indexSpelkaart() throws Exception
    {
        this.mvc.perform(get("/api/spelkaarten").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void createSpelkaart() throws Exception
    {
        Cirkelsessie cirkelsessie = new Cirkelsessie("Een cirkelsessie", 10, 10, false, new Date(), null, null);
        Kaart kaart = new Kaart("Een kaart", "http://www.afbeeldingurl.be", true, null);
        Spelkaart spelkaart = new Spelkaart(kaart, cirkelsessie);
        String json = objectMapper.writeValueAsString(spelkaart);

        this.mvc.perform(post("/api/spelkaarten").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/api/spelkaarten").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].positie", is(0)));
    }

    @Test
    public void showSpelkaart() throws Exception
    {
        Cirkelsessie cirkelsessie = new Cirkelsessie("Een cirkelsessie", 10, 10, false, new Date(), null, null);
        Kaart kaart = new Kaart("Een kaart", "http://www.afbeeldingurl.be", true, null);
        Spelkaart spelkaart = new Spelkaart(kaart, cirkelsessie);
        String json = objectMapper.writeValueAsString(spelkaart);

        this.mvc.perform(post("/api/spelkaarten").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/api/spelkaarten/1").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.positie", is(0)));
    }

    @Test(expected = NestedServletException.class)
    public void showSpelkaart_nonExistingKaart() throws Exception
    {
        Cirkelsessie cirkelsessie = new Cirkelsessie("Een cirkelsessie", 10, 10, false, new Date(), null, null);
        Kaart kaart = new Kaart("Een kaart", "http://www.afbeeldingurl.be", true, null);
        Spelkaart spelkaart = new Spelkaart(kaart, cirkelsessie);
        String json = objectMapper.writeValueAsString(spelkaart);

        this.mvc.perform(post("/api/spelkaarten").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/api/spelkaarten/2").accept(MediaType.APPLICATION_JSON));
    }

    @Test
    public void updateSpelkaart() throws Exception
    {
        Cirkelsessie cirkelsessie = new Cirkelsessie("Een cirkelsessie", 10, 10, false, new Date(), null, null);
        Kaart kaart = new Kaart("Een kaart", "http://www.afbeeldingurl.be", true, null);
        Spelkaart spelkaart = new Spelkaart(kaart, cirkelsessie);
        String json = objectMapper.writeValueAsString(spelkaart);

        this.mvc.perform(post("/api/spelkaarten").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated());

        spelkaart.setPositie(5);
        json = objectMapper.writeValueAsString(spelkaart);

        this.mvc.perform(put("/api/spelkaarten/1").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isOk());

        this.mvc.perform(get("/api/spelkaarten/1").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.positie", is(5)));
    }

    @Test(expected = NestedServletException.class)
    public void verschuifKaartMetEénStap_maxLimitReached() throws Exception
    {
        Cirkelsessie cirkelsessie = new Cirkelsessie("Een cirkelsessie", 10, 10, false, new Date(), null, null);
        Kaart kaart = new Kaart("Een kaart", "http://www.afbeeldingurl.be", true, null);
        Spelkaart spelkaart = new Spelkaart(kaart, cirkelsessie);
        spelkaart.setPositie(cirkelsessie.getAantalCirkels());
        String json = objectMapper.writeValueAsString(spelkaart);

        this.mvc.perform(post("/api/spelkaarten").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated());

        this.mvc.perform(post("/api/spelkaarten/1/verschuif").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isConflict());
    }

    @Test(expected = NestedServletException.class)
    public void updateSpelkaart_nonExistingSpelkaart() throws Exception
    {
        Cirkelsessie cirkelsessie = new Cirkelsessie("Een cirkelsessie", 10, 10, false, new Date(), null, null);
        Kaart kaart = new Kaart("Een kaart", "http://www.afbeeldingurl.be", true, null);
        Spelkaart spelkaart = new Spelkaart(kaart, cirkelsessie);
        String json = objectMapper.writeValueAsString(spelkaart);

        this.mvc.perform(post("/api/spelkaarten").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated());

        spelkaart.setPositie(2);
        json = objectMapper.writeValueAsString(spelkaart);

        this.mvc.perform(put("/api/spelkaarten/2").contentType(MediaType.APPLICATION_JSON).content(json));
    }

    @Test
    public void deleteSpelkaart() throws Exception
    {
        Cirkelsessie cirkelsessie = new Cirkelsessie("Een cirkelsessie", 10, 10, false, new Date(), null, null);
        Kaart kaart = new Kaart("Een kaart", "http://www.afbeeldingurl.be", true, null);
        Spelkaart spelkaart = new Spelkaart(kaart, cirkelsessie);
        String json = objectMapper.writeValueAsString(spelkaart);

        this.mvc.perform(post("/api/spelkaarten").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated());

        this.mvc.perform(delete("/api/spelkaarten/1"))
            .andExpect(status().isOk());

        this.mvc.perform(get("/api/spelkaarten").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test(expected = NestedServletException.class)
    public void deleteSpelkaart_nonExistingSpelkaart() throws Exception
    {
        Cirkelsessie cirkelsessie = new Cirkelsessie("Een cirkelsessie", 10, 10, false, new Date(), null, null);
        Kaart kaart = new Kaart("Een kaart", "http://www.afbeeldingurl.be", true, null);
        Spelkaart spelkaart = new Spelkaart(kaart, cirkelsessie);
        String json = objectMapper.writeValueAsString(spelkaart);

        this.mvc.perform(post("/api/spelkaarten").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated());

        this.mvc.perform(delete("/api/spelkaarten/2"));
    }
}
