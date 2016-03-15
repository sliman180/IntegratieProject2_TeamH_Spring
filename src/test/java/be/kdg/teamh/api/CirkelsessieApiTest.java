package be.kdg.teamh.api;

import be.kdg.teamh.dtos.request.*;
import be.kdg.teamh.entities.Status;
import org.junit.Test;
import org.springframework.web.util.NestedServletException;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CirkelsessieApiTest extends ApiTest
{
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
        OrganisatieRequest organisatie = new OrganisatieRequest("Voetbal", "Nieuw voetbalveld", 1);
        String json = objectMapper.writeValueAsString(organisatie);

        http.perform(post("/api/organisaties", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        HoofdthemaRequest hoofdthema = new HoofdthemaRequest("Voetbal", "Nieuw voetbalveld", 1, 1);
        json = objectMapper.writeValueAsString(hoofdthema);

        http.perform(post("/api/hoofdthemas", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        SubthemaRequest subthema = new SubthemaRequest("Houffalize", "Route 6", 1, 1);
        json = objectMapper.writeValueAsString(subthema);

        http.perform(post("/api/subthemas", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        CirkelsessieRequest cirkelsessie = new CirkelsessieRequest("Een cirkelsessie", Status.OPEN, 10, 10, LocalDateTime.now(), 1, 1);
        json = objectMapper.writeValueAsString(cirkelsessie);

        http.perform(post("/api/cirkelsessies", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        http.perform(get("/api/cirkelsessies").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test(expected = NestedServletException.class)
    public void createCirkelsessie_nullInput() throws Exception
    {
        CirkelsessieRequest cirkelsessie = new CirkelsessieRequest(null, null, 0, 0, null, 1, 1);
        String json = objectMapper.writeValueAsString(cirkelsessie);

        http.perform(post("/api/cirkelsessies", json).header("Authorization", getAdminToken()));
    }

    @Test
    public void showCirkelsessie() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Voetbal", "Nieuw voetbalveld", 1);
        String json = objectMapper.writeValueAsString(organisatie);

        http.perform(post("/api/organisaties", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        HoofdthemaRequest hoofdthema = new HoofdthemaRequest("Voetbal", "Nieuw voetbalveld", 1, 1);
        json = objectMapper.writeValueAsString(hoofdthema);

        http.perform(post("/api/hoofdthemas", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        SubthemaRequest subthema = new SubthemaRequest("Houffalize", "Route 6", 1, 1);
        json = objectMapper.writeValueAsString(subthema);

        http.perform(post("/api/subthemas", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        CirkelsessieRequest cirkelsessie = new CirkelsessieRequest("Een cirkelsessie", Status.OPEN, 10, 10, LocalDateTime.now(), 1, 1);
        json = objectMapper.writeValueAsString(cirkelsessie);

        http.perform(post("/api/cirkelsessies", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        http.perform(get("/api/cirkelsessies/1").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.naam", is("Een cirkelsessie")))
            .andExpect(jsonPath("$.aantalCirkels", is(10)))
            .andExpect(jsonPath("$.maxAantalKaarten", is(10)));
    }

    @Test(expected = NestedServletException.class)
    public void showCirkelsessie_nonExistingCirkelsessie() throws Exception
    {
        http.perform(get("/api/cirkelsessies/1").header("Authorization", getUserToken()));
    }

    @Test
    public void updateCirkelsessie() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Voetbal", "Nieuw voetbalveld", 1);
        String json = objectMapper.writeValueAsString(organisatie);

        http.perform(post("/api/organisaties", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        HoofdthemaRequest hoofdthema = new HoofdthemaRequest("Voetbal", "Nieuw voetbalveld", 1, 1);
        json = objectMapper.writeValueAsString(hoofdthema);

        http.perform(post("/api/hoofdthemas", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        SubthemaRequest subthema = new SubthemaRequest("Houffalize", "Route 6", 1, 1);
        json = objectMapper.writeValueAsString(subthema);

        http.perform(post("/api/subthemas", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        CirkelsessieRequest cirkelsessie = new CirkelsessieRequest("Een cirkelsessie", Status.OPEN, 10, 10, LocalDateTime.now(), 1, 1);
        json = objectMapper.writeValueAsString(cirkelsessie);

        http.perform(post("/api/cirkelsessies", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        cirkelsessie = new CirkelsessieRequest("Session two", Status.OPEN, 8, 15, LocalDateTime.now(), 1, 1);
        json = objectMapper.writeValueAsString(cirkelsessie);

        http.perform(put("/api/cirkelsessies/1", json).header("Authorization", getAdminToken()))
            .andExpect(status().isOk());

        http.perform(get("/api/cirkelsessies/1").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.naam", is("Session two")))
            .andExpect(jsonPath("$.aantalCirkels", is(8)))
            .andExpect(jsonPath("$.maxAantalKaarten", is(15)));
    }

    @Test(expected = NestedServletException.class)
    public void updateCirkelsessie_nullInput() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Voetbal", "Nieuw voetbalveld", 1);
        String json = objectMapper.writeValueAsString(organisatie);

        http.perform(post("/api/organisaties", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        HoofdthemaRequest hoofdthema = new HoofdthemaRequest("Voetbal", "Nieuw voetbalveld", 1, 1);
        json = objectMapper.writeValueAsString(hoofdthema);

        http.perform(post("/api/hoofdthemas", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        SubthemaRequest subthema = new SubthemaRequest("Houffalize", "Route 6", 1, 1);
        json = objectMapper.writeValueAsString(subthema);

        http.perform(post("/api/subthemas", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        CirkelsessieRequest cirkelsessie = new CirkelsessieRequest("Een cirkelsessie", Status.OPEN, 10, 10, LocalDateTime.now(), 1, 1);
        json = objectMapper.writeValueAsString(cirkelsessie);

        http.perform(post("/api/cirkelsessies", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        cirkelsessie = new CirkelsessieRequest(null, null, 0, 0, LocalDateTime.now(), 1, 1);
        json = objectMapper.writeValueAsString(cirkelsessie);

        http.perform(put("/api/cirkelsessies/1", json).header("Authorization", getAdminToken()));
    }

    @Test(expected = NestedServletException.class)
    public void updateCirkelsessie_nonExistingCirkelsessie() throws Exception
    {
        CirkelsessieRequest cirkelsessie = new CirkelsessieRequest("Session one", Status.OPEN, 10, 10, LocalDateTime.now(), 1, 1);
        String json = objectMapper.writeValueAsString(cirkelsessie);

        http.perform(put("/api/cirkelsessies/1", json).header("Authorization", getAdminToken()));
    }

    @Test(expected = NestedServletException.class)
    public void deleteCirkelsessie() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Voetbal", "Nieuw voetbalveld", 1);
        String json = objectMapper.writeValueAsString(organisatie);

        http.perform(post("/api/organisaties", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        HoofdthemaRequest hoofdthema = new HoofdthemaRequest("Voetbal", "Nieuw voetbalveld", 1, 1);
        json = objectMapper.writeValueAsString(hoofdthema);

        http.perform(post("/api/hoofdthemas", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        SubthemaRequest subthema = new SubthemaRequest("Houffalize", "Route 6", 1, 1);
        json = objectMapper.writeValueAsString(subthema);

        http.perform(post("/api/subthemas", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        CirkelsessieRequest cirkelsessie = new CirkelsessieRequest("Een cirkelsessie", Status.OPEN, 10, 10, LocalDateTime.now(), 1, 1);
        json = objectMapper.writeValueAsString(cirkelsessie);

        http.perform(post("/api/cirkelsessies", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        http.perform(delete("/api/cirkelsessies/1").header("Authorization", getAdminToken()))
            .andExpect(status().isOk());

        http.perform(get("/api/cirkelsessies/1").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test(expected = NestedServletException.class)
    public void deleteCirkelsessie_nonExistingCirkelsessie() throws Exception
    {
        http.perform(delete("/api/cirkelsessies/1").header("Authorization", getAdminToken()));
    }

    // TODO fix
    public void checkCirkelsessieLinkedToSubthema() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Voetbal", "Nieuw voetbalveld", 1);
        String json = objectMapper.writeValueAsString(organisatie);

        http.perform(post("/api/organisaties", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        HoofdthemaRequest hoofdthema = new HoofdthemaRequest("Voetbal", "Nieuw voetbalveld", 1, 1);
        json = objectMapper.writeValueAsString(hoofdthema);

        http.perform(post("/api/hoofdthemas", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        SubthemaRequest subthema = new SubthemaRequest("Houffalize", "Route 6", 1, 1);
        json = objectMapper.writeValueAsString(subthema);

        http.perform(post("/api/subthemas", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        CirkelsessieRequest cirkelsessie = new CirkelsessieRequest("Een cirkelsessie", Status.OPEN, 10, 10, LocalDateTime.now(), 1, 1);
        json = objectMapper.writeValueAsString(cirkelsessie);

        http.perform(post("/api/cirkelsessies", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        http.perform(get("/api/cirkelsessies/1/subthema").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.naam", is("Houffalize")))
            .andExpect(jsonPath("$.beschrijving", is("Route 6")));
    }

    @Test
    public void cloneCirkelSessie() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Voetbal", "Nieuw voetbalveld", 1);
        String json = objectMapper.writeValueAsString(organisatie);

        http.perform(post("/api/organisaties", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        HoofdthemaRequest hoofdthema = new HoofdthemaRequest("Voetbal", "Nieuw voetbalveld", 1, 1);
        json = objectMapper.writeValueAsString(hoofdthema);

        http.perform(post("/api/hoofdthemas", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        SubthemaRequest subthema = new SubthemaRequest("Houffalize", "Route 6", 1, 1);
        json = objectMapper.writeValueAsString(subthema);

        http.perform(post("/api/subthemas", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        CirkelsessieRequest cirkelsessie = new CirkelsessieRequest("Een cirkelsessie", Status.OPEN, 10, 10, LocalDateTime.now(), 1, 1);
        json = objectMapper.writeValueAsString(cirkelsessie);

        http.perform(post("/api/cirkelsessies", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        http.perform(post("/api/cirkelsessies/1/clone", "").header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        http.perform(get("/api/cirkelsessies/2").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(2)))
            .andExpect(jsonPath("$.naam", is("Een cirkelsessie")))
            .andExpect(jsonPath("$.aantalCirkels", is(10)))
            .andExpect(jsonPath("$.maxAantalKaarten", is(10)))
            .andExpect(jsonPath("$.deelnames", hasSize(0)));
    }

    @Test
    public void showActieveCirkelSessies() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Voetbal", "Nieuw voetbalveld", 1);
        String json = objectMapper.writeValueAsString(organisatie);

        http.perform(post("/api/organisaties", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        HoofdthemaRequest hoofdthema = new HoofdthemaRequest("Voetbal", "Nieuw voetbalveld", 1, 1);
        json = objectMapper.writeValueAsString(hoofdthema);

        http.perform(post("/api/hoofdthemas", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        SubthemaRequest subthema = new SubthemaRequest("Houffalize", "Route 6", 1, 1);
        json = objectMapper.writeValueAsString(subthema);

        http.perform(post("/api/subthemas", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        CirkelsessieRequest cirkelsessie = new CirkelsessieRequest("Session one", Status.GESLOTEN, 10, 10, LocalDateTime.now().plusDays(1), 1, 1);
        json = objectMapper.writeValueAsString(cirkelsessie);

        http.perform(post("/api/cirkelsessies", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        cirkelsessie = new CirkelsessieRequest("Session two", Status.OPEN, 10, 10, LocalDateTime.now().minusDays(1), 1, 1);
        json = objectMapper.writeValueAsString(cirkelsessie);

        http.perform(post("/api/cirkelsessies", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        cirkelsessie = new CirkelsessieRequest("Session three", Status.OPEN, 10, 10, LocalDateTime.now().minusDays(1), 1, 1);
        json = objectMapper.writeValueAsString(cirkelsessie);

        http.perform(post("/api/cirkelsessies", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        http.perform(get("/api/cirkelsessies/actief").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void showGeplandeCirkelSessies() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Voetbal", "Nieuw voetbalveld", 1);
        String json = objectMapper.writeValueAsString(organisatie);

        http.perform(post("/api/organisaties", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        HoofdthemaRequest hoofdthema = new HoofdthemaRequest("Voetbal", "Nieuw voetbalveld", 1, 1);
        json = objectMapper.writeValueAsString(hoofdthema);

        http.perform(post("/api/hoofdthemas", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        SubthemaRequest subthema = new SubthemaRequest("Houffalize", "Route 6", 1, 1);
        json = objectMapper.writeValueAsString(subthema);

        http.perform(post("/api/subthemas", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        CirkelsessieRequest cirkelsessie = new CirkelsessieRequest("Een cirkelsessie", Status.OPEN, 10, 10, LocalDateTime.now().plusDays(1), 1, 1);
        json = objectMapper.writeValueAsString(cirkelsessie);

        http.perform(post("/api/cirkelsessies", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        cirkelsessie = new CirkelsessieRequest("Session two", Status.OPEN, 10, 10, LocalDateTime.now().minusDays(1), 1, 1);
        json = objectMapper.writeValueAsString(cirkelsessie);

        http.perform(post("/api/cirkelsessies", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        http.perform(get("/api/cirkelsessies/gepland").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void addDeelnameToCirkelsessie() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Voetbal", "Nieuw voetbalveld", 1);
        String json = objectMapper.writeValueAsString(organisatie);

        http.perform(post("/api/organisaties", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        HoofdthemaRequest hoofdthema = new HoofdthemaRequest("Voetbal", "Nieuw voetbalveld", 1, 1);
        json = objectMapper.writeValueAsString(hoofdthema);

        http.perform(post("/api/hoofdthemas", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        SubthemaRequest subthema = new SubthemaRequest("Houffalize", "Route 6", 1, 1);
        json = objectMapper.writeValueAsString(subthema);

        http.perform(post("/api/subthemas", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        CirkelsessieRequest cirkelsessie = new CirkelsessieRequest("Een cirkelsessie", Status.OPEN, 10, 10, LocalDateTime.now(), 1, 1);
        json = objectMapper.writeValueAsString(cirkelsessie);

        http.perform(post("/api/cirkelsessies", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        DeelnameRequest deelname = new DeelnameRequest(15, false, LocalDateTime.now(), 1, 1);
        json = objectMapper.writeValueAsString(deelname);

        this.http.perform(post("/api/cirkelsessies/1/deelnames", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.http.perform(get("/api/cirkelsessies/1/deelnames").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));
    }
}
