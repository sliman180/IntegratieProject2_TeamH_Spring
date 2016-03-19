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
        OrganisatieRequest organisatie = new OrganisatieRequest("Naam Organisatie", "Beschrijving", 1);
        String json = objectMapper.writeValueAsString(organisatie);

        http.perform(post("/api/organisaties", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        http.perform(get("/api/organisaties").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].naam", is("Naam Organisatie")))
            .andExpect(jsonPath("$[0].beschrijving", is("Beschrijving")));
    }

    @Test(expected = NestedServletException.class)
    public void createOrganisatie_nullInput() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest(null, null, 0);
        String json = objectMapper.writeValueAsString(organisatie);

        http.perform(post("/api/organisaties", json).header("Authorization", getAdminToken()));
    }

    @Test(expected = ServletException.class)
    public void createOrganisatie_wrongCredentials() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Naam Organisatie", "Beschrijving", 1);
        String json = objectMapper.writeValueAsString(organisatie);

        http.perform(post("/api/organisaties", json).header("Authorization", getNonExistingUserToken()))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void showOrganisatie() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Naam Organisatie", "Beschrijving", 1);
        String json = objectMapper.writeValueAsString(organisatie);

        http.perform(post("/api/organisaties", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        http.perform(get("/api/organisaties/1").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.naam", is("Naam Organisatie")))
            .andExpect(jsonPath("$.beschrijving", is("Beschrijving")));
    }

    @Test(expected = ServletException.class)
    public void showOrganisatie_wrongCredentials() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Naam Organisatie", "Beschrijving", 1);
        String json = objectMapper.writeValueAsString(organisatie);

        http.perform(post("/api/organisaties", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        http.perform(get("/api/organisaties/1").header("Authorization", getNonExistingUserToken()));
    }

    @Test
    public void updateOrganisatie() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Naam Organisatie", "Beschrijving", 1);
        String json = objectMapper.writeValueAsString(organisatie);

        http.perform(post("/api/organisaties", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        organisatie = new OrganisatieRequest("Nieuwe Naam Organisatie", "Beschrijving", 1);
        json = objectMapper.writeValueAsString(organisatie);

        http.perform(put("/api/organisaties/1", json).header("Authorization", getAdminToken()))
            .andExpect(status().isOk());

        http.perform(get("/api/organisaties/1").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.naam", is("Nieuwe Naam Organisatie")))
            .andExpect(jsonPath("$.beschrijving", is("Beschrijving")));
    }

    @Test(expected = NestedServletException.class)
    public void updateOrganisatie_nullInput() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Naam Organisatie", "Beschrijving", 1);
        String json = objectMapper.writeValueAsString(organisatie);

        http.perform(post("/api/organisaties", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        organisatie = new OrganisatieRequest(null, null, 0);
        json = objectMapper.writeValueAsString(organisatie);

        http.perform(put("/api/organisaties/1", json).header("Authorization", getAdminToken()));
    }

    @Test(expected = NestedServletException.class)
    public void updateOrganisatie_nonExistingOrganisatie() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Organisatie", "KdG", 1);
        String json = objectMapper.writeValueAsString(organisatie);

        http.perform(post("/api/organisaties", json).header("Authorization", getAdminToken()));

        organisatie = new OrganisatieRequest("NieuweOrganisatie", "KdG", 1);
        json = objectMapper.writeValueAsString(organisatie);

        http.perform(put("/api/organisaties/2", json).header("Authorization", getAdminToken()));
    }

    @Test(expected = NestedServletException.class)
    public void updateOrganisatie_wrongRole() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Organisatie", "KdG", 1);
        String json = objectMapper.writeValueAsString(organisatie);

        http.perform(post("/api/organisaties", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        http.perform(put("/api/organisaties/1", json).header("Authorization", getUserToken()))
            .andExpect(status().isForbidden());
    }

    @Test(expected = ServletException.class)
    public void updateOrganisatie_wrongCredentials() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Organisatie", "KdG", 1);
        String json = objectMapper.writeValueAsString(organisatie);

        http.perform(post("/api/organisaties", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        organisatie = new OrganisatieRequest("Nieuwe Naam Organisatie", "Beschrijving", 1);
        json = objectMapper.writeValueAsString(organisatie);

        http.perform(put("/api/organisaties/1", json).header("Authorization", getNonExistingUserToken()))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void deleteOrganisatie() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Naam Organisatie", "Beschrijving", 1);
        String json = objectMapper.writeValueAsString(organisatie);

        http.perform(post("/api/organisaties", json).header("Authorization", getUserToken()))
            .andExpect(status().isCreated());

        http.perform(delete("/api/organisaties/1").header("Authorization", getAdminToken()))
            .andExpect(status().isOk());

        http.perform(get("/api/organisaties").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test(expected = NestedServletException.class)
    public void deleteOrganisatie_nonExistingOrganisatie() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("KdG Organisatie", "KdG", 1);
        String json = objectMapper.writeValueAsString(organisatie);

        http.perform(post("/api/organisaties", json).header("Authorization", getUserToken()))
            .andExpect(status().isCreated());

        http.perform(delete("/api/organisaties/2").header("Authorization", getAdminToken()));
    }

    @Test(expected = ServletException.class)
    public void deleteOrganisatie_wrongCredentials() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Organisatie", "KdG", 1);
        String json = objectMapper.writeValueAsString(organisatie);

        http.perform(post("/api/organisaties", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        http.perform(delete("/api/organisaties/1").header("Authorization", getNonExistingUserToken()))
            .andExpect(status().isUnauthorized());
    }
}
