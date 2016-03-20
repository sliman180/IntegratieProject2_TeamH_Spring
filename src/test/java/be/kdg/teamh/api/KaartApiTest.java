package be.kdg.teamh.api;

import be.kdg.teamh.dtos.request.*;
import org.joda.time.DateTime;
import org.junit.Test;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class KaartApiTest extends ApiTest
{
    @Test
    public void indexKaarten() throws Exception
    {
        http.perform(get("/api/kaarten").header("Authorization", getUserOneToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void createKaart() throws Exception
    {
        KaartRequest kaart = new KaartRequest("Naam Kaart", "http://www.afbeelding.url", 1);

        http.perform(post("/api/kaarten", objectMapper.writeValueAsString(kaart)).header("Authorization", getUserOneToken()))
            .andExpect(status().isCreated());

        http.perform(get("/api/kaarten").header("Authorization", getUserOneToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void createKaart_nullInput() throws Exception
    {
        KaartRequest kaart = new KaartRequest(null, null, 0);

        http.perform(post("/api/kaarten", objectMapper.writeValueAsString(kaart)).header("Authorization", getUserOneToken()))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void createKaart_zonderAuthenticationHeader() throws Exception
    {
        KaartRequest kaart = new KaartRequest("Naam Kaart", "http://www.afbeelding.url", 1);

        http.perform(post("/api/kaarten", objectMapper.writeValueAsString(kaart)))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void createKaart_ongeregistreerdeGebruiker() throws Exception
    {
        KaartRequest kaart = new KaartRequest("Naam Kaart", "http://www.afbeelding.url", 1);

        http.perform(post("/api/kaarten", objectMapper.writeValueAsString(kaart)).header("Authorization", getNonExistingUserToken()))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void showKaart() throws Exception
    {
        KaartRequest kaart = new KaartRequest("Naam Kaart", "http://www.afbeelding.url", 1);

        http.perform(post("/api/kaarten", objectMapper.writeValueAsString(kaart)).header("Authorization", getUserOneToken()));

        http.perform(get("/api/kaarten/1").header("Authorization", getUserOneToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.tekst", is("Naam Kaart")))
            .andExpect(jsonPath("$.imageUrl", is("http://www.afbeelding.url")));
    }

    @Test
    public void showKaart_onbestaandeKaart() throws Exception
    {
        http.perform(get("/api/kaarten/1").header("Authorization", getUserOneToken()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateKaart() throws Exception
    {
        KaartRequest kaart = new KaartRequest("Naam Kaart", "http://www.afbeelding.url", 1);

        http.perform(post("/api/kaarten", objectMapper.writeValueAsString(kaart)).header("Authorization", getUserOneToken()));

        kaart = new KaartRequest("Nieuwe Naam Kaart", "http://www.gewijzigdeafbeelding.url", 1);

        http.perform(put("/api/kaarten/1", objectMapper.writeValueAsString(kaart)).header("Authorization", getUserOneToken()))
            .andExpect(status().isOk());

        http.perform(get("/api/kaarten/1").header("Authorization", getUserOneToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.tekst", is("Nieuwe Naam Kaart")))
            .andExpect(jsonPath("$.imageUrl", is("http://www.gewijzigdeafbeelding.url")));
    }

    @Test
    public void updateKaart_nullInput() throws Exception
    {
        KaartRequest kaart = new KaartRequest(null, null, 1);

        http.perform(post("/api/kaarten", objectMapper.writeValueAsString(kaart)).header("Authorization", getUserOneToken()));

        kaart = new KaartRequest(null, null, 0);

        http.perform(put("/api/kaarten/1", objectMapper.writeValueAsString(kaart)).header("Authorization", getUserOneToken()))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void updateKaart_onbestaandeKaart() throws Exception
    {
        KaartRequest kaart = new KaartRequest("Naam Kaart", "http://www.afbeelding.url", 1);

        http.perform(put("/api/kaarten/1", objectMapper.writeValueAsString(kaart)).header("Authorization", getUserOneToken()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateKaart_zonderAuthenticationHeader() throws Exception
    {
        KaartRequest kaart = new KaartRequest("Naam Kaart", "http://www.afbeelding.url", 1);

        http.perform(post("/api/kaarten", objectMapper.writeValueAsString(kaart)).header("Authorization", getUserOneToken()));

        kaart = new KaartRequest("Nieuwe Naam Kaart", "http://www.gewijzigdeafbeelding.url", 1);

        http.perform(put("/api/kaarten/1", objectMapper.writeValueAsString(kaart)))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void updateKaart_ongeregistreerdeGebruiker() throws Exception
    {
        KaartRequest kaart = new KaartRequest("Naam Kaart", "http://www.afbeelding.url", 1);

        http.perform(post("/api/kaarten", objectMapper.writeValueAsString(kaart)).header("Authorization", getUserOneToken()));

        kaart = new KaartRequest("Nieuwe Naam Kaart", "http://www.gewijzigdeafbeelding.url", 1);

        http.perform(put("/api/kaarten/1", objectMapper.writeValueAsString(kaart)).header("Authorization", getNonExistingUserToken()))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void updateKaart_verkeerdeGebruiker() throws Exception
    {
        KaartRequest kaart = new KaartRequest("Naam Kaart", "http://www.afbeelding.url", 1);

        http.perform(post("/api/kaarten", objectMapper.writeValueAsString(kaart)).header("Authorization", getUserOneToken()));

        kaart = new KaartRequest("Nieuwe Naam Kaart", "http://www.gewijzigdeafbeelding.url", 1);

        http.perform(put("/api/kaarten/1", objectMapper.writeValueAsString(kaart)).header("Authorization", getUserTwoToken()))
            .andExpect(status().isForbidden());
    }

    @Test
    public void deleteKaart() throws Exception
    {
        KaartRequest kaart = new KaartRequest("Naam Kaart", "http://www.afbeelding.url", 1);

        http.perform(post("/api/kaarten", objectMapper.writeValueAsString(kaart)).header("Authorization", getUserOneToken()));

        http.perform(delete("/api/kaarten/1").header("Authorization", getUserOneToken()))
            .andExpect(status().isOk());

        http.perform(get("/api/kaarten").header("Authorization", getUserOneToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void deleteKaart_onbestaandeKaart() throws Exception
    {
        http.perform(delete("/api/kaarten/1").header("Authorization", getUserOneToken()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void deleteKaart_zonderAuthenticationHeader() throws Exception
    {
        KaartRequest kaart = new KaartRequest("Naam Kaart", "http://www.afbeelding.url", 1);

        http.perform(post("/api/kaarten", objectMapper.writeValueAsString(kaart)).header("Authorization", getUserOneToken()));

        http.perform(delete("/api/kaarten/1"))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteKaart_ongeregistreerdeGebruiker() throws Exception
    {
        KaartRequest kaart = new KaartRequest("Naam Kaart", "http://www.afbeelding.url", 1);

        http.perform(post("/api/kaarten", objectMapper.writeValueAsString(kaart)).header("Authorization", getUserOneToken()));

        http.perform(delete("/api/kaarten/1").header("Authorization", getNonExistingUserToken()))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void deleteKaart_verkeerdeGebruiker() throws Exception
    {
        KaartRequest kaart = new KaartRequest("Naam Kaart", "http://www.afbeelding.url", 1);

        http.perform(post("/api/kaarten", objectMapper.writeValueAsString(kaart)).header("Authorization", getUserOneToken()));

        http.perform(delete("/api/kaarten/1").header("Authorization", getUserTwoToken()))
            .andExpect(status().isForbidden());
    }

    @Test
    public void commentaarToevoegenAanKaart() throws Exception
    {
        KaartRequest kaart = new KaartRequest("Naam Kaart", "http://www.afbeelding.url", 1);

        http.perform(post("/api/kaarten", objectMapper.writeValueAsString(kaart)).header("Authorization", getUserOneToken()));

        CommentaarRequest commentaar = new CommentaarRequest("Commentaar", DateTime.now(), 1, 1);

        http.perform(post("/api/kaarten/1/commentaren", objectMapper.writeValueAsString(commentaar)).header("Authorization", getUserOneToken()))
            .andExpect(status().isCreated());

        http.perform(get("/api/kaarten/1/commentaren").header("Authorization", getUserOneToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void commentaarToevoegenAanKaart_commentaarNietToegelaten() throws Exception
    {
        KaartRequest kaart = new KaartRequest("Een kaartje", "http://www.afbeeldingurl.be", false, 1);

        http.perform(post("/api/kaarten", objectMapper.writeValueAsString(kaart)).header("Authorization", getUserOneToken()));

        CommentaarRequest commentaar = new CommentaarRequest("Commentaar", DateTime.now(), 1, 1);

        http.perform(post("/api/kaarten/1/commentaren", objectMapper.writeValueAsString(commentaar)).header("Authorization", getUserOneToken()))
            .andExpect(status().isConflict());
    }

    @Test
    public void commentaarToevoegenAanKaart_zonderAuthenticationHeader() throws Exception
    {
        KaartRequest kaart = new KaartRequest("Naam Kaart", "http://www.afbeelding.url", 1);

        http.perform(post("/api/kaarten", objectMapper.writeValueAsString(kaart)).header("Authorization", getUserOneToken()));

        CommentaarRequest commentaar = new CommentaarRequest("Commentaar", DateTime.now(), 1, 1);

        http.perform(post("/api/kaarten/1/commentaren", objectMapper.writeValueAsString(commentaar)))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void commentaarToevoegenAanKaart_ongeregistreerdeGebruiker() throws Exception
    {
        KaartRequest kaart = new KaartRequest("Naam Kaart", "http://www.afbeelding.url", 1);

        http.perform(post("/api/kaarten", objectMapper.writeValueAsString(kaart)).header("Authorization", getUserOneToken()));

        CommentaarRequest commentaar = new CommentaarRequest("Commentaar", DateTime.now(), 1, 1);

        http.perform(post("/api/kaarten/1/commentaren", objectMapper.writeValueAsString(commentaar)).header("Authorization", getNonExistingUserToken()))
            .andExpect(status().isUnauthorized());
    }
}
