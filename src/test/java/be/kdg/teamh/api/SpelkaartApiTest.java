package be.kdg.teamh.api;

import be.kdg.teamh.dtos.request.*;
import be.kdg.teamh.entities.Status;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

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
        KaartRequest kaart = new KaartRequest("Naam Kaart", "http://www.afbeelding.url", 1);

        http.perform(post("/api/organisaties", objectMapper.writeValueAsString(organisatie)).header("Authorization", getUserOneToken()));
        http.perform(post("/api/hoofdthemas", objectMapper.writeValueAsString(hoofdthema)).header("Authorization", getUserOneToken()));
        http.perform(post("/api/subthemas", objectMapper.writeValueAsString(subthema)).header("Authorization", getUserOneToken()));
        http.perform(post("/api/cirkelsessies", objectMapper.writeValueAsString(cirkelsessie)).header("Authorization", getUserOneToken()));
        http.perform(post("/api/kaarten", objectMapper.writeValueAsString(kaart)).header("Authorization", getUserOneToken()));
    }

    @Test
    public void indexSpelkaart() throws Exception
    {
        http.perform(get("/api/spelkaarten").header("Authorization", getUserOneToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void createSpelkaart() throws Exception
    {
        SpelkaartRequest spelkaart = new SpelkaartRequest(1, 1);

        http.perform(post("/api/spelkaarten", objectMapper.writeValueAsString(spelkaart)).header("Authorization", getUserOneToken()))
            .andExpect(status().isCreated());

        http.perform(get("/api/spelkaarten").header("Authorization", getUserOneToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void createSpelkaart_zonderAuthenticationHeader() throws Exception
    {
        SpelkaartRequest spelkaart = new SpelkaartRequest(1, 1);

        http.perform(post("/api/spelkaarten", objectMapper.writeValueAsString(spelkaart)))
            .andExpect(status().isBadRequest());
    }
    @Test
    public void createSpelkaart_ongeregistreerdeGebruiker() throws Exception
    {
        SpelkaartRequest spelkaart = new SpelkaartRequest(1, 1);

        http.perform(post("/api/spelkaarten", objectMapper.writeValueAsString(spelkaart)).header("Authorization", getNonExistingUserToken()))
            .andExpect(status().isUnauthorized());
    }


    @Test
    public void showSpelkaart() throws Exception
    {
        SpelkaartRequest spelkaart = new SpelkaartRequest(1, 1);

        http.perform(post("/api/spelkaarten", objectMapper.writeValueAsString(spelkaart)).header("Authorization", getUserOneToken()));

        http.perform(get("/api/spelkaarten/1").header("Authorization", getUserOneToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.positie", is(0)));
    }

    @Test
    public void showSpelkaart_onbestaandeSpelkaart() throws Exception
    {
        http.perform(get("/api/spelkaarten/1").header("Authorization", getUserOneToken()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateSpelkaart() throws Exception
    {
        SpelkaartRequest spelkaart = new SpelkaartRequest(1, 1);

        http.perform(post("/api/spelkaarten", objectMapper.writeValueAsString(spelkaart)).header("Authorization", getUserOneToken()));

        spelkaart.setPositie(5);

        http.perform(put("/api/spelkaarten/1", objectMapper.writeValueAsString(spelkaart)).header("Authorization", getUserOneToken()))
            .andExpect(status().isOk());

        http.perform(get("/api/spelkaarten/1").header("Authorization", getUserOneToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.positie", is(5)));
    }

    @Test
    public void updateSpelkaart_onbestaandeSpelkaart() throws Exception
    {
        SpelkaartRequest spelkaart = new SpelkaartRequest(1, 1);

        http.perform(put("/api/spelkaarten/1", objectMapper.writeValueAsString(spelkaart)).header("Authorization", getUserOneToken()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateSpelkaart_zonderAuthenticationHeader() throws Exception
    {
        SpelkaartRequest spelkaart = new SpelkaartRequest(1, 1);

        http.perform(post("/api/spelkaarten", objectMapper.writeValueAsString(spelkaart)).header("Authorization", getUserOneToken()));

        spelkaart.setPositie(5);

        http.perform(put("/api/spelkaarten/1", objectMapper.writeValueAsString(spelkaart)))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void updateSpelkaart_ongeregistreerdeGebruiker() throws Exception
    {
        SpelkaartRequest spelkaart = new SpelkaartRequest(1, 1);

        http.perform(post("/api/spelkaarten", objectMapper.writeValueAsString(spelkaart)).header("Authorization", getUserOneToken()));

        spelkaart.setPositie(5);

        http.perform(put("/api/spelkaarten/1", objectMapper.writeValueAsString(spelkaart)).header("Authorization", getNonExistingUserToken()))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void deleteSpelkaart() throws Exception
    {
        SpelkaartRequest spelkaart = new SpelkaartRequest(1, 1);

        http.perform(post("/api/spelkaarten", objectMapper.writeValueAsString(spelkaart)).header("Authorization", getUserOneToken()));

        http.perform(delete("/api/spelkaarten/1").header("Authorization", getUserOneToken()))
            .andExpect(status().isOk());

        http.perform(get("/api/spelkaarten").header("Authorization", getUserOneToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void deleteSpelkaart_onbestaandeSpelkaart() throws Exception
    {
        http.perform(delete("/api/spelkaarten/1").header("Authorization", getUserOneToken()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void deleteSpelkaart_zonderAuthenticationHeader() throws Exception
    {
        SpelkaartRequest spelkaart = new SpelkaartRequest(1, 1);

        http.perform(post("/api/spelkaarten", objectMapper.writeValueAsString(spelkaart)).header("Authorization", getUserOneToken()));

        http.perform(delete("/api/spelkaarten/1"))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteSpelkaart_ongeregistreerdeGebruiker() throws Exception
    {
        SpelkaartRequest spelkaart = new SpelkaartRequest(1, 1);

        http.perform(post("/api/spelkaarten", objectMapper.writeValueAsString(spelkaart)).header("Authorization", getUserOneToken()));

        http.perform(delete("/api/spelkaarten/1").header("Authorization", getNonExistingUserToken()))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void verschuifSpelkaart() throws Exception
    {
        SpelkaartRequest spelkaart = new SpelkaartRequest(1, 1);

        http.perform(post("/api/spelkaarten", objectMapper.writeValueAsString(spelkaart)).header("Authorization", getUserOneToken()));

        http.perform(post("/api/spelkaarten/1/verschuif", "").header("Authorization", getUserOneToken()))
            .andExpect(status().isOk());

        http.perform(get("/api/spelkaarten/1").header("Authorization", getUserOneToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.positie", is(1)));
    }

    @Test
    public void verschuifSpelkaart_maximumPositieBereikt() throws Exception
    {
        SpelkaartRequest spelkaart = new SpelkaartRequest(1, 1);

        http.perform(post("/api/spelkaarten", objectMapper.writeValueAsString(spelkaart)).header("Authorization", getUserOneToken()));

        spelkaart.setPositie(10);

        http.perform(put("/api/spelkaarten/1", objectMapper.writeValueAsString(spelkaart)).header("Authorization", getUserOneToken()));

        http.perform(post("/api/spelkaarten/1/verschuif", "").header("Authorization", getUserOneToken()))
            .andExpect(status().isConflict());
    }

    @Test
    public void verschuifSpelkaart_zonderAuthenticationHeader() throws Exception
    {
        SpelkaartRequest spelkaart = new SpelkaartRequest(1, 1);

        http.perform(post("/api/spelkaarten", objectMapper.writeValueAsString(spelkaart)).header("Authorization", getUserOneToken()));

        http.perform(post("/api/spelkaarten/1/verschuif", ""))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void verschuifSpelkaart_ongeregistreerdeGebruiker() throws Exception
    {
        SpelkaartRequest spelkaart = new SpelkaartRequest(1, 1);

        http.perform(post("/api/spelkaarten", objectMapper.writeValueAsString(spelkaart)).header("Authorization", getUserOneToken()));

        http.perform(post("/api/spelkaarten/1/verschuif", "").header("Authorization", getNonExistingUserToken()))
            .andExpect(status().isUnauthorized());
    }
}
