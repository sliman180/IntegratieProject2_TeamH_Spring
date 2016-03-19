package be.kdg.teamh.api;

import be.kdg.teamh.dtos.request.HoofdthemaRequest;
import be.kdg.teamh.dtos.request.OrganisatieRequest;
import be.kdg.teamh.entities.Hoofdthema;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.util.NestedServletException;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HoofdthemaApiTest extends ApiTest
{
    @Before
    public void setUpParents() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Naam Organisatie", "Beschrijving Organisatie", 1);

        http.perform(post("/api/organisaties", objectMapper.writeValueAsString(organisatie)).header("Authorization", getUserToken()));
    }

    @Test
    public void indexHoofdthema() throws Exception
    {
        http.perform(get("/api/hoofdthemas").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void createHoofdthema() throws Exception
    {
        HoofdthemaRequest hoofdthema = new HoofdthemaRequest("Naam Hoofdthema", "Beschrijving Hoofdthema", 1, 1);

        http.perform(post("/api/hoofdthemas", objectMapper.writeValueAsString(hoofdthema)).header("Authorization", getUserToken()))
            .andExpect(status().isCreated());

        http.perform(get("/api/hoofdthemas").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void createHoofdthema_nullInput() throws Exception
    {
        HoofdthemaRequest hoofdthema = new HoofdthemaRequest(null, null, 1, 1);

        http.perform(post("/api/hoofdthemas", objectMapper.writeValueAsString(hoofdthema)).header("Authorization", getUserToken()))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void showHoofdthema() throws Exception
    {
        HoofdthemaRequest hoofdthema = new HoofdthemaRequest("Naam Hoofdthema", "Beschrijving Hoofdthema", 1, 1);

        http.perform(post("/api/hoofdthemas", objectMapper.writeValueAsString(hoofdthema)).header("Authorization", getUserToken()));

        http.perform(get("/api/hoofdthemas/1").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.naam", is("Naam Hoofdthema")))
            .andExpect(jsonPath("$.beschrijving", is("Beschrijving Hoofdthema")));
    }

    @Test
    public void showHoofdthema_onbestaandHoofdthema() throws Exception
    {
        http.perform(get("/api/hoofdthemas/1").header("Authorization", getUserToken()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateHoofdthema() throws Exception
    {
        HoofdthemaRequest hoofdthema = new HoofdthemaRequest("Naam Hoofdthema", "Beschrijving Hoofdthema", 1, 1);

        http.perform(post("/api/hoofdthemas", objectMapper.writeValueAsString(hoofdthema)).header("Authorization", getUserToken()));

        hoofdthema = new HoofdthemaRequest("Nieuwe Naam Hoofdthema", "Nieuwe Beschrijving Hoofdthema", 1, 1);

        http.perform(put("/api/hoofdthemas/1", objectMapper.writeValueAsString(hoofdthema)).header("Authorization", getUserToken()))
            .andExpect(status().isOk());

        http.perform(get("/api/hoofdthemas/1").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.naam", is("Nieuwe Naam Hoofdthema")))
            .andExpect(jsonPath("$.beschrijving", is("Nieuwe Beschrijving Hoofdthema")));
    }

    @Test
    public void updateHoofdthema_nullInput() throws Exception
    {
        HoofdthemaRequest hoofdthema = new HoofdthemaRequest("Naam Hoofdthema", "Beschrijving Hoofdthema", 1, 1);

        http.perform(post("/api/hoofdthemas", objectMapper.writeValueAsString(hoofdthema)).header("Authorization", getUserToken()));

        hoofdthema = new HoofdthemaRequest(null, null, 1, 1);

        http.perform(put("/api/hoofdthemas/1", objectMapper.writeValueAsString(hoofdthema)).header("Authorization", getUserToken()))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void updateHoofdthema_onbestaandHoofdthema() throws Exception
    {
        HoofdthemaRequest hoofdthema = new HoofdthemaRequest("Naam Hoofdthema", "Beschrijving Hoofdthema", 1, 1);

        http.perform(put("/api/hoofdthemas/1", objectMapper.writeValueAsString(hoofdthema)).header("Authorization", getUserToken()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void deleteHoofdthema() throws Exception
    {
        HoofdthemaRequest hoofdthema = new HoofdthemaRequest("Naam Hoofdthema", "Beschrijving Hoofdthema", 1, 1);

        http.perform(post("/api/hoofdthemas", objectMapper.writeValueAsString(hoofdthema)).header("Authorization", getUserToken()));

        http.perform(delete("/api/hoofdthemas/1").header("Authorization", getUserToken()))
            .andExpect(status().isOk());

        http.perform(get("/api/hoofdthemas").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void deleteHoofdthema_onbestaandHoofdthema() throws Exception
    {
        http.perform(delete("/api/hoofdthemas/1").header("Authorization", getUserToken()))
            .andExpect(status().isNotFound());
    }
}
