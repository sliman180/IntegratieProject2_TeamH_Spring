package be.kdg.teamh.api;

import be.kdg.teamh.dtos.request.OrganisatieRequest;
import org.junit.Test;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OrganisatieApiTest extends ApiTest
{
    @Test
    public void indexOrganisatie() throws Exception
    {
        http.perform(get("/api/organisaties").header("Authorization", getUserOneToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void createOrganisatie() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Naam Organisatie", "Beschrijving Organisatie", 1);

        http.perform(post("/api/organisaties", objectMapper.writeValueAsString(organisatie)).header("Authorization", getUserOneToken()))
            .andExpect(status().isCreated());

        http.perform(get("/api/organisaties").header("Authorization", getUserOneToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void createOrganisatie_nullInput() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest(null, null, 0);

        http.perform(post("/api/organisaties", objectMapper.writeValueAsString(organisatie)).header("Authorization", getUserOneToken()))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void createOrganisatie_zonderAuthenticationHeader() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Naam Organisatie", "Beschrijving Organisatie", 1);

        http.perform(post("/api/organisaties", objectMapper.writeValueAsString(organisatie)))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void createOrganisatie_ongeregistreerdeGebruiker() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Naam Organisatie", "Beschrijving Organisatie", 1);

        http.perform(post("/api/organisaties", objectMapper.writeValueAsString(organisatie)).header("Authorization", getNonExistingUserToken()))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void showOrganisatie() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Naam Organisatie", "Beschrijving Organisatie", 1);

        http.perform(post("/api/organisaties", objectMapper.writeValueAsString(organisatie)).header("Authorization", getUserOneToken()));

        http.perform(get("/api/organisaties/1").header("Authorization", getUserOneToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.naam", is("Naam Organisatie")))
            .andExpect(jsonPath("$.beschrijving", is("Beschrijving Organisatie")));
    }

    @Test
    public void showOrganisatie_onbestaandeOrganisatie() throws Exception
    {
        http.perform(get("/api/organisaties/1").header("Authorization", getUserOneToken()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateOrganisatie() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Naam Organisatie", "Beschrijving Organisatie", 1);

        http.perform(post("/api/organisaties", objectMapper.writeValueAsString(organisatie)).header("Authorization", getUserOneToken()));

        organisatie = new OrganisatieRequest("Nieuwe Naam Organisatie", "Nieuwe Beschrijving Organisatie", 1);

        http.perform(put("/api/organisaties/1", objectMapper.writeValueAsString(organisatie)).header("Authorization", getUserOneToken()))
            .andExpect(status().isOk());

        http.perform(get("/api/organisaties/1").header("Authorization", getUserOneToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.naam", is("Nieuwe Naam Organisatie")))
            .andExpect(jsonPath("$.beschrijving", is("Nieuwe Beschrijving Organisatie")));
    }

    @Test
    public void updateOrganisatie_nullInput() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Naam Organisatie", "Beschrijving Organisatie", 1);

        http.perform(post("/api/organisaties", objectMapper.writeValueAsString(organisatie)).header("Authorization", getUserOneToken()));

        organisatie = new OrganisatieRequest(null, null, 0);

        http.perform(put("/api/organisaties/1", objectMapper.writeValueAsString(organisatie)).header("Authorization", getUserOneToken()))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void updateOrganisatie_onbestaandeOrganisatie() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Naam Organisatie", "Beschrijving Organisatie", 1);

        http.perform(put("/api/organisaties/1", objectMapper.writeValueAsString(organisatie)).header("Authorization", getUserOneToken()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateOrganisatie_zonderAuthenticationHeader() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Naam Organisatie", "Beschrijving Organisatie", 1);

        http.perform(post("/api/organisaties", objectMapper.writeValueAsString(organisatie)).header("Authorization", getUserOneToken()));

        organisatie = new OrganisatieRequest("Nieuwe Naam Organisatie", "Nieuwe Beschrijving Organisatie", 1);

        http.perform(put("/api/organisaties/1", objectMapper.writeValueAsString(organisatie)))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void updateOrganisatie_ongeregistreerdeGebruiker() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Naam Organisatie", "Beschrijving Organisatie", 1);

        http.perform(post("/api/organisaties", objectMapper.writeValueAsString(organisatie)).header("Authorization", getUserOneToken()));

        organisatie = new OrganisatieRequest("Nieuwe Naam Organisatie", "Nieuwe Beschrijving Organisatie", 1);

        http.perform(put("/api/organisaties/1", objectMapper.writeValueAsString(organisatie)).header("Authorization", getNonExistingUserToken()))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void updateOrganisatie_verkeerdeGebruiker() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Naam Organisatie", "Beschrijving Organisatie", 1);

        http.perform(post("/api/organisaties", objectMapper.writeValueAsString(organisatie)).header("Authorization", getUserOneToken()));

        organisatie = new OrganisatieRequest("Nieuwe Naam Organisatie", "Nieuwe Beschrijving Organisatie", 1);

        http.perform(put("/api/organisaties/1", objectMapper.writeValueAsString(organisatie)).header("Authorization", getUserTwoToken()))
            .andExpect(status().isForbidden());
    }

    @Test
    public void deleteOrganisatie() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Naam Organisatie", "Beschrijving Organisatie", 1);

        http.perform(post("/api/organisaties", objectMapper.writeValueAsString(organisatie)).header("Authorization", getUserOneToken()));

        http.perform(delete("/api/organisaties/1").header("Authorization", getUserOneToken()))
            .andExpect(status().isOk());

        http.perform(get("/api/organisaties").header("Authorization", getUserOneToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void deleteOrganisatie_onbestaandeOrganisatie() throws Exception
    {
        http.perform(delete("/api/organisaties/1").header("Authorization", getUserOneToken()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void deleteOrganisatie_zonderAuthenticationHeader() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Naam Organisatie", "Beschrijving Organisatie", 1);

        http.perform(post("/api/organisaties", objectMapper.writeValueAsString(organisatie)).header("Authorization", getUserOneToken()));

        http.perform(delete("/api/organisaties/1"))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteOrganisatie_ongeregistreerdeGebruiker() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Naam Organisatie", "Beschrijving Organisatie", 1);

        http.perform(post("/api/organisaties", objectMapper.writeValueAsString(organisatie)).header("Authorization", getUserOneToken()));

        http.perform(delete("/api/organisaties/1").header("Authorization", getNonExistingUserToken()))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void deleteOrganisatie_verkeerdeGebruiker() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Naam Organisatie", "Beschrijving Organisatie", 1);

        http.perform(post("/api/organisaties", objectMapper.writeValueAsString(organisatie)).header("Authorization", getUserOneToken()));

        http.perform(delete("/api/organisaties/1").header("Authorization", getUserTwoToken()))
            .andExpect(status().isForbidden());
    }
}
