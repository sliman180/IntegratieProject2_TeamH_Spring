package be.kdg.teamh;

import be.kdg.teamh.entities.Commentaar;
import be.kdg.teamh.entities.Kaart;
import be.kdg.teamh.entities.Subthema;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.web.util.NestedServletException;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class KaartTest extends ApiTest
{
    @Test
    public void indexKaarten() throws Exception
    {
        mvc.perform(get("/api/kaarten").accept(MediaType.APPLICATION_JSON).header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void createKaart() throws Exception
    {
        String json = objectMapper.writeValueAsString(new Kaart("Een kaartje", "http://www.afbeeldingurl.be", true, null));

        mvc.perform(post("/api/kaarten").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        mvc.perform(get("/api/kaarten").accept(MediaType.APPLICATION_JSON).header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].tekst", is("Een kaartje")))
            .andExpect(jsonPath("$[0].imageUrl", is("http://www.afbeeldingurl.be")));
    }

    @Test(expected = NestedServletException.class)
    public void createKaart_nullInput() throws Exception
    {
        String json = objectMapper.writeValueAsString(new Kaart(null, null, true, null));

        mvc.perform(post("/api/kaarten").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()));
    }

    @Test
    public void showKaart() throws Exception
    {
        String json = objectMapper.writeValueAsString(new Kaart("Een kaartje", "http://www.afbeeldingurl.be", true, null));

        mvc.perform(post("/api/kaarten").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        mvc.perform(get("/api/kaarten/1").accept(MediaType.APPLICATION_JSON).header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.imageUrl", is("http://www.afbeeldingurl.be")))
            .andExpect(jsonPath("$.tekst", is("Een kaartje")));
    }

    @Test(expected = NestedServletException.class)
    public void showKaart_nonExistingKaart() throws Exception
    {
        mvc.perform(get("/api/kaarten/1").accept(MediaType.APPLICATION_JSON).header("Authorization", getUserToken()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateKaart() throws Exception
    {
        Kaart kaart = new Kaart("Een kaartje", "http://www.afbeeldingurl.be", true, null);
        String json = objectMapper.writeValueAsString(kaart);

        mvc.perform(post("/api/kaarten").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        kaart = new Kaart("Een gewijzigde kaartje", "http://www.gewijzigdeafbeeldingurl.be", true, null);
        json = objectMapper.writeValueAsString(kaart);

        mvc.perform(put("/api/kaarten/1").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isOk());

        mvc.perform(get("/api/kaarten/1").accept(MediaType.APPLICATION_JSON).header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.tekst", is("Een gewijzigde kaartje")))
            .andExpect(jsonPath("$.imageUrl", is("http://www.gewijzigdeafbeeldingurl.be")));
    }

    @Test(expected = NestedServletException.class)
    public void updateKaart_nullInput() throws Exception
    {
        Kaart kaart = new Kaart("Een kaartje", "http://www.afbeeldingurl.be", true, null);
        String json = objectMapper.writeValueAsString(kaart);

        mvc.perform(post("/api/kaarten").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        kaart = new Kaart(null, null, true, null);
        json = objectMapper.writeValueAsString(kaart);

        mvc.perform(put("/api/kaarten/1").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()));
    }

    @Test(expected = NestedServletException.class)
    public void updateKaart_nonExistingKaart() throws Exception
    {
        Kaart kaart = new Kaart("Een kaartje", "http://www.afbeeldingurl.be", true, null);
        String json = objectMapper.writeValueAsString(kaart);

        mvc.perform(post("/api/kaarten").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        kaart = new Kaart("Een gewijzigde kaartje", "http://www.gewijzigdeafbeeldingurl.be", true, null);
        json = objectMapper.writeValueAsString(kaart);

        mvc.perform(put("/api/kaarten/2").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()));
    }

    @Test
    public void deleteKaart() throws Exception
    {
        Kaart kaart = new Kaart("Een kaartje", "http://www.afbeeldingurl.be", true, null);
        String json = objectMapper.writeValueAsString(kaart);

        mvc.perform(post("/api/kaarten").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        mvc.perform(get("/api/kaarten").accept(MediaType.APPLICATION_JSON).header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));

        mvc.perform(delete("/api/kaarten/1").header("Authorization", getAdminToken()))
            .andExpect(status().isOk());

        mvc.perform(get("/api/kaarten").accept(MediaType.APPLICATION_JSON).header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test(expected = NestedServletException.class)
    public void deleteKaart_nonExistingKaart() throws Exception
    {
        Kaart kaart = new Kaart("Een kaartje", "http://www.afbeeldingurl.be", true, null);
        String json = objectMapper.writeValueAsString(kaart);

        mvc.perform(post("/api/kaarten").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        mvc.perform(delete("/api/kaarten/2").header("Authorization", getAdminToken()));
    }

    @Test
    public void commentToevoegenAanKaart() throws Exception
    {
        Kaart kaart = new Kaart("Een kaartje", "http://www.afbeeldingurl.be", true, null);
        String json = objectMapper.writeValueAsString(kaart);

        mvc.perform(post("/api/kaarten").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        mvc.perform(get("/api/kaarten").accept(MediaType.APPLICATION_JSON).header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].tekst", is("Een kaartje")))
            .andExpect(jsonPath("$[0].imageUrl", is("http://www.afbeeldingurl.be")));

        String commentJson = objectMapper.writeValueAsString(new Commentaar("Een comment", null));

        mvc.perform(post("/api/kaarten/1/comments").contentType(MediaType.APPLICATION_JSON).content(commentJson).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        mvc.perform(get("/api/kaarten/1/comments").contentType(MediaType.APPLICATION_JSON).header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test(expected = NestedServletException.class)
    public void commentToevoegenAanKaart_nietToegelaten() throws Exception
    {
        String json = objectMapper.writeValueAsString(new Kaart("Een kaartje", "http://www.afbeeldingurl.be", false, null));

        mvc.perform(post("/api/kaarten").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        mvc.perform(get("/api/kaarten").accept(MediaType.APPLICATION_JSON).header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].tekst", is("Een kaartje")))
            .andExpect(jsonPath("$[0].imageUrl", is("http://www.afbeeldingurl.be")));

        json = objectMapper.writeValueAsString(new Commentaar("Een comment", null));

        mvc.perform(post("/api/kaarten/1/comments").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()));
    }

    @Test // TODO: change url
    public void koppelKaartAanSubthema() throws Exception
    {
        String json = objectMapper.writeValueAsString(new Kaart("Een kaartje", "http://www.afbeeldingurl.be", false, null));

        mvc.perform(post("/api/kaarten").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        json = objectMapper.writeValueAsString(new Subthema("Een subthema", "beschrijving", null));

        mvc.perform(post("/api/kaarten/1/subthemas").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        json = objectMapper.writeValueAsString(new Subthema("Een subthema 2", "beschrijving", null));

        mvc.perform(post("/api/kaarten/1/subthemas").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        mvc.perform(get("/api/kaarten/1/subthemas").contentType(MediaType.APPLICATION_JSON).header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)));
    }
}
