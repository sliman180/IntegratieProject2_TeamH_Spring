package be.kdg.teamh.api;

import be.kdg.teamh.dtos.request.*;
import be.kdg.teamh.dtos.response.CommentaarResponse;
import be.kdg.teamh.entities.Commentaar;
import be.kdg.teamh.entities.Subthema;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.web.util.NestedServletException;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class KaartApiTest extends ApiTest
{
    @Test
    public void indexKaarten() throws Exception
    {
        http.perform(get("/api/kaarten").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void createKaart() throws Exception
    {
        KaartRequest kaart = new KaartRequest("Een kaartje", "http://www.afbeeldingurl.be", true, 1);
        String json = objectMapper.writeValueAsString(kaart);

        http.perform(post("/api/kaarten", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        http.perform(get("/api/kaarten").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].tekst", is("Een kaartje")))
            .andExpect(jsonPath("$[0].imageUrl", is("http://www.afbeeldingurl.be")));
    }

    @Test(expected = NestedServletException.class)
    public void createKaart_nullInput() throws Exception
    {
        KaartRequest kaart = new KaartRequest(null, null, true, 0);
        String json = objectMapper.writeValueAsString(kaart);

        http.perform(post("/api/kaarten", json).header("Authorization", getAdminToken()));
    }

    @Test
    public void showKaart() throws Exception
    {
        KaartRequest kaart = new KaartRequest("Een kaartje", "http://www.afbeeldingurl.be", true, 1);
        String json = objectMapper.writeValueAsString(kaart);

        http.perform(post("/api/kaarten", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        http.perform(get("/api/kaarten/1").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.imageUrl", is("http://www.afbeeldingurl.be")))
            .andExpect(jsonPath("$.tekst", is("Een kaartje")));
    }

    @Test(expected = NestedServletException.class)
    public void showKaart_nonExistingKaart() throws Exception
    {
        http.perform(get("/api/kaarten/1").header("Authorization", getUserToken()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateKaart() throws Exception
    {
        KaartRequest kaart = new KaartRequest("Een kaartje", "http://www.afbeeldingurl.be", true, 1);
        String json = objectMapper.writeValueAsString(kaart);

        http.perform(post("/api/kaarten", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        kaart = new KaartRequest("Een gewijzigde kaartje", "http://www.gewijzigdeafbeeldingurl.be", true, 1);
        json = objectMapper.writeValueAsString(kaart);

        http.perform(put("/api/kaarten/1", json).header("Authorization", getAdminToken()))
            .andExpect(status().isOk());

        http.perform(get("/api/kaarten/1").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.tekst", is("Een gewijzigde kaartje")))
            .andExpect(jsonPath("$.imageUrl", is("http://www.gewijzigdeafbeeldingurl.be")));
    }

    @Test(expected = NestedServletException.class)
    public void updateKaart_nullInput() throws Exception
    {
        KaartRequest kaart = new KaartRequest("Een kaartje", "http://www.afbeeldingurl.be", true, 1);
        String json = objectMapper.writeValueAsString(kaart);

        http.perform(post("/api/kaarten", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        kaart = new KaartRequest(null, null, true, 0);
        json = objectMapper.writeValueAsString(kaart);

        http.perform(put("/api/kaarten/1", json).header("Authorization", getAdminToken()));
    }

    @Test(expected = NestedServletException.class)
    public void updateKaart_nonExistingKaart() throws Exception
    {
        KaartRequest kaart = new KaartRequest("Een kaartje", "http://www.afbeeldingurl.be", true, 1);
        String json = objectMapper.writeValueAsString(kaart);

        http.perform(post("/api/kaarten", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        kaart = new KaartRequest("Een gewijzigde kaartje", "http://www.gewijzigdeafbeeldingurl.be", true, 1);
        json = objectMapper.writeValueAsString(kaart);

        http.perform(put("/api/kaarten/2", json).header("Authorization", getAdminToken()));
    }

    @Test
    public void deleteKaart() throws Exception
    {
        KaartRequest kaart = new KaartRequest("Een kaartje", "http://www.afbeeldingurl.be", true, 1);
        String json = objectMapper.writeValueAsString(kaart);

        http.perform(post("/api/kaarten", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        http.perform(get("/api/kaarten").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));

        http.perform(delete("/api/kaarten/1").header("Authorization", getAdminToken()))
            .andExpect(status().isOk());

        http.perform(get("/api/kaarten").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test(expected = NestedServletException.class)
    public void deleteKaart_nonExistingKaart() throws Exception
    {
        KaartRequest kaart = new KaartRequest("Een kaartje", "http://www.afbeeldingurl.be", true, 1);
        String json = objectMapper.writeValueAsString(kaart);

        http.perform(post("/api/kaarten", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        http.perform(delete("/api/kaarten/2").header("Authorization", getAdminToken()));
    }

    @Test
    public void commentToevoegenAanKaart() throws Exception
    {
        KaartRequest kaart = new KaartRequest("Een kaartje", "http://www.afbeeldingurl.be", true, 1);
        String json = objectMapper.writeValueAsString(kaart);

        http.perform(post("/api/kaarten", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        CommentaarRequest commentaar = new CommentaarRequest("Een comment", LocalDateTime.now(), 1, 1);
        json = objectMapper.writeValueAsString(commentaar);

        http.perform(post("/api/kaarten/1/comments", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        http.perform(get("/api/kaarten/1/comments").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test(expected = NestedServletException.class)
    public void commentToevoegenAanKaart_nietToegelaten() throws Exception
    {
        KaartRequest kaart = new KaartRequest("Een kaartje", "http://www.afbeeldingurl.be", false, 1);
        String json = objectMapper.writeValueAsString(kaart);

        http.perform(post("/api/kaarten", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        CommentaarRequest commentaar = new CommentaarRequest("Een comment", LocalDateTime.now(), 1, 1);
        json = objectMapper.writeValueAsString(commentaar);

        http.perform(post("/api/kaarten/1/comments", json).header("Authorization", getAdminToken()));
    }

    // TODO fix
    public void koppelKaartAanSubthema() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Voetbal", "Nieuw voetbalveld", 1);
        String json = objectMapper.writeValueAsString(organisatie);

        http.perform(post("/api/organisaties", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        HoofdthemaRequest hoofdthema = new HoofdthemaRequest("Voetbal", "Nieuw voetbalveld", 1, 1);
        json = objectMapper.writeValueAsString(hoofdthema);

        http.perform(post("/api/hoofdthemas", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        KaartRequest kaart = new KaartRequest("Een kaartje", "http://www.afbeeldingurl.be", false, 1);
        json = objectMapper.writeValueAsString(kaart);

        http.perform(post("/api/kaarten", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        SubthemaRequest subthema = new SubthemaRequest("Een subthema", "beschrijving", 1, 1);
        json = objectMapper.writeValueAsString(subthema);

        http.perform(post("/api/kaarten/1/subthemas", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        http.perform(get("/api/kaarten/1/subthemas").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));
    }
}
