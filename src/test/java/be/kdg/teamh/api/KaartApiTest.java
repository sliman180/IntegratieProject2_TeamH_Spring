package be.kdg.teamh.api;

import be.kdg.teamh.dtos.request.*;
import org.joda.time.DateTime;
import org.junit.Test;
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
        KaartRequest kaart = new KaartRequest("Naam Kaart", "http://www.afbeelding.url", true, 1);

        http.perform(post("/api/kaarten", objectMapper.writeValueAsString(kaart)).header("Authorization", getUserToken()))
            .andExpect(status().isCreated());

        http.perform(get("/api/kaarten").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void createKaart_nullInput() throws Exception
    {
        KaartRequest kaart = new KaartRequest(null, null, true, 0);

        http.perform(post("/api/kaarten", objectMapper.writeValueAsString(kaart)).header("Authorization", getUserToken()))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void showKaart() throws Exception
    {
        KaartRequest kaart = new KaartRequest("Naam Kaart", "http://www.afbeelding.url", true, 1);

        http.perform(post("/api/kaarten", objectMapper.writeValueAsString(kaart)).header("Authorization", getUserToken()));

        http.perform(get("/api/kaarten/1").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.tekst", is("Naam Kaart")))
            .andExpect(jsonPath("$.imageUrl", is("http://www.afbeelding.url")));
    }

    @Test
    public void showKaart_nonExistingKaart() throws Exception
    {
        http.perform(get("/api/kaarten/1").header("Authorization", getUserToken()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateKaart() throws Exception
    {
        KaartRequest kaart = new KaartRequest("Naam Kaart", "http://www.afbeelding.url", true, 1);

        http.perform(post("/api/kaarten", objectMapper.writeValueAsString(kaart)).header("Authorization", getUserToken()));

        kaart = new KaartRequest("Nieuwe Naam Kaart", "http://www.gewijzigdeafbeelding.url", true, 1);

        http.perform(put("/api/kaarten/1", objectMapper.writeValueAsString(kaart)).header("Authorization", getUserToken()))
            .andExpect(status().isOk());

        http.perform(get("/api/kaarten/1").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.tekst", is("Nieuwe Naam Kaart")))
            .andExpect(jsonPath("$.imageUrl", is("http://www.gewijzigdeafbeelding.url")));
    }

    @Test
    public void updateKaart_nullInput() throws Exception
    {
        KaartRequest kaart = new KaartRequest(null, null, true, 1);

        http.perform(post("/api/kaarten", objectMapper.writeValueAsString(kaart)).header("Authorization", getUserToken()));

        kaart = new KaartRequest(null, null, true, 0);

        http.perform(put("/api/kaarten/1", objectMapper.writeValueAsString(kaart)).header("Authorization", getUserToken()))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void updateKaart_nonExistingKaart() throws Exception
    {
        KaartRequest kaart = new KaartRequest("Naam Kaart", "http://www.afbeelding.url", true, 1);

        http.perform(put("/api/kaarten/1", objectMapper.writeValueAsString(kaart)).header("Authorization", getUserToken()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void deleteKaart() throws Exception
    {
        KaartRequest kaart = new KaartRequest("Naam Kaart", "http://www.afbeelding.url", true, 1);

        http.perform(post("/api/kaarten", objectMapper.writeValueAsString(kaart)).header("Authorization", getUserToken()));

        http.perform(delete("/api/kaarten/1").header("Authorization", getUserToken()))
            .andExpect(status().isOk());

        http.perform(get("/api/kaarten").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void deleteKaart_nonExistingKaart() throws Exception
    {
        http.perform(delete("/api/kaarten/1").header("Authorization", getUserToken()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void commentToevoegenAanKaart() throws Exception
    {
        KaartRequest kaart = new KaartRequest("Naam Kaart", "http://www.afbeelding.url", true, 1);

        http.perform(post("/api/kaarten", objectMapper.writeValueAsString(kaart)).header("Authorization", getUserToken()));

        CommentaarRequest commentaar = new CommentaarRequest("Commentaar", DateTime.now(), 1, 1);

        http.perform(post("/api/kaarten/1/comments", objectMapper.writeValueAsString(commentaar)).header("Authorization", getUserToken()))
            .andExpect(status().isCreated());

        http.perform(get("/api/kaarten/1/comments").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void commentToevoegenAanKaart_nietToegelaten() throws Exception
    {
        KaartRequest kaart = new KaartRequest("Een kaartje", "http://www.afbeeldingurl.be", false, 1);

        http.perform(post("/api/kaarten", objectMapper.writeValueAsString(kaart)).header("Authorization", getUserToken()));

        CommentaarRequest commentaar = new CommentaarRequest("Commentaar", DateTime.now(), 1, 1);

        http.perform(post("/api/kaarten/1/comments", objectMapper.writeValueAsString(commentaar)).header("Authorization", getUserToken()))
            .andExpect(status().isConflict());
    }
}
