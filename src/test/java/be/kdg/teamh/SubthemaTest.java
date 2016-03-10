package be.kdg.teamh;

import be.kdg.teamh.entities.Subthema;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.web.util.NestedServletException;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SubthemaTest extends ApiTest
{
    @Test
    public void indexSubthema() throws Exception
    {
        this.mvc.perform(get("/api/subthemas").accept(MediaType.APPLICATION_JSON).header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void createSubthema() throws Exception
    {
        String json = objectMapper.writeValueAsString(new Subthema("Houffalize", "Route 6", null));

        this.mvc.perform(post("/api/subthemas").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/api/subthemas").contentType(MediaType.APPLICATION_JSON).header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].naam", is("Houffalize")))
            .andExpect(jsonPath("$[0].beschrijving", is("Route 6")));
    }

    @Test(expected = NestedServletException.class)
    public void createSubthema_nullInput() throws Exception
    {
        String json = objectMapper.writeValueAsString(new Subthema(null, null, null));

        this.mvc.perform(post("/api/subthemas").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getUserToken()));
    }

    @Test
    public void showSubthema() throws Exception
    {
        String json = objectMapper.writeValueAsString(new Subthema("Houffalize", "Route 6", null));

        this.mvc.perform(post("/api/subthemas").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/api/subthemas/1").contentType(MediaType.APPLICATION_JSON).header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.naam", is("Houffalize")))
            .andExpect(jsonPath("$.beschrijving", is("Route 6")));
    }

    @Test(expected = NestedServletException.class)
    public void showSubthema_nonExistingSubthema() throws Exception
    {
        String json = objectMapper.writeValueAsString(new Subthema("Houffalize", "Route 6", null));

        this.mvc.perform(post("/api/subthemas").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/api/subthemas/2").accept(MediaType.APPLICATION_JSON).header("Authorization", getUserToken()));
    }

    @Test
    public void updateSubthema() throws Exception
    {
        String json = objectMapper.writeValueAsString(new Subthema("Houffalize", "Route 6", null));

        this.mvc.perform(post("/api/subthemas").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        json = objectMapper.writeValueAsString(new Subthema("Houffalize", "Route 3", null));

        this.mvc.perform(put("/api/subthemas/1").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isOk());

        this.mvc.perform(get("/api/subthemas/1").contentType(MediaType.APPLICATION_JSON).header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.naam", is("Houffalize")))
            .andExpect(jsonPath("$.beschrijving", is("Route 3")));
    }

    @Test(expected = NestedServletException.class)
    public void updateHoofdthema_nullInput() throws Exception
    {
        String json = objectMapper.writeValueAsString(new Subthema("Houffalize", "Route 6", null));

        this.mvc.perform(post("/api/subthemas").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        json = objectMapper.writeValueAsString(new Subthema(null, null, null));

        this.mvc.perform(put("/api/subthemas/1").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()));
    }

    @Test(expected = NestedServletException.class)
    public void updateSubthema_nonExistingSubthema() throws Exception
    {
        String json = objectMapper.writeValueAsString(new Subthema("Houffalize", "Route 6", null));

        this.mvc.perform(post("/api/subthemas").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        json = objectMapper.writeValueAsString(new Subthema("Houffalize", "Route 3", null));

        this.mvc.perform(put("/api/subthemas/2").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()));
    }

    @Test
    public void deleteSubthema() throws Exception
    {
        String json = objectMapper.writeValueAsString(new Subthema("Houffalize", "Route 6", null));

        this.mvc.perform(post("/api/subthemas").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.mvc.perform(delete("/api/subthemas/1").header("Authorization", getAdminToken()))
            .andExpect(status().isOk());

        this.mvc.perform(get("/api/subthemas").accept(MediaType.APPLICATION_JSON).header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test(expected = NestedServletException.class)
    public void deleteSubthema_nonExistingSubthema() throws Exception
    {
        String json = objectMapper.writeValueAsString(new Subthema("Houffalize", "Route 6", null));

        this.mvc.perform(post("/api/subthemas").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.mvc.perform(delete("/api/subthemas/2").header("Authorization", getAdminToken()));
    }
}
