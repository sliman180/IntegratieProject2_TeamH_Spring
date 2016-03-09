package be.kdg.teamh;

import be.kdg.teamh.entities.Cirkelsessie;
import be.kdg.teamh.entities.Kaart;
import be.kdg.teamh.entities.Spelkaart;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.web.util.NestedServletException;

import java.util.Date;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SpelkaartTest extends ApiTest
{
    @Test
    public void indexSpelkaart() throws Exception
    {
        this.mvc.perform(get("/api/spelkaarten").accept(MediaType.APPLICATION_JSON).header("Authorization", getAdminToken()))
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

        this.mvc.perform(post("/api/spelkaarten").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/api/spelkaarten").accept(MediaType.APPLICATION_JSON).header("Authorization", getUserToken()))
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

        this.mvc.perform(post("/api/spelkaarten").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/api/spelkaarten/1").accept(MediaType.APPLICATION_JSON).header("Authorization", getUserToken()))
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

        this.mvc.perform(post("/api/spelkaarten").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/api/spelkaarten/2").accept(MediaType.APPLICATION_JSON).header("Authorization", getUserToken()));
    }

    @Test
    public void updateSpelkaart() throws Exception
    {
        Cirkelsessie cirkelsessie = new Cirkelsessie("Een cirkelsessie", 10, 10, false, new Date(), null, null);
        Kaart kaart = new Kaart("Een kaart", "http://www.afbeeldingurl.be", true, null);
        Spelkaart spelkaart = new Spelkaart(kaart, cirkelsessie);
        String json = objectMapper.writeValueAsString(spelkaart);

        this.mvc.perform(post("/api/spelkaarten").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        spelkaart.setPositie(5);
        json = objectMapper.writeValueAsString(spelkaart);

        this.mvc.perform(put("/api/spelkaarten/1").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isOk());

        this.mvc.perform(get("/api/spelkaarten/1").accept(MediaType.APPLICATION_JSON).header("Authorization", getUserToken()))
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

        this.mvc.perform(post("/api/spelkaarten").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.mvc.perform(post("/api/spelkaarten/1/verschuif").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isConflict());
    }

    @Test(expected = NestedServletException.class)
    public void updateSpelkaart_nonExistingSpelkaart() throws Exception
    {
        Cirkelsessie cirkelsessie = new Cirkelsessie("Een cirkelsessie", 10, 10, false, new Date(), null, null);
        Kaart kaart = new Kaart("Een kaart", "http://www.afbeeldingurl.be", true, null);
        Spelkaart spelkaart = new Spelkaart(kaart, cirkelsessie);
        String json = objectMapper.writeValueAsString(spelkaart);

        this.mvc.perform(post("/api/spelkaarten").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        spelkaart.setPositie(2);
        json = objectMapper.writeValueAsString(spelkaart);

        this.mvc.perform(put("/api/spelkaarten/2").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()));
    }

    @Test
    public void deleteSpelkaart() throws Exception
    {
        Cirkelsessie cirkelsessie = new Cirkelsessie("Een cirkelsessie", 10, 10, false, new Date(), null, null);
        Kaart kaart = new Kaart("Een kaart", "http://www.afbeeldingurl.be", true, null);
        Spelkaart spelkaart = new Spelkaart(kaart, cirkelsessie);
        String json = objectMapper.writeValueAsString(spelkaart);

        this.mvc.perform(post("/api/spelkaarten").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.mvc.perform(delete("/api/spelkaarten/1").header("Authorization", getAdminToken()))
            .andExpect(status().isOk());

        this.mvc.perform(get("/api/spelkaarten").accept(MediaType.APPLICATION_JSON).header("Authorization", getUserToken()))
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

        this.mvc.perform(post("/api/spelkaarten").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.mvc.perform(delete("/api/spelkaarten/2").header("Authorization", getAdminToken()));
    }
}
