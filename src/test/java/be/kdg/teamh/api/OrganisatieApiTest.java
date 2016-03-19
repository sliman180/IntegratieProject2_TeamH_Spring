package be.kdg.teamh.api;

import be.kdg.teamh.dtos.request.OrganisatieRequest;
import org.junit.Test;
import org.springframework.web.util.NestedServletException;

import javax.servlet.ServletException;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OrganisatieApiTest extends ApiTest
{
    @Test
    public void indexOrganisatie() throws Exception
    {
        http.perform(get("/api/organisaties").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void createOrganisatie() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Naam Organisatie", "Beschrijving Organisatie", 1);

        http.perform(post("/api/organisaties", objectMapper.writeValueAsString(organisatie)).header("Authorization", getUserToken()))
            .andExpect(status().isCreated());

        http.perform(get("/api/organisaties").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void createOrganisatie_nullInput() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest(null, null, 0);

        http.perform(post("/api/organisaties", objectMapper.writeValueAsString(organisatie)).header("Authorization", getUserToken()))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void showOrganisatie() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Naam Organisatie", "Beschrijving Organisatie", 1);

        http.perform(post("/api/organisaties", objectMapper.writeValueAsString(organisatie)).header("Authorization", getUserToken()));

        http.perform(get("/api/organisaties/1").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.naam", is("Naam Organisatie")))
            .andExpect(jsonPath("$.beschrijving", is("Beschrijving Organisatie")));
    }

    @Test
    public void showOrganisatie_onbestaandeOrganisatie() throws Exception
    {
        http.perform(get("/api/organisaties/1").header("Authorization", getUserToken()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateOrganisatie() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Naam Organisatie", "Beschrijving Organisatie", 1);

        http.perform(post("/api/organisaties", objectMapper.writeValueAsString(organisatie)).header("Authorization", getUserToken()));

        organisatie = new OrganisatieRequest("Nieuwe Naam Organisatie", "Nieuwe Beschrijving Organisatie", 1);

        http.perform(put("/api/organisaties/1", objectMapper.writeValueAsString(organisatie)).header("Authorization", getUserToken()))
            .andExpect(status().isOk());

        http.perform(get("/api/organisaties/1").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.naam", is("Nieuwe Naam Organisatie")))
            .andExpect(jsonPath("$.beschrijving", is("Nieuwe Beschrijving Organisatie")));
    }

    @Test
    public void updateOrganisatie_nullInput() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Naam Organisatie", "Beschrijving Organisatie", 1);

        http.perform(post("/api/organisaties", objectMapper.writeValueAsString(organisatie)).header("Authorization", getUserToken()));

        organisatie = new OrganisatieRequest(null, null, 0);

        http.perform(put("/api/organisaties/1", objectMapper.writeValueAsString(organisatie)).header("Authorization", getUserToken()))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void updateOrganisatie_onbestaandeOrganisatie() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Naam Organisatie", "Beschrijving Organisatie", 1);

        http.perform(put("/api/organisaties/1", objectMapper.writeValueAsString(organisatie)).header("Authorization", getUserToken()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void deleteOrganisatie() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Naam Organisatie", "Beschrijving Organisatie", 1);

        http.perform(post("/api/organisaties", objectMapper.writeValueAsString(organisatie)).header("Authorization", getUserToken()));

        http.perform(delete("/api/organisaties/1").header("Authorization", getUserToken()))
            .andExpect(status().isOk());

        http.perform(get("/api/organisaties").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void deleteOrganisatie_onbestaandeOrganisatie() throws Exception
    {
        http.perform(delete("/api/organisaties/1").header("Authorization", getUserToken()))
            .andExpect(status().isNotFound());
    }
}
