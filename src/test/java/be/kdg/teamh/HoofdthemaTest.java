package be.kdg.teamh;

import be.kdg.teamh.entities.Hoofdthema;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.web.util.NestedServletException;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HoofdthemaTest extends ApiTest
{
    @Test
    public void indexHoofdthema() throws Exception
    {
        this.mvc.perform(get("/api/hoofdthemas").accept(MediaType.APPLICATION_JSON).header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void createHoofdthema() throws Exception
    {
        String json = objectMapper.writeValueAsString(new Hoofdthema("Voetbal", "Nieuw voetbalveld", null, null));

        this.mvc.perform(post("/api/hoofdthemas").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/api/hoofdthemas").accept(MediaType.APPLICATION_JSON).header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].naam", is("Voetbal")))
            .andExpect(jsonPath("$[0].beschrijving", is("Nieuw voetbalveld")));
    }

    @Test(expected = NestedServletException.class)
    public void createHoofdthema_nullInput() throws Exception
    {
        String json = objectMapper.writeValueAsString(new Hoofdthema(null, null, null, null));

        this.mvc.perform(post("/api/hoofdthemas").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()));
    }

    @Test
    public void showHoofdthema() throws Exception
    {
        String json = objectMapper.writeValueAsString(new Hoofdthema("Voetbal", "Nieuw voetbalveld", null, null));

        this.mvc.perform(post("/api/hoofdthemas").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/api/hoofdthemas/1").accept(MediaType.APPLICATION_JSON).header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.naam", is("Voetbal")))
            .andExpect(jsonPath("$.beschrijving", is("Nieuw voetbalveld")));
    }

    @Test(expected = NestedServletException.class)
    public void showHoofdthema_nonExistingHoofdthema() throws Exception
    {
        String json = objectMapper.writeValueAsString(new Hoofdthema("Voetbal", "Nieuw voetbalveld", null, null));

        this.mvc.perform(post("/api/hoofdthemas").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/api/hoofdthemas/2").accept(MediaType.APPLICATION_JSON).header("Authorization", getUserToken()));
    }

    @Test
    public void updateHoofdthema() throws Exception
    {
        String json = objectMapper.writeValueAsString(new Hoofdthema("Voetbal", "Nieuw voetbalveld", null, null));

        this.mvc.perform(post("/api/hoofdthemas").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        json = objectMapper.writeValueAsString(new Hoofdthema("Voetbal", "Vernieuwd voetbalveld", null, null));

        this.mvc.perform(put("/api/hoofdthemas/1").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isOk());

        this.mvc.perform(get("/api/hoofdthemas/1").accept(MediaType.APPLICATION_JSON).header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.naam", is("Voetbal")))
            .andExpect(jsonPath("$.beschrijving", is("Vernieuwd voetbalveld")));
    }

    @Test(expected = NestedServletException.class)
    public void updateHoofdthema_nullInput() throws Exception
    {
        String json = objectMapper.writeValueAsString(new Hoofdthema("Voetbal", "Nieuw voetbalveld", null, null));

        this.mvc.perform(post("/api/hoofdthemas").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        json = objectMapper.writeValueAsString(new Hoofdthema(null, null, null, null));

        this.mvc.perform(put("/api/hoofdthemas/1").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()));
    }

    @Test(expected = NestedServletException.class)
    public void updateHoofdthema_nonExistingHoofdthema() throws Exception
    {
        String json = objectMapper.writeValueAsString(new Hoofdthema("Voetbal", "Nieuw voetbalveld", null, null));

        this.mvc.perform(post("/api/hoofdthemas").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        json = objectMapper.writeValueAsString(new Hoofdthema("Voetbal", "Vernieuwd voetbalveld", null, null));

        this.mvc.perform(put("/api/hoofdthemas/2").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()));
    }

    @Test
    public void deleteHoofdthema() throws Exception
    {
        String json = objectMapper.writeValueAsString(new Hoofdthema("Voetbal", "Nieuw voetbalveld", null, null));

        this.mvc.perform(post("/api/hoofdthemas").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.mvc.perform(delete("/api/hoofdthemas/1").header("Authorization", getAdminToken()))
            .andExpect(status().isOk());

        this.mvc.perform(get("/api/hoofdthemas").accept(MediaType.APPLICATION_JSON).header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test(expected = NestedServletException.class)
    public void deleteHoofdthema_nonExistingHoofdthema() throws Exception
    {
        String json = objectMapper.writeValueAsString(new Hoofdthema("Voetbal", "Nieuw voetbalveld", null, null));

        this.mvc.perform(post("/api/hoofdthemas").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.mvc.perform(delete("/api/hoofdthemas/2").header("Authorization", getAdminToken()));
    }
}
