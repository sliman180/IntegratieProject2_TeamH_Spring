package be.kdg.teamh.api;

import be.kdg.teamh.dtos.request.*;
import be.kdg.teamh.entities.Status;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.util.NestedServletException;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CirkelsessieApiTest extends ApiTest
{
    @Before
    public void setUpParents() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Naam Organisatie", "Beschrijving Organisatie", 1);
        HoofdthemaRequest hoofdthema = new HoofdthemaRequest("Naam Hoofdthema", "Beschrijving Hoofdthema", 1, 1);
        SubthemaRequest subthema = new SubthemaRequest("Naam Subthema", "Beschrijving Subthema", 1, 1);

        http.perform(post("/api/organisaties", objectMapper.writeValueAsString(organisatie)).header("Authorization", getUserToken()));
        http.perform(post("/api/hoofdthemas", objectMapper.writeValueAsString(hoofdthema)).header("Authorization", getUserToken()));
        http.perform(post("/api/subthemas", objectMapper.writeValueAsString(subthema)).header("Authorization", getUserToken()));
    }

    @Test
    public void indexCirkesessie() throws Exception
    {
        http.perform(get("/api/cirkelsessies").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void createCirkelsessie() throws Exception
    {
        CirkelsessieRequest cirkelsessie = new CirkelsessieRequest("Naam Cirkelsessie", Status.OPEN, 10, 10, DateTime.now(), 1, 1);

        http.perform(post("/api/cirkelsessies", objectMapper.writeValueAsString(cirkelsessie)).header("Authorization", getUserToken()))
            .andExpect(status().isCreated());

        http.perform(get("/api/cirkelsessies").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void createCirkelsessie_nullInput() throws Exception
    {
        CirkelsessieRequest cirkelsessie = new CirkelsessieRequest(null, null, 0, 0, null, 1, 1);

        http.perform(post("/api/cirkelsessies", objectMapper.writeValueAsString(cirkelsessie)).header("Authorization", getUserToken()))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void showCirkelsessie() throws Exception
    {
        CirkelsessieRequest cirkelsessie = new CirkelsessieRequest("Naam Cirkelsessie", Status.OPEN, 10, 10, DateTime.now(), 1, 1);

        http.perform(post("/api/cirkelsessies", objectMapper.writeValueAsString(cirkelsessie)).header("Authorization", getUserToken()));

        http.perform(get("/api/cirkelsessies/1").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.naam", is("Naam Cirkelsessie")))
            .andExpect(jsonPath("$.status", is("OPEN")))
            .andExpect(jsonPath("$.aantalCirkels", is(10)))
            .andExpect(jsonPath("$.maxAantalKaarten", is(10)));
    }

    @Test
    public void showCirkelsessie_onbestaandeCirkelsessie() throws Exception
    {
        http.perform(get("/api/cirkelsessies/1").header("Authorization", getUserToken()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateCirkelsessie() throws Exception
    {
        CirkelsessieRequest cirkelsessie = new CirkelsessieRequest("Naam Cirkelsessie", Status.OPEN, 10, 10, DateTime.now(), 1, 1);

        http.perform(post("/api/cirkelsessies", objectMapper.writeValueAsString(cirkelsessie)).header("Authorization", getUserToken()));

        cirkelsessie = new CirkelsessieRequest("Nieuwe Naam Cirkelsessie", Status.GESLOTEN, 8, 15, DateTime.now(), 1, 1);

        http.perform(put("/api/cirkelsessies/1", objectMapper.writeValueAsString(cirkelsessie)).header("Authorization", getUserToken()))
            .andExpect(status().isOk());

        http.perform(get("/api/cirkelsessies/1").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.naam", is("Nieuwe Naam Cirkelsessie")))
            .andExpect(jsonPath("$.status", is("GESLOTEN")))
            .andExpect(jsonPath("$.aantalCirkels", is(8)))
            .andExpect(jsonPath("$.maxAantalKaarten", is(15)));
    }

    @Test
    public void updateCirkelsessie_nullInput() throws Exception
    {
        CirkelsessieRequest cirkelsessie = new CirkelsessieRequest("Naam Cirkelsessie", Status.OPEN, 10, 10, DateTime.now(), 1, 1);

        http.perform(post("/api/cirkelsessies", objectMapper.writeValueAsString(cirkelsessie)).header("Authorization", getUserToken()));

        cirkelsessie = new CirkelsessieRequest(null, null, 0, 0, DateTime.now(), 1, 1);

        http.perform(put("/api/cirkelsessies/1", objectMapper.writeValueAsString(cirkelsessie)).header("Authorization", getUserToken()))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void updateCirkelsessie_onbestaandeCirkelsessie() throws Exception
    {
        CirkelsessieRequest cirkelsessie = new CirkelsessieRequest("Session one", Status.OPEN, 10, 10, DateTime.now(), 1, 1);

        http.perform(put("/api/cirkelsessies/1", objectMapper.writeValueAsString(cirkelsessie)).header("Authorization", getUserToken()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void deleteCirkelsessie() throws Exception
    {
        CirkelsessieRequest cirkelsessie = new CirkelsessieRequest("Naam Cirkelsessie", Status.OPEN, 10, 10, DateTime.now(), 1, 1);

        http.perform(post("/api/cirkelsessies", objectMapper.writeValueAsString(cirkelsessie)).header("Authorization", getUserToken()));

        http.perform(delete("/api/cirkelsessies/1").header("Authorization", getUserToken()))
            .andExpect(status().isOk());

        http.perform(get("/api/cirkelsessies").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void deleteCirkelsessie_onbestaandeCirkelsessie() throws Exception
    {
        http.perform(delete("/api/cirkelsessies/1").header("Authorization", getUserToken()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void cloneCirkelSessie() throws Exception
    {
        CirkelsessieRequest cirkelsessie = new CirkelsessieRequest("Naam Cirkelsessie", Status.OPEN, 10, 10, DateTime.now(), 1, 1);

        http.perform(post("/api/cirkelsessies", objectMapper.writeValueAsString(cirkelsessie)).header("Authorization", getUserToken()));

        http.perform(post("/api/cirkelsessies/1/clone", "").header("Authorization", getUserToken()))
            .andExpect(status().isCreated());

        http.perform(get("/api/cirkelsessies/2").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.naam", is("Naam Cirkelsessie")))
            .andExpect(jsonPath("$.status", is("OPEN")))
            .andExpect(jsonPath("$.aantalCirkels", is(10)))
            .andExpect(jsonPath("$.maxAantalKaarten", is(10)));
    }

    @Test
    public void actieveCirkelSessies() throws Exception
    {
        CirkelsessieRequest cirkelsessie = new CirkelsessieRequest("Session one", Status.GESLOTEN, 10, 10, DateTime.now(), 1, 1);

        http.perform(post("/api/cirkelsessies", objectMapper.writeValueAsString(cirkelsessie)).header("Authorization", getUserToken()));

        cirkelsessie = new CirkelsessieRequest("Session two", Status.OPEN, 10, 10, DateTime.now(), 1, 1);

        http.perform(post("/api/cirkelsessies", objectMapper.writeValueAsString(cirkelsessie)).header("Authorization", getUserToken()));

        cirkelsessie = new CirkelsessieRequest("Session three", Status.OPEN, 10, 10, DateTime.now(), 1, 1);

        http.perform(post("/api/cirkelsessies", objectMapper.writeValueAsString(cirkelsessie)).header("Authorization", getUserToken()));

        http.perform(get("/api/cirkelsessies/actief").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void geplandeCirkelSessies() throws Exception
    {
        CirkelsessieRequest cirkelsessie = new CirkelsessieRequest("Een cirkelsessie", Status.OPEN, 10, 10, DateTime.now().plusDays(1), 1, 1);

        http.perform(post("/api/cirkelsessies", objectMapper.writeValueAsString(cirkelsessie)).header("Authorization", getUserToken()));

        cirkelsessie = new CirkelsessieRequest("Session two", Status.OPEN, 10, 10, DateTime.now().minusDays(1), 1, 1);

        http.perform(post("/api/cirkelsessies", objectMapper.writeValueAsString(cirkelsessie)).header("Authorization", getUserToken()));

        http.perform(get("/api/cirkelsessies/gepland").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void addDeelnameToCirkelsessie() throws Exception
    {
        CirkelsessieRequest cirkelsessie = new CirkelsessieRequest("Naam Cirkelsessie", Status.OPEN, 10, 10, DateTime.now(), 1, 1);

        http.perform(post("/api/cirkelsessies", objectMapper.writeValueAsString(cirkelsessie)).header("Authorization", getUserToken()));

        DeelnameRequest deelname = new DeelnameRequest(15, false, DateTime.now(), 1, 1);

        this.http.perform(post("/api/cirkelsessies/1/deelnames", objectMapper.writeValueAsString(deelname)).header("Authorization", getUserToken()))
            .andExpect(status().isCreated());

        this.http.perform(get("/api/cirkelsessies/1/deelnames").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));
    }
}
