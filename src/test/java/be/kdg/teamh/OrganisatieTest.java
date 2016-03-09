package be.kdg.teamh;

import be.kdg.teamh.entities.Organisatie;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.web.util.NestedServletException;

import javax.servlet.ServletException;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OrganisatieTest extends ApiTest
{
    @Test
    public void indexOrganisatie() throws Exception
    {
        this.mvc.perform(get("/api/organisaties").accept(MediaType.APPLICATION_JSON).header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void createOrganisatie() throws Exception
    {
        String json = objectMapper.writeValueAsString(new Organisatie("NaamOrganisatie", "Beschrijving", null));

        this.mvc.perform(post("/api/organisaties").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/api/organisaties").contentType(MediaType.APPLICATION_JSON).header("Authorization", getAdminToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].naam", is("NaamOrganisatie")))
            .andExpect(jsonPath("$[0].beschrijving", is("Beschrijving")));
    }

    @Test(expected = NestedServletException.class)
    public void createOrganisatie_nullInput() throws Exception
    {
        String json = objectMapper.writeValueAsString(new Organisatie(null, null, null));

        this.mvc.perform(post("/api/organisaties").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()));
    }

    @Test(expected = ServletException.class)
    public void createOrganisatie_wrongCredentials() throws Exception
    {
        String json = objectMapper.writeValueAsString(new Organisatie("", "", null));

        this.mvc.perform(post("/api/organisaties").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getTokenAsInexistent()))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void showOrganisatie() throws Exception
    {
        String json = objectMapper.writeValueAsString(new Organisatie("NaamOrganisatie", "Beschrijving", null));

        this.mvc.perform(post("/api/organisaties").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/api/organisaties/1").contentType(MediaType.APPLICATION_JSON_UTF8).header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.naam", is("NaamOrganisatie")))
            .andExpect(jsonPath("$.beschrijving", is("Beschrijving")));
    }

    @Test(expected = ServletException.class)
    public void showOrganisatie_wrongCredentials() throws Exception
    {
        String json = objectMapper.writeValueAsString(new Organisatie("NaamOrganisatie", "Beschrijving", null));

        this.mvc.perform(post("/api/organisaties").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/api/organisaties/1").contentType(MediaType.APPLICATION_JSON_UTF8).header("Authorization", getTokenAsInexistent()));
    }

    @Test
    public void updateOrganisatie() throws Exception
    {
        String json = objectMapper.writeValueAsString(new Organisatie("NaamOrganisatie", "Beschrijving", null));

        this.mvc.perform(post("/api/organisaties").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        json = objectMapper.writeValueAsString(new Organisatie("NieuweNaamOrganisatie", "Beschrijving", null));

        this.mvc.perform(put("/api/organisaties/1").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isOk());

        this.mvc.perform(get("/api/organisaties/1").contentType(MediaType.APPLICATION_JSON_UTF8).header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.naam", is("NieuweNaamOrganisatie")))
            .andExpect(jsonPath("$.beschrijving", is("Beschrijving")));
    }

    @Test(expected = NestedServletException.class)
    public void updateOrganisatie_nullInput() throws Exception
    {
        String json = objectMapper.writeValueAsString(new Organisatie(null, null, null));

        this.mvc.perform(post("/api/organisaties").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()));
    }

    @Test(expected = NestedServletException.class)
    public void updateOrganisatie_nonExistingOrganisatie() throws Exception
    {
        String json = objectMapper.writeValueAsString(new Organisatie("Organisatie", "KdG", null));

        this.mvc.perform(post("/api/organisaties").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()));

        json = objectMapper.writeValueAsString(new Organisatie("NieuweOrganisatie", "KdG", null));

        this.mvc.perform(put("/api/organisaties/2").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()));
    }

    @Test(expected = NestedServletException.class)
    public void updateOrganisatie_wrongRole() throws Exception
    {
        String json = objectMapper.writeValueAsString(new Organisatie("Organisatie", "KdG", null));

        this.mvc.perform(post("/api/organisaties").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.mvc.perform(put("/api/organisaties/1").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getUserToken()))
            .andExpect(status().isForbidden());
    }

    @Test(expected = ServletException.class)
    public void updateOrganisatie_wrongCredentials() throws Exception
    {
        String json = objectMapper.writeValueAsString(new Organisatie("Organisatie", "KdG", null));

        this.mvc.perform(post("/api/organisaties").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        json = objectMapper.writeValueAsString(new Organisatie("NieuweNaamOrganisatie", "Beschrijving", null));

        this.mvc.perform(put("/api/organisaties/1").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getTokenAsInexistent()))
            .andExpect(status().isUnauthorized());
    }

//    @Test
//    public void deleteOrganisatie() throws Exception
//    {
//        String json = objectMapper.writeValueAsString(new Organisatie("teVerwijderenOrganisatie", "Beschrijving", null));
//
//        this.mvc.perform(post("/api/organisaties").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getUserToken()))
//            .andExpect(status().isCreated());
//
//        this.mvc.perform(get("/api/organisaties/1").header("Authorization", getUserToken()))
//                .andExpect(status().isOk());
//
//        this.mvc.perform(delete("/api/organisaties/1").header("Authorization", getUserToken()))
//            .andExpect(status().isOk());
//
//        this.mvc.perform(get("/api/organisaties").accept(MediaType.APPLICATION_JSON).header("Authorization", getUserToken()))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$", hasSize(0)));
//    }

    @Test(expected = NestedServletException.class)
    public void deleteOrganisatie_nonExistingOrganisatie() throws Exception
    {
        String json = objectMapper.writeValueAsString(new Organisatie("KdG Organisatie", "KdG", null));

        this.mvc.perform(post("/api/organisaties").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getUserToken()))
            .andExpect(status().isCreated());

        this.mvc.perform(delete("/api/organisaties/2").header("Authorization", getAdminToken()));
    }


    @Test(expected = ServletException.class)
    public void deleteOrganisatie_wrongCredentials() throws Exception
    {
        String json = objectMapper.writeValueAsString(new Organisatie("Organisatie", "KdG", null));

        this.mvc.perform(post("/api/organisaties").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.mvc.perform(delete("/api/organisaties/1").header("Authorization", getTokenAsInexistent()))
            .andExpect(status().isUnauthorized());
    }
}
