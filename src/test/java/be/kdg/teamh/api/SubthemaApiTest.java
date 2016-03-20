package be.kdg.teamh.api;

import be.kdg.teamh.dtos.request.HoofdthemaRequest;
import be.kdg.teamh.dtos.request.OrganisatieRequest;
import be.kdg.teamh.dtos.request.SubthemaRequest;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SubthemaApiTest extends ApiTest
{
    @Before
    public void setUpParents() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Naam Organisatie", "Beschrijving Organisatie", 1);
        HoofdthemaRequest hoofdthema = new HoofdthemaRequest("Naam Hoofdthema", "Beschrijving Hoofdthema", 1, 1);

        http.perform(post("/api/organisaties", objectMapper.writeValueAsString(organisatie)).header("Authorization", getUserOneToken()));
        http.perform(post("/api/hoofdthemas", objectMapper.writeValueAsString(hoofdthema)).header("Authorization", getUserOneToken()));
    }

    @Test
    public void indexSubthema() throws Exception
    {
        http.perform(get("/api/subthemas").header("Authorization", getUserOneToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void createSubthema() throws Exception
    {
        SubthemaRequest subthema = new SubthemaRequest("Naam Subthema", "Beschrijving Subthema", 1, 1);

        http.perform(post("/api/subthemas", objectMapper.writeValueAsString(subthema)).header("Authorization", getUserOneToken()))
            .andExpect(status().isCreated());

        http.perform(get("/api/subthemas").header("Authorization", getUserOneToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void createSubthema_nullInput() throws Exception
    {
        SubthemaRequest subthema = new SubthemaRequest(null, null, 0, 0);

        http.perform(post("/api/subthemas", objectMapper.writeValueAsString(subthema)).header("Authorization", getUserOneToken()))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void createSubthema_zonderAuthenticationHeader() throws Exception
    {
        SubthemaRequest subthema = new SubthemaRequest("Naam Subthema", "Beschrijving Subthema", 1, 1);

        http.perform(post("/api/subthemas", objectMapper.writeValueAsString(subthema)))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void createSubthema_ongeregistreerdeGebruiker() throws Exception
    {
        SubthemaRequest subthema = new SubthemaRequest("Naam Subthema", "Beschrijving Subthema", 1, 1);

        http.perform(post("/api/subthemas", objectMapper.writeValueAsString(subthema)).header("Authorization", getNonExistingUserToken()))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void showSubthema() throws Exception
    {
        SubthemaRequest subthema = new SubthemaRequest("Naam Subthema", "Beschrijving Subthema", 1, 1);

        http.perform(post("/api/subthemas", objectMapper.writeValueAsString(subthema)).header("Authorization", getUserOneToken()));

        http.perform(get("/api/subthemas/1").header("Authorization", getUserOneToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.naam", is("Naam Subthema")))
            .andExpect(jsonPath("$.beschrijving", is("Beschrijving Subthema")));
    }

    @Test
    public void showSubthema_onbestaandSubthema() throws Exception
    {
        http.perform(get("/api/subthemas/1").header("Authorization", getUserOneToken()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateSubthema() throws Exception
    {
        SubthemaRequest subthema = new SubthemaRequest("Naam Subthema", "Beschrijving Subthema", 1, 1);

        http.perform(post("/api/subthemas", objectMapper.writeValueAsString(subthema)).header("Authorization", getUserOneToken()));

        subthema = new SubthemaRequest("Nieuwe Naam Subthema", "Nieuwe Beschrijving Subthema", 1, 1);

        http.perform(put("/api/subthemas/1", objectMapper.writeValueAsString(subthema)).header("Authorization", getUserOneToken()))
            .andExpect(status().isOk());

        http.perform(get("/api/subthemas/1").header("Authorization", getUserOneToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.naam", is("Nieuwe Naam Subthema")))
            .andExpect(jsonPath("$.beschrijving", is("Nieuwe Beschrijving Subthema")));
    }

    @Test
    public void updateHoofdthema_nullInput() throws Exception
    {
        SubthemaRequest subthema = new SubthemaRequest("Naam Subthema", "Beschrijving Subthema", 1, 1);

        http.perform(post("/api/subthemas", objectMapper.writeValueAsString(subthema)).header("Authorization", getUserOneToken()));

        subthema = new SubthemaRequest(null, null, 1, 1);

        http.perform(put("/api/subthemas/1", objectMapper.writeValueAsString(subthema)).header("Authorization", getUserOneToken()))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void updateSubthema_onbestaandSubthema() throws Exception
    {
        SubthemaRequest subthema = new SubthemaRequest("Naam Subthema", "Beschrijving Subthema", 1, 1);

        http.perform(put("/api/subthemas/1", objectMapper.writeValueAsString(subthema)).header("Authorization", getUserOneToken()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateSubthema_zonderAuthenticationHeader() throws Exception
    {
        SubthemaRequest subthema = new SubthemaRequest("Naam Subthema", "Beschrijving Subthema", 1, 1);

        http.perform(post("/api/subthemas", objectMapper.writeValueAsString(subthema)).header("Authorization", getUserOneToken()));

        subthema = new SubthemaRequest("Nieuwe Naam Subthema", "Nieuwe Beschrijving Subthema", 1, 1);

        http.perform(put("/api/subthemas/1", objectMapper.writeValueAsString(subthema)))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void updateSubthema_ongeregistreerdeGebruiker() throws Exception
    {
        SubthemaRequest subthema = new SubthemaRequest("Naam Subthema", "Beschrijving Subthema", 1, 1);

        http.perform(post("/api/subthemas", objectMapper.writeValueAsString(subthema)).header("Authorization", getUserOneToken()));

        subthema = new SubthemaRequest("Nieuwe Naam Subthema", "Nieuwe Beschrijving Subthema", 1, 1);

        http.perform(put("/api/subthemas/1", objectMapper.writeValueAsString(subthema)).header("Authorization", getNonExistingUserToken()))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void updateSubthema_verkeerdeGebruiker() throws Exception
    {
        SubthemaRequest subthema = new SubthemaRequest("Naam Subthema", "Beschrijving Subthema", 1, 1);

        http.perform(post("/api/subthemas", objectMapper.writeValueAsString(subthema)).header("Authorization", getUserOneToken()));

        subthema = new SubthemaRequest("Nieuwe Naam Subthema", "Nieuwe Beschrijving Subthema", 1, 1);

        http.perform(put("/api/subthemas/1", objectMapper.writeValueAsString(subthema)).header("Authorization", getUserTwoToken()))
            .andExpect(status().isForbidden());
    }

    @Test
    public void deleteSubthema() throws Exception
    {
        SubthemaRequest subthema = new SubthemaRequest("Naam Subthema", "Beschrijving Subthema", 1, 1);

        http.perform(post("/api/subthemas", objectMapper.writeValueAsString(subthema)).header("Authorization", getUserOneToken()));

        http.perform(delete("/api/subthemas/1").header("Authorization", getUserOneToken()))
            .andExpect(status().isOk());

        http.perform(get("/api/subthemas").header("Authorization", getUserOneToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void deleteSubthema_onbestaandSubthema() throws Exception
    {
        http.perform(delete("/api/subthemas/1").header("Authorization", getUserOneToken()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void deleteSubthema_zonderAuthenticationHeader() throws Exception
    {
        SubthemaRequest subthema = new SubthemaRequest("Naam Subthema", "Beschrijving Subthema", 1, 1);

        http.perform(post("/api/subthemas", objectMapper.writeValueAsString(subthema)).header("Authorization", getUserOneToken()));

        http.perform(delete("/api/subthemas/1"))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteSubthema_ongeregistreerdeGebruiker() throws Exception
    {
        SubthemaRequest subthema = new SubthemaRequest("Naam Subthema", "Beschrijving Subthema", 1, 1);

        http.perform(post("/api/subthemas", objectMapper.writeValueAsString(subthema)).header("Authorization", getUserOneToken()));

        http.perform(delete("/api/subthemas/1").header("Authorization", getNonExistingUserToken()))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void deleteSubthema_verkeerdeGebruiker() throws Exception
    {
        SubthemaRequest subthema = new SubthemaRequest("Naam Subthema", "Beschrijving Subthema", 1, 1);

        http.perform(post("/api/subthemas", objectMapper.writeValueAsString(subthema)).header("Authorization", getUserOneToken()));

        http.perform(delete("/api/subthemas/1").header("Authorization", getUserTwoToken()))
            .andExpect(status().isForbidden());
    }
}
