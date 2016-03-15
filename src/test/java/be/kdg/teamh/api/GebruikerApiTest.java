package be.kdg.teamh.api;

import be.kdg.teamh.dtos.request.GebruikerRequest;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.web.util.NestedServletException;

import javax.servlet.ServletException;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GebruikerApiTest extends ApiTest
{
    @Test
    public void indexGebruiker() throws Exception
    {
        http.perform(get("/api/gebruikers").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void createGebruiker() throws Exception
    {
        GebruikerRequest gebruiker = new GebruikerRequest("testuser", "testuser");
        String json = objectMapper.writeValueAsString(gebruiker);

        http.perform(post("/api/gebruikers", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        http.perform(get("/api/gebruikers").contentType(MediaType.APPLICATION_JSON).header("Authorization", getAdminToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(4)))
            .andExpect(jsonPath("$[3].gebruikersnaam", is("testuser")));
    }

    @Test(expected = NestedServletException.class)
    public void createGebruiker_nullInput() throws Exception
    {
        GebruikerRequest gebruiker = new GebruikerRequest(null, null);
        String json = objectMapper.writeValueAsString(gebruiker);

        http.perform(post("/api/gebruikers", json).header("Authorization", getAdminToken()));
    }

    @Test(expected = ServletException.class)
    public void createGebruiker_wrongCredentials() throws Exception
    {
        GebruikerRequest gebruiker = new GebruikerRequest("testuser", "testuser");
        String json = objectMapper.writeValueAsString(gebruiker);

        http.perform(post("/api/gebruikers", json).header("Authorization", getNonExistingUserToken()))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void showGebruiker() throws Exception
    {
        GebruikerRequest gebruiker = new GebruikerRequest("testuser", "testuser");
        String json = objectMapper.writeValueAsString(gebruiker);

        http.perform(post("/api/gebruikers", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        http.perform(get("/api/gebruikers/4").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.gebruikersnaam", is("testuser")));
    }

    @Test(expected = ServletException.class)
    public void showGebruiker_wrongCredentials() throws Exception
    {
        GebruikerRequest gebruiker = new GebruikerRequest("testuser", "testuser");
        String json = objectMapper.writeValueAsString(gebruiker);

        http.perform(post("/api/gebruikers", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        http.perform(get("/api/gebruikers/4").header("Authorization", getNonExistingUserToken()));
    }

    @Test
    public void updateGebruiker() throws Exception
    {
        GebruikerRequest gebruiker = new GebruikerRequest("testuser", "testuser");
        String json = objectMapper.writeValueAsString(gebruiker);

        http.perform(post("/api/gebruikers", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        gebruiker = new GebruikerRequest("newuser", "newuser");
        json = objectMapper.writeValueAsString(gebruiker);

        http.perform(put("/api/gebruikers/4", json).header("Authorization", getAdminToken()))
            .andExpect(status().isOk());

        http.perform(get("/api/gebruikers/4").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.gebruikersnaam", is("newuser")));
    }

    @Test(expected = NestedServletException.class)
    public void updateGebruiker_nullInput() throws Exception
    {
        GebruikerRequest gebruiker = new GebruikerRequest("testuser", "testuser");
        String json = objectMapper.writeValueAsString(gebruiker);

        http.perform(post("/api/gebruikers", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        gebruiker = new GebruikerRequest(null, null);
        json = objectMapper.writeValueAsString(gebruiker);

        http.perform(put("/api/gebruikers/4", json).header("Authorization", getAdminToken()));
    }

    @Test(expected = NestedServletException.class)
    public void updateGebruiker_nonExistingGebruiker() throws Exception
    {
        GebruikerRequest gebruiker = new GebruikerRequest("newuser", "newuser");
        String json = objectMapper.writeValueAsString(gebruiker);

        http.perform(put("/api/gebruikers/4", json).header("Authorization", getAdminToken()));
    }

    @Test(expected = ServletException.class)
    public void updateGebruiker_wrongCredentials() throws Exception
    {
        GebruikerRequest gebruiker = new GebruikerRequest("testuser", "testuser");
        String json = objectMapper.writeValueAsString(gebruiker);

        http.perform(post("/api/gebruikers", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        gebruiker = new GebruikerRequest("newuser", "newuser");
        json = objectMapper.writeValueAsString(gebruiker);

        http.perform(put("/api/gebruikers/4", json).header("Authorization", getNonExistingUserToken()))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void deleteGebruiker() throws Exception
    {
        GebruikerRequest gebruiker = new GebruikerRequest("testuser", "testuser");
        String json = objectMapper.writeValueAsString(gebruiker);

        http.perform(post("/api/gebruikers", json).header("Authorization", getUserToken()))
            .andExpect(status().isCreated());

        http.perform(get("/api/gebruikers").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(4)));

        http.perform(delete("/api/gebruikers/4").header("Authorization", getUserToken()))
            .andExpect(status().isOk());

        http.perform(get("/api/gebruikers").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test(expected = NestedServletException.class)
    public void deleteGebruiker_nonExistingGebruiker() throws Exception
    {
        http.perform(delete("/api/gebruikers/4").header("Authorization", getAdminToken()));
    }


    @Test(expected = ServletException.class)
    public void deleteGebruiker_wrongCredentials() throws Exception
    {
        GebruikerRequest gebruiker = new GebruikerRequest("testuser", "testuser");
        String json = objectMapper.writeValueAsString(gebruiker);

        http.perform(post("/api/gebruikers", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        http.perform(delete("/api/gebruikers/4").header("Authorization", getNonExistingUserToken()))
            .andExpect(status().isUnauthorized());
    }
}
