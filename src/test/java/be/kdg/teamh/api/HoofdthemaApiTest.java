package be.kdg.teamh.api;

import be.kdg.teamh.dtos.request.HoofdthemaRequest;
import be.kdg.teamh.dtos.request.OrganisatieRequest;
import be.kdg.teamh.entities.Hoofdthema;
import org.junit.Test;
import org.springframework.web.util.NestedServletException;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HoofdthemaApiTest extends ApiTest
{
    @Test
    public void indexHoofdthema() throws Exception
    {
        this.http.perform(get("/api/hoofdthemas").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void createHoofdthema() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Voetbal", "Nieuw voetbalveld", 1);
        String json = objectMapper.writeValueAsString(organisatie);

        this.http.perform(post("/api/organisaties", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        HoofdthemaRequest hoofdthema = new HoofdthemaRequest("Voetbal", "Nieuw voetbalveld", 1, 1);
        json = objectMapper.writeValueAsString(hoofdthema);

        this.http.perform(post("/api/hoofdthemas", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.http.perform(get("/api/hoofdthemas").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].naam", is("Voetbal")))
            .andExpect(jsonPath("$[0].beschrijving", is("Nieuw voetbalveld")));
    }

    @Test(expected = NestedServletException.class)
    public void createHoofdthema_nullInput() throws Exception
    {
        Hoofdthema hoofdthema = new Hoofdthema(null, null, null, null);
        String json = objectMapper.writeValueAsString(hoofdthema);

        this.http.perform(post("/api/hoofdthemas", json).header("Authorization", getAdminToken()));
    }

    @Test
    public void showHoofdthema() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Voetbal", "Nieuw voetbalveld", 1);
        String json = objectMapper.writeValueAsString(organisatie);

        this.http.perform(post("/api/organisaties", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        HoofdthemaRequest hoofdthema = new HoofdthemaRequest("Voetbal", "Nieuw voetbalveld", 1, 1);
        json = objectMapper.writeValueAsString(hoofdthema);

        this.http.perform(post("/api/hoofdthemas", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.http.perform(get("/api/hoofdthemas/1").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.naam", is("Voetbal")))
            .andExpect(jsonPath("$.beschrijving", is("Nieuw voetbalveld")));
    }

    @Test(expected = NestedServletException.class)
    public void showHoofdthema_nonExistingHoofdthema() throws Exception
    {
        this.http.perform(get("/api/hoofdthemas/1").header("Authorization", getUserToken()));
    }

    @Test
    public void updateHoofdthema() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Voetbal", "Nieuw voetbalveld", 1);
        String json = objectMapper.writeValueAsString(organisatie);

        this.http.perform(post("/api/organisaties", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        HoofdthemaRequest hoofdthema = new HoofdthemaRequest("Voetbal", "Nieuw voetbalveld", 1, 1);
        json = objectMapper.writeValueAsString(hoofdthema);

        this.http.perform(post("/api/hoofdthemas", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        hoofdthema = new HoofdthemaRequest("Voetbal", "Vernieuwd voetbalveld", 1, 1);
        json = objectMapper.writeValueAsString(hoofdthema);

        this.http.perform(put("/api/hoofdthemas/1", json).header("Authorization", getAdminToken()))
            .andExpect(status().isOk());

        this.http.perform(get("/api/hoofdthemas/1").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.naam", is("Voetbal")))
            .andExpect(jsonPath("$.beschrijving", is("Vernieuwd voetbalveld")));
    }

    @Test(expected = NestedServletException.class)
    public void updateHoofdthema_nullInput() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Voetbal", "Nieuw voetbalveld", 1);
        String json = objectMapper.writeValueAsString(organisatie);

        this.http.perform(post("/api/organisaties", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        HoofdthemaRequest hoofdthema = new HoofdthemaRequest("Voetbal", "Nieuw voetbalveld", 1, 1);
        json = objectMapper.writeValueAsString(hoofdthema);

        this.http.perform(post("/api/hoofdthemas", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        hoofdthema = new HoofdthemaRequest(null, null, 1, 1);
        json = objectMapper.writeValueAsString(hoofdthema);

        this.http.perform(put("/api/hoofdthemas/1", json).header("Authorization", getAdminToken()));
    }

    @Test(expected = NestedServletException.class)
    public void updateHoofdthema_nonExistingHoofdthema() throws Exception
    {
        HoofdthemaRequest hoofdthema = new HoofdthemaRequest("Voetbal", "Vernieuwd voetbalveld", 1, 1);
        String json = objectMapper.writeValueAsString(hoofdthema);

        this.http.perform(put("/api/hoofdthemas/1", json).header("Authorization", getAdminToken()));
    }

    @Test
    public void deleteHoofdthema() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Voetbal", "Nieuw voetbalveld", 1);
        String json = objectMapper.writeValueAsString(organisatie);

        this.http.perform(post("/api/organisaties", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        HoofdthemaRequest hoofdthema = new HoofdthemaRequest("Voetbal", "Nieuw voetbalveld", 1, 1);
        json = objectMapper.writeValueAsString(hoofdthema);

        this.http.perform(post("/api/hoofdthemas", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.http.perform(delete("/api/hoofdthemas/1").header("Authorization", getAdminToken()))
            .andExpect(status().isOk());

        this.http.perform(get("/api/hoofdthemas").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test(expected = NestedServletException.class)
    public void deleteHoofdthema_nonExistingHoofdthema() throws Exception
    {
        this.http.perform(delete("/api/hoofdthemas/1").header("Authorization", getAdminToken()));
    }
}
