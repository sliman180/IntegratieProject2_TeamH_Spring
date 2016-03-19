package be.kdg.teamh.api;

import be.kdg.teamh.dtos.request.*;
import be.kdg.teamh.entities.Status;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.util.NestedServletException;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SpelkaartApiTest extends ApiTest
{
    @Before
    public void setUpParents() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Naam Organisatie", "Beschrijving Organisatie", 1);
        HoofdthemaRequest hoofdthema = new HoofdthemaRequest("Naam Hoofdthema", "Beschrijving Hoofdthema", 1, 1);
        SubthemaRequest subthema = new SubthemaRequest("Naam Subthema", "Beschrijving Subthema", 1, 1);
        CirkelsessieRequest cirkelsessie = new CirkelsessieRequest("Naam Cirkelsessie", Status.OPEN, 10, 10, DateTime.now(), 1, 1);
        KaartRequest kaart = new KaartRequest("Naam Kaart", "http://www.afbeelding.url", true, 1);

        http.perform(post("/api/organisaties", objectMapper.writeValueAsString(organisatie)).header("Authorization", getUserToken()));
        http.perform(post("/api/hoofdthemas", objectMapper.writeValueAsString(hoofdthema)).header("Authorization", getUserToken()));
        http.perform(post("/api/subthemas", objectMapper.writeValueAsString(subthema)).header("Authorization", getUserToken()));
        http.perform(post("/api/cirkelsessies", objectMapper.writeValueAsString(cirkelsessie)).header("Authorization", getUserToken()));
        http.perform(post("/api/kaarten", objectMapper.writeValueAsString(kaart)).header("Authorization", getUserToken()));
    }

    @Test
    public void indexSpelkaart() throws Exception
    {
        http.perform(get("/api/spelkaarten").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void createSpelkaart() throws Exception
    {
        SpelkaartRequest spelkaart = new SpelkaartRequest(1, 1);

        http.perform(post("/api/spelkaarten", objectMapper.writeValueAsString(spelkaart)).header("Authorization", getUserToken()))
            .andExpect(status().isCreated());

        http.perform(get("/api/spelkaarten").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void showSpelkaart() throws Exception
    {
        SpelkaartRequest spelkaart = new SpelkaartRequest(1, 1);

        http.perform(post("/api/spelkaarten", objectMapper.writeValueAsString(spelkaart)).header("Authorization", getUserToken()));

        http.perform(get("/api/spelkaarten/1").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.positie", is(0)));
    }

    @Test
    public void showSpelkaart_onbestaandeSpelkaart() throws Exception
    {
        http.perform(get("/api/spelkaarten/1").header("Authorization", getUserToken()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateSpelkaart() throws Exception
    {
        SpelkaartRequest spelkaart = new SpelkaartRequest(1, 1);

        http.perform(post("/api/spelkaarten", objectMapper.writeValueAsString(spelkaart)).header("Authorization", getUserToken()));

        spelkaart.setPositie(5);

        http.perform(put("/api/spelkaarten/1", objectMapper.writeValueAsString(spelkaart)).header("Authorization", getUserToken()))
            .andExpect(status().isOk());

        http.perform(get("/api/spelkaarten/1").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.positie", is(5)));
    }

    @Test
    public void updateSpelkaart_onbestaandeSpelkaart() throws Exception
    {
        SpelkaartRequest spelkaart = new SpelkaartRequest(1, 1);

        http.perform(put("/api/spelkaarten/1", objectMapper.writeValueAsString(spelkaart)).header("Authorization", getUserToken()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void deleteSpelkaart() throws Exception
    {
        SpelkaartRequest spelkaart = new SpelkaartRequest(1, 1);

        http.perform(post("/api/spelkaarten", objectMapper.writeValueAsString(spelkaart)).header("Authorization", getUserToken()));

        http.perform(delete("/api/spelkaarten/1").header("Authorization", getUserToken()))
            .andExpect(status().isOk());

        http.perform(get("/api/spelkaarten").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void deleteSpelkaart_onbestaandeSpelkaart() throws Exception
    {
        http.perform(delete("/api/spelkaarten/1").header("Authorization", getUserToken()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void verschuifSpelkaartMetEenStap() throws Exception
    {
        SpelkaartRequest spelkaart = new SpelkaartRequest(1, 1);

        http.perform(post("/api/spelkaarten", objectMapper.writeValueAsString(spelkaart)).header("Authorization", getUserToken()));

        http.perform(post("/api/spelkaarten/1/verschuif", "").header("Authorization", getUserToken()))
            .andExpect(status().isOk());

        http.perform(get("/api/spelkaarten/1").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.positie", is(1)));
    }

    @Test
    public void verschuifSpelkaartMetEenStap_maximumPositieBereikt() throws Exception
    {
        SpelkaartRequest spelkaart = new SpelkaartRequest(1, 1);

        http.perform(post("/api/spelkaarten", objectMapper.writeValueAsString(spelkaart)).header("Authorization", getUserToken()));

        spelkaart.setPositie(10);

        http.perform(put("/api/spelkaarten/1", objectMapper.writeValueAsString(spelkaart)).header("Authorization", getUserToken()));

        http.perform(post("/api/spelkaarten/1/verschuif", "").header("Authorization", getUserToken()))
            .andExpect(status().isConflict());
    }
}
