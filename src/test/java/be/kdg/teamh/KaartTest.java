package be.kdg.teamh;

import be.kdg.teamh.entities.*;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class KaartTest
{
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private Gson gson;

    @Mock
    private Gebruiker gebruiker;

    @Mock
    private Hoofdthema hoofdthema;

    @Before
    public void setUp() throws Exception
    {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    public void indexKaarten() throws Exception
    {
        this.mvc.perform(get("/api/kaarten").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void createKaart() throws Exception
    {
        String json = gson.toJson(new Kaart("Een kaartje", "http://www.afbeeldingurl.be", true, gebruiker));

        this.mvc.perform(post("/api/kaarten").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/api/kaarten").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].id", is(1)))
            .andExpect(jsonPath("$[0].tekst", is("Een kaartje")))
            .andExpect(jsonPath("$[0].imageUrl", is("http://www.afbeeldingurl.be")));
    }

    @Test(expected = NestedServletException.class)
    public void createKaart_nullInput() throws Exception
    {
        String json = gson.toJson(new Kaart(null, null, true, gebruiker));

        this.mvc.perform(post("/api/kaarten").contentType(MediaType.APPLICATION_JSON).content(json));
    }

    @Test
    public void showKaart() throws Exception
    {
        String json = gson.toJson(new Kaart("Een kaartje", "http://www.afbeeldingurl.be", true, gebruiker));

        this.mvc.perform(post("/api/kaarten").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/api/kaarten/1").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.imageUrl", is("http://www.afbeeldingurl.be")))
            .andExpect(jsonPath("$.tekst", is("Een kaartje")));
    }

    @Test(expected = NestedServletException.class)
    public void showKaart_nonExistingKaart() throws Exception
    {
        String json = gson.toJson(new Kaart("Een kaartje", "http://www.afbeeldingurl.be", true, gebruiker));

        this.mvc.perform(post("/api/kaarten").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/api/kaarten/2").accept(MediaType.APPLICATION_JSON));
    }

    @Test
    public void updateKaart() throws Exception
    {
        String json = gson.toJson(new Kaart("Een kaartje", "http://www.afbeeldingurl.be", true, gebruiker));

        this.mvc.perform(post("/api/kaarten").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated());

        json = gson.toJson(new Kaart("Een gewijzigde kaartje", "http://www.gewijzigdeafbeeldingurl.be", true, gebruiker));

        this.mvc.perform(put("/api/kaarten/1").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isOk());

        this.mvc.perform(get("/api/kaarten/1").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.tekst", is("Een gewijzigde kaartje")))
            .andExpect(jsonPath("$.imageUrl", is("http://www.gewijzigdeafbeeldingurl.be")));
    }

    @Test(expected = NestedServletException.class)
    public void updateKaart_nullInput() throws Exception
    {
        String json = gson.toJson(new Kaart("Een kaartje", "http://www.afbeeldingurl.be", true, gebruiker));

        this.mvc.perform(post("/api/kaarten").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated());

        json = gson.toJson(new Kaart(null, null, true, gebruiker));

        this.mvc.perform(put("/api/kaarten/1").contentType(MediaType.APPLICATION_JSON).content(json));
    }

    @Test(expected = NestedServletException.class)
    public void updateKaart_nonExistingKaart() throws Exception
    {
        String json = gson.toJson(new Kaart("Een kaartje", "http://www.afbeeldingurl.be", true, gebruiker));

        this.mvc.perform(post("/api/kaarten").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated());

        json = gson.toJson(new Kaart("Een gewijzigde kaartje", "http://www.gewijzigdeafbeeldingurl.be", true, gebruiker));

        this.mvc.perform(put("/api/kaarten/2").contentType(MediaType.APPLICATION_JSON).content(json));
    }

    @Test
    public void deleteKaart() throws Exception
    {
        String json = gson.toJson(new Kaart("Een kaartje", "http://www.afbeeldingurl.be", true, gebruiker));

        this.mvc.perform(post("/api/kaarten").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated());

        this.mvc.perform(delete("/api/kaarten/1"))
            .andExpect(status().isOk());

        this.mvc.perform(get("/api/kaarten").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test(expected = NestedServletException.class)
    public void deleteKaart_nonExistingKaart() throws Exception
    {
        String json = gson.toJson(new Kaart("Een kaartje", "http://www.afbeeldingurl.be", true, gebruiker));

        this.mvc.perform(post("/api/kaarten").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated());

        this.mvc.perform(delete("/api/kaarten/2"));
    }


    @Test
    public void commentToevoegenAanKaart() throws Exception
    {
        String kaartJson = gson.toJson(new Kaart("Een kaartje", "http://www.afbeeldingurl.be", true, gebruiker));

        this.mvc.perform(post("/api/kaarten").contentType(MediaType.APPLICATION_JSON).content(kaartJson))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/api/kaarten").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].id", is(1)))
            .andExpect(jsonPath("$[0].tekst", is("Een kaartje")))
            .andExpect(jsonPath("$[0].imageUrl", is("http://www.afbeeldingurl.be")));

        String commentJson = gson.toJson(new Comment("Een comment", gebruiker));

        this.mvc.perform(post("/api/kaarten/1/comments").contentType(MediaType.APPLICATION_JSON).content(commentJson))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/api/kaarten/1/comments").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test(expected = NestedServletException.class)
    public void commentToevoegenAanKaart_nietToegelaten() throws Exception
    {
        String kaartJson = gson.toJson(new Kaart("Een kaartje", "http://www.afbeeldingurl.be", false, gebruiker));

        this.mvc.perform(post("/api/kaarten").contentType(MediaType.APPLICATION_JSON).content(kaartJson))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/api/kaarten").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].id", is(1)))
            .andExpect(jsonPath("$[0].tekst", is("Een kaartje")))
            .andExpect(jsonPath("$[0].imageUrl", is("http://www.afbeeldingurl.be")));

        String commentJson = gson.toJson(new Comment("Een comment", gebruiker));

        this.mvc.perform(post("/api/kaarten/1/comments").contentType(MediaType.APPLICATION_JSON).content(commentJson));
    }


    @Test
    public void koppelKaartAanSubthema() throws Exception
    {

        String kaartJson = gson.toJson(new Kaart("Een kaartje", "http://www.afbeeldingurl.be", false, gebruiker));

        this.mvc.perform(post("/api/kaarten").contentType(MediaType.APPLICATION_JSON).content(kaartJson))
            .andExpect(status().isCreated());

        String subthemaJson = gson.toJson(new Subthema("Een subthema", "beschrijving", hoofdthema));
        String subthemaJson2 = gson.toJson(new Subthema("Een subthema 2", "beschrijving", hoofdthema));


        this.mvc.perform(post("/api/kaarten/1/subthemas").contentType(MediaType.APPLICATION_JSON).content(subthemaJson))
            .andExpect(status().isCreated());

        this.mvc.perform(post("/api/kaarten/1/subthemas").contentType(MediaType.APPLICATION_JSON).content(subthemaJson2))
            .andExpect(status().isCreated());


        this.mvc.perform(get("/api/kaarten/1/subthemas").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)));


    }


    @Test
    public void verschuifKaartMetEénStap() throws Exception
    {

        Kaart kaart = new Kaart("Een kaartje", "http://www.afbeeldingurl.be", false, gebruiker);

        Cirkelsessie cirkelsessie = new Cirkelsessie("Een circelsessie", 10, 10);

        String spelkaartJson = gson.toJson(new Spelkaart(kaart, cirkelsessie));

        this.mvc.perform(post("/api/spelkaarten").contentType(MediaType.APPLICATION_JSON).content(spelkaartJson))
            .andExpect(status().isCreated());


        this.mvc.perform(patch("/api/spelkaarten/verschuif/1").contentType(MediaType.APPLICATION_JSON).content(spelkaartJson))
            .andExpect(status().isOk());


        this.mvc.perform(get("/api/spelkaarten/1").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.positie", is(1)));


    }

    @Test(expected = NestedServletException.class)
    public void verschuifKaartMetEénStap_maxLimitReached() throws Exception
    {

        Kaart kaart = new Kaart("Een kaartje", "http://www.afbeeldingurl.be", false, gebruiker);

        Cirkelsessie cirkelsessie = new Cirkelsessie("Een circelsessie", 10, 10);
        Spelkaart spelkaart = new Spelkaart(kaart, cirkelsessie);
        spelkaart.setPositie(cirkelsessie.getAantalCirkels());

        String spelkaartJson = gson.toJson(spelkaart);

        this.mvc.perform(post("/api/spelkaarten").contentType(MediaType.APPLICATION_JSON).content(spelkaartJson))
            .andExpect(status().isCreated());


        this.mvc.perform(patch("/api/spelkaarten/verschuif/1").contentType(MediaType.APPLICATION_JSON).content(spelkaartJson))
            .andExpect(status().isConflict());

    }

    @Test
    public void legKaartenBuitenDeCirkel() throws Exception
    {

        Cirkelsessie cirkelsessie = new Cirkelsessie("Een circelsessie", 10, 10);
        Kaart kaart;
        Spelkaart spelkaart;
        String spelkaartJson;

        for (int x = 0; x < 5; x++)
        {

            kaart = new Kaart("Een kaartje" + x, "http://www.afbeeldingurl.be", false, gebruiker);

            spelkaart = new Spelkaart(kaart, cirkelsessie);

            spelkaartJson = gson.toJson(spelkaart);

            this.mvc.perform(post("/api/spelkaarten").contentType(MediaType.APPLICATION_JSON).content(spelkaartJson))
                .andExpect(status().isCreated());
        }

        this.mvc.perform(get("/api/spelkaarten").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(5)))
            .andExpect(jsonPath("$[0].positie", is(0)))
            .andExpect(jsonPath("$[1].positie", is(0)))
            .andExpect(jsonPath("$[2].positie", is(0)))
            .andExpect(jsonPath("$[3].positie", is(0)))
            .andExpect(jsonPath("$[4].positie", is(0)));
    }


    @Test
    public void importeerKaartenVanuitCsv() throws Exception
    {
        String gebruikerJson = gson.toJson(new Gebruiker());

        this.mvc.perform(post("/api/kaarten/importCards//kaarten.csv/").contentType(MediaType.APPLICATION_JSON).content(gebruikerJson))
            .andExpect(status().isOk());

        this.mvc.perform(get("/api/kaarten").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(3)));

    }


}
