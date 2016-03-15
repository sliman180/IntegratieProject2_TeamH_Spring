package be.kdg.teamh.api;

import be.kdg.teamh.dtos.request.*;
import org.junit.Test;
import org.springframework.web.util.NestedServletException;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SpelkaartApiTest extends ApiTest
{
    @Test
    public void indexSpelkaartDTO() throws Exception
    {
        http.perform(get("/api/spelkaarten").header("Authorization", getAdminToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void createSpelkaartDTO() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Voetbal", "Nieuw voetbalveld", 1);
        String json = objectMapper.writeValueAsString(organisatie);

        http.perform(post("/api/organisaties", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        HoofdthemaRequest hoofdthema = new HoofdthemaRequest("Voetbal", "Nieuw voetbalveld", 1, 1);
        json = objectMapper.writeValueAsString(hoofdthema);

        http.perform(post("/api/hoofdthemas", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        SubthemaRequest subthema = new SubthemaRequest("Houffalize", "Route 6", 1);
        json = objectMapper.writeValueAsString(subthema);

        http.perform(post("/api/subthemas", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        CirkelsessieRequest cirkelsessie = new CirkelsessieRequest("Een cirkelsessie", 10, 10, false, LocalDateTime.now(), 1, 1);
        json = objectMapper.writeValueAsString(cirkelsessie);

        http.perform(post("/api/cirkelsessies", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        KaartRequest kaart = new KaartRequest("Een kaart", "http://www.afbeeldingurl.be", true, 1);
        json = objectMapper.writeValueAsString(kaart);

        http.perform(post("/api/kaarten", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        SpelkaartRequest spelkaart = new SpelkaartRequest(1, 1);
        json = objectMapper.writeValueAsString(spelkaart);

        http.perform(post("/api/spelkaarten", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        http.perform(get("/api/spelkaarten").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void showSpelkaartDTO() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Voetbal", "Nieuw voetbalveld", 1);
        String json = objectMapper.writeValueAsString(organisatie);

        http.perform(post("/api/organisaties", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        HoofdthemaRequest hoofdthema = new HoofdthemaRequest("Voetbal", "Nieuw voetbalveld", 1, 1);
        json = objectMapper.writeValueAsString(hoofdthema);

        http.perform(post("/api/hoofdthemas", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        SubthemaRequest subthema = new SubthemaRequest("Houffalize", "Route 6", 1);
        json = objectMapper.writeValueAsString(subthema);

        http.perform(post("/api/subthemas", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        CirkelsessieRequest cirkelsessie = new CirkelsessieRequest("Een cirkelsessie", 10, 10, false, LocalDateTime.now(), 1, 1);
        json = objectMapper.writeValueAsString(cirkelsessie);

        http.perform(post("/api/cirkelsessies", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        KaartRequest kaart = new KaartRequest("Een kaart", "http://www.afbeeldingurl.be", true, 1);
        json = objectMapper.writeValueAsString(kaart);

        http.perform(post("/api/kaarten", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        SpelkaartRequest spelkaart = new SpelkaartRequest(1, 1);
        json = objectMapper.writeValueAsString(spelkaart);

        http.perform(post("/api/spelkaarten", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        http.perform(get("/api/spelkaarten/1").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.positie", is(0)));
    }

    @Test(expected = NestedServletException.class)
    public void showSpelkaartDTO_nonExistingKaartDTO() throws Exception
    {
        http.perform(get("/api/spelkaarten/1").header("Authorization", getUserToken()));
    }

    @Test
    public void updateSpelkaartDTO() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Voetbal", "Nieuw voetbalveld", 1);
        String json = objectMapper.writeValueAsString(organisatie);

        http.perform(post("/api/organisaties", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        HoofdthemaRequest hoofdthema = new HoofdthemaRequest("Voetbal", "Nieuw voetbalveld", 1, 1);
        json = objectMapper.writeValueAsString(hoofdthema);

        http.perform(post("/api/hoofdthemas", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        SubthemaRequest subthema = new SubthemaRequest("Houffalize", "Route 6", 1);
        json = objectMapper.writeValueAsString(subthema);

        http.perform(post("/api/subthemas", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        CirkelsessieRequest cirkelsessie = new CirkelsessieRequest("Een cirkelsessie", 10, 10, false, LocalDateTime.now(), 1, 1);
        json = objectMapper.writeValueAsString(cirkelsessie);

        http.perform(post("/api/cirkelsessies", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        KaartRequest kaart = new KaartRequest("Een kaart", "http://www.afbeeldingurl.be", true, 1);
        json = objectMapper.writeValueAsString(kaart);

        http.perform(post("/api/kaarten", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        SpelkaartRequest spelkaart = new SpelkaartRequest(1, 1);
        json = objectMapper.writeValueAsString(spelkaart);

        http.perform(post("/api/spelkaarten", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        spelkaart.setPositie(5);
        json = objectMapper.writeValueAsString(spelkaart);

        http.perform(put("/api/spelkaarten/1", json).header("Authorization", getAdminToken()))
            .andExpect(status().isOk());

        http.perform(get("/api/spelkaarten/1").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.positie", is(5)));
    }

    @Test(expected = NestedServletException.class)
    public void verschuifKaartDTOMetEénStap_maxLimitReached() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Voetbal", "Nieuw voetbalveld", 1);
        String json = objectMapper.writeValueAsString(organisatie);

        http.perform(post("/api/organisaties", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        HoofdthemaRequest hoofdthema = new HoofdthemaRequest("Voetbal", "Nieuw voetbalveld", 1, 1);
        json = objectMapper.writeValueAsString(hoofdthema);

        http.perform(post("/api/hoofdthemas", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        SubthemaRequest subthema = new SubthemaRequest("Houffalize", "Route 6", 1);
        json = objectMapper.writeValueAsString(subthema);

        http.perform(post("/api/subthemas", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        CirkelsessieRequest cirkelsessie = new CirkelsessieRequest("Een cirkelsessie", 10, 10, false, LocalDateTime.now(), 1, 1);
        json = objectMapper.writeValueAsString(cirkelsessie);

        http.perform(post("/api/cirkelsessies", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        KaartRequest kaart = new KaartRequest("Een kaart", "http://www.afbeeldingurl.be", true, 1);
        json = objectMapper.writeValueAsString(kaart);

        http.perform(post("/api/kaarten", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        SpelkaartRequest spelkaart = new SpelkaartRequest(1, 1);
        json = objectMapper.writeValueAsString(spelkaart);

        http.perform(post("/api/spelkaarten", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        spelkaart.setPositie(cirkelsessie.getAantalCirkels());
        json = objectMapper.writeValueAsString(spelkaart);

        http.perform(put("/api/spelkaarten/1", json).header("Authorization", getAdminToken()))
            .andExpect(status().isOk());

        http.perform(post("/api/spelkaarten/1/verschuif", json).header("Authorization", getAdminToken()));
    }

    @Test(expected = NestedServletException.class)
    public void updateSpelkaartDTO_nonExistingSpelkaartDTO() throws Exception
    {
        SpelkaartRequest spelkaart = new SpelkaartRequest(1, 1);
        spelkaart.setPositie(2);
        String json = objectMapper.writeValueAsString(spelkaart);

        http.perform(put("/api/spelkaarten/2", json).header("Authorization", getAdminToken()));
    }

    @Test
    public void deleteSpelkaartDTO() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Voetbal", "Nieuw voetbalveld", 1);
        String json = objectMapper.writeValueAsString(organisatie);

        http.perform(post("/api/organisaties", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        HoofdthemaRequest hoofdthema = new HoofdthemaRequest("Voetbal", "Nieuw voetbalveld", 1, 1);
        json = objectMapper.writeValueAsString(hoofdthema);

        http.perform(post("/api/hoofdthemas", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        SubthemaRequest subthema = new SubthemaRequest("Houffalize", "Route 6", 1);
        json = objectMapper.writeValueAsString(subthema);

        http.perform(post("/api/subthemas", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        CirkelsessieRequest cirkelsessie = new CirkelsessieRequest("Een cirkelsessie", 10, 10, false, LocalDateTime.now(), 1, 1);
        json = objectMapper.writeValueAsString(cirkelsessie);

        http.perform(post("/api/cirkelsessies", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        KaartRequest kaart = new KaartRequest("Een kaart", "http://www.afbeeldingurl.be", true, 1);
        json = objectMapper.writeValueAsString(kaart);

        http.perform(post("/api/kaarten", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        SpelkaartRequest spelkaart = new SpelkaartRequest(1, 1);
        json = objectMapper.writeValueAsString(spelkaart);

        http.perform(post("/api/spelkaarten", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        http.perform(delete("/api/spelkaarten/1").header("Authorization", getAdminToken()))
            .andExpect(status().isOk());

        http.perform(get("/api/spelkaarten").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test(expected = NestedServletException.class)
    public void deleteSpelkaartDTO_nonExistingSpelkaartDTO() throws Exception
    {
        http.perform(delete("/api/spelkaarten/1").header("Authorization", getAdminToken()));
    }
}
