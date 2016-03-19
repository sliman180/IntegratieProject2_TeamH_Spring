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

        http.perform(post("/api/gebruikers", objectMapper.writeValueAsString(gebruiker)).header("Authorization", getUserToken()))
            .andExpect(status().isCreated());

        http.perform(get("/api/gebruikers").contentType(MediaType.APPLICATION_JSON).header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    public void createGebruiker_nullInput() throws Exception
    {
        GebruikerRequest gebruiker = new GebruikerRequest(null, null);

        http.perform(post("/api/gebruikers", objectMapper.writeValueAsString(gebruiker)).header("Authorization", getUserToken()))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void showGebruiker() throws Exception
    {
        GebruikerRequest gebruiker = new GebruikerRequest("testuser", "testuser");

        http.perform(post("/api/gebruikers", objectMapper.writeValueAsString(gebruiker)).header("Authorization", getUserToken()));

        http.perform(get("/api/gebruikers/4").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.gebruikersnaam", is("testuser")));
    }

    @Test
    public void showGebruiker_onbestaandeGebruiker() throws Exception
    {
        http.perform(get("/api/gebruikers/4").header("Authorization", getUserToken()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateGebruiker() throws Exception
    {
        GebruikerRequest gebruiker = new GebruikerRequest("testuser", "testuser");

        http.perform(post("/api/gebruikers", objectMapper.writeValueAsString(gebruiker)).header("Authorization", getUserToken()));

        gebruiker = new GebruikerRequest("newuser", "newuser");

        http.perform(put("/api/gebruikers/4", objectMapper.writeValueAsString(gebruiker)).header("Authorization", getUserToken()))
            .andExpect(status().isOk());

        http.perform(get("/api/gebruikers/4").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.gebruikersnaam", is("newuser")));
    }

    @Test
    public void updateGebruiker_nullInput() throws Exception
    {
        GebruikerRequest gebruiker = new GebruikerRequest(null, null);

        http.perform(put("/api/gebruikers/4", objectMapper.writeValueAsString(gebruiker)).header("Authorization", getUserToken()))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void updateGebruiker_onbestaandeGebruiker() throws Exception
    {
        GebruikerRequest gebruiker = new GebruikerRequest("testuser", "testuser");

        http.perform(put("/api/gebruikers/4", objectMapper.writeValueAsString(gebruiker)).header("Authorization", getUserToken()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void deleteGebruiker() throws Exception
    {
        GebruikerRequest gebruiker = new GebruikerRequest("testuser", "testuser");

        http.perform(post("/api/gebruikers", objectMapper.writeValueAsString(gebruiker)).header("Authorization", getUserToken()));

        http.perform(delete("/api/gebruikers/4").header("Authorization", getUserToken()))
            .andExpect(status().isOk());

        http.perform(get("/api/gebruikers").header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void deleteGebruiker_onbestaandeGebruiker() throws Exception
    {
        http.perform(delete("/api/gebruikers/4").header("Authorization", getUserToken()))
            .andExpect(status().isNotFound());
    }
}
