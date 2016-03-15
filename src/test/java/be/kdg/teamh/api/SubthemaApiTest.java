package be.kdg.teamh.api;

import be.kdg.teamh.dtos.request.HoofdthemaRequest;
import be.kdg.teamh.dtos.request.OrganisatieRequest;
import be.kdg.teamh.dtos.request.SubthemaRequest;
import org.junit.Test;
import org.springframework.web.util.NestedServletException;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SubthemaApiTest extends ApiTest
{
    @Test
    public void indexSubthema() throws Exception
    {
        http.perform(get("/api/subthemas").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void createSubthema() throws Exception
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

        http.perform(get("/api/subthemas").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].naam", is("Houffalize")))
            .andExpect(jsonPath("$[0].beschrijving", is("Route 6")));
    }

    @Test(expected = NestedServletException.class)
    public void createSubthema_nullInput() throws Exception
    {
        SubthemaRequest subthema = new SubthemaRequest(null, null, 0);
        String json = objectMapper.writeValueAsString(subthema);

        http.perform(post("/api/subthemas", json).header("Authorization", getUserToken()));
    }

    @Test
    public void showSubthema() throws Exception
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

        http.perform(get("/api/subthemas/1").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.naam", is("Houffalize")))
            .andExpect(jsonPath("$.beschrijving", is("Route 6")));
    }

    @Test(expected = NestedServletException.class)
    public void showSubthema_nonExistingSubthema() throws Exception
    {
        http.perform(get("/api/subthemas/1").header("Authorization", getUserToken()));
    }

    @Test
    public void updateSubthema() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Voetbal", "Nieuw voetbalveld", 1);
        String json = objectMapper.writeValueAsString(organisatie);

        this.http.perform(post("/api/organisaties", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        HoofdthemaRequest hoofdthema = new HoofdthemaRequest("Voetbal", "Nieuw voetbalveld", 1, 1);
        json = objectMapper.writeValueAsString(hoofdthema);

        this.http.perform(post("/api/hoofdthemas", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        SubthemaRequest subthema = new SubthemaRequest("Houffalize", "Route 6", 1);
        json = objectMapper.writeValueAsString(subthema);

        http.perform(post("/api/subthemas", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        subthema = new SubthemaRequest("Houffalize", "Route 3", 1);
        json = objectMapper.writeValueAsString(subthema);

        http.perform(put("/api/subthemas/1", json).header("Authorization", getAdminToken()))
            .andExpect(status().isOk());

        http.perform(get("/api/subthemas/1").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.naam", is("Houffalize")))
            .andExpect(jsonPath("$.beschrijving", is("Route 3")));
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

        SubthemaRequest subthema = new SubthemaRequest("Houffalize", "Route 6", 1);
        json = objectMapper.writeValueAsString(subthema);

        http.perform(post("/api/subthemas", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        subthema = new SubthemaRequest(null, null, 1);
        json = objectMapper.writeValueAsString(subthema);

        http.perform(put("/api/subthemas/1", json).header("Authorization", getAdminToken()));
    }

    @Test(expected = NestedServletException.class)
    public void updateSubthema_nonExistingSubthema() throws Exception
    {
        SubthemaRequest subthema = new SubthemaRequest("Houffalize", "Route 6", 1);
        String json = objectMapper.writeValueAsString(subthema);

        http.perform(put("/api/subthemas/1", json).header("Authorization", getAdminToken()));
    }

    @Test
    public void deleteSubthema() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Voetbal", "Nieuw voetbalveld", 1);
        String json = objectMapper.writeValueAsString(organisatie);

        this.http.perform(post("/api/organisaties", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        HoofdthemaRequest hoofdthema = new HoofdthemaRequest("Voetbal", "Nieuw voetbalveld", 1, 1);
        json = objectMapper.writeValueAsString(hoofdthema);

        this.http.perform(post("/api/hoofdthemas", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        SubthemaRequest subthema = new SubthemaRequest("Houffalize", "Route 6", 1);
        json = objectMapper.writeValueAsString(subthema);

        http.perform(post("/api/subthemas", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        http.perform(delete("/api/subthemas/1").header("Authorization", getAdminToken()))
            .andExpect(status().isOk());

        http.perform(get("/api/subthemas").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test(expected = NestedServletException.class)
    public void deleteSubthema_nonExistingSubthema() throws Exception
    {
        http.perform(delete("/api/subthemas/1").header("Authorization", getAdminToken()));
    }
}
