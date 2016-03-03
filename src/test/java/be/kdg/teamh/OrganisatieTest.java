package be.kdg.teamh;

import be.kdg.teamh.configuration.JwtFilter;
import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.dto.Token;
import be.kdg.teamh.entities.Organisatie;
import be.kdg.teamh.entities.Rol;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class OrganisatieTest
{
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private Gson gson;

    @Mock
    private Gebruiker gebruiker;

    @Before
    public void setUp() throws Exception
    {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).addFilter(new JwtFilter(), "/api/organisaties/*").build();
    }

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
        String json = gson.toJson(new Organisatie("NaamOrganisatie", "Beschrijving", gebruiker));

        this.mvc.perform(post("/api/organisaties").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/api/organisaties").contentType(MediaType.APPLICATION_JSON).header("Authorization", getAdminToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].id", is(1)))
            .andExpect(jsonPath("$[0].naam", is("NaamOrganisatie")))
            .andExpect(jsonPath("$[0].beschrijving", is("Beschrijving")));
    }

    @Test(expected = NestedServletException.class)
    public void createOrganisatie_nullInput() throws Exception
    {
        String json = gson.toJson(new Organisatie(null, null, gebruiker));

        this.mvc.perform(post("/api/organisaties").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()));
    }

    @Test(expected = NestedServletException.class)
    public void createOrganisatie_wrongRole() throws Exception
    {
        String json = gson.toJson(new Organisatie("", "", gebruiker));

        this.mvc.perform(post("/api/organisaties").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getUserToken()))
            .andExpect(status().isForbidden());
    }

    @Test(expected = ServletException.class)
    public void createOrganisatie_wrongCredentials() throws Exception
    {
        String json = gson.toJson(new Organisatie("", "", gebruiker));

        this.mvc.perform(post("/api/organisaties").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getTokenAsInexistent()))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void showOrganisatie() throws Exception
    {
        String json = gson.toJson(new Organisatie("NaamOrganisatie", "Beschrijving", gebruiker));

        this.mvc.perform(post("/api/organisaties").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/api/organisaties/1").contentType(MediaType.APPLICATION_JSON_UTF8).header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.naam", is("NaamOrganisatie")))
            .andExpect(jsonPath("$.beschrijving", is("Beschrijving")));
    }

    @Test(expected = ServletException.class)
    public void showOrganisatie_wrongCredentials() throws Exception
    {
        String json = gson.toJson(new Organisatie("NaamOrganisatie", "Beschrijving", gebruiker));

        this.mvc.perform(post("/api/organisaties").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/api/organisaties/1").contentType(MediaType.APPLICATION_JSON_UTF8).header("Authorization", getTokenAsInexistent()));
    }

    @Test
    public void updateOrganisatie() throws Exception
    {
        String json = gson.toJson(new Organisatie("NaamOrganisatie", "Beschrijving", gebruiker));

        this.mvc.perform(post("/api/organisaties").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        json = gson.toJson(new Organisatie("NieuweNaamOrganisatie", "Beschrijving", gebruiker));

        this.mvc.perform(put("/api/organisaties/1").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isOk());

        this.mvc.perform(get("/api/organisaties/1").contentType(MediaType.APPLICATION_JSON_UTF8).header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.naam", is("NieuweNaamOrganisatie")))
            .andExpect(jsonPath("$.beschrijving", is("Beschrijving")));
    }

    @Test(expected = NestedServletException.class)
    public void updateOrganisatie_nullInput() throws Exception
    {
        String json = gson.toJson(new Organisatie(null, null, gebruiker));

        this.mvc.perform(post("/api/organisaties").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()));
    }

    @Test(expected = NestedServletException.class)
    public void updateOrganisatie_nonExistingOrganisatie() throws Exception
    {
        String json = gson.toJson(new Organisatie("Organisatie", "KdG", gebruiker));

        this.mvc.perform(post("/api/organisaties").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()));

        json = gson.toJson(new Organisatie("NieuweOrganisatie", "KdG", gebruiker));

        this.mvc.perform(put("/api/organisaties/2").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()));
    }

    @Test(expected = NestedServletException.class)
    public void updateOrganisatie_wrongRole() throws Exception
    {
        String json = gson.toJson(new Organisatie("Organisatie", "KdG", gebruiker));

        this.mvc.perform(post("/api/organisaties").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.mvc.perform(put("/api/organisaties/1").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getUserToken()))
            .andExpect(status().isForbidden());
    }

    @Test(expected = ServletException.class)
    public void updateOrganisatie_wrongCredentials() throws Exception
    {
        String json = gson.toJson(new Organisatie("Organisatie", "KdG", gebruiker));

        this.mvc.perform(post("/api/organisaties").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        json = gson.toJson(new Organisatie("NieuweNaamOrganisatie", "Beschrijving", gebruiker));

        this.mvc.perform(put("/api/organisaties/1").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getTokenAsInexistent()))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void deleteOrganisatie() throws Exception
    {
        String json = gson.toJson(new Organisatie("teVerwijderenOrganisatie", "Beschrijving", gebruiker));

        this.mvc.perform(post("/api/organisaties").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/api/organisaties/1").header("Authorization", getAdminToken()))
            .andExpect(status().isOk());

        this.mvc.perform(delete("/api/organisaties/1").header("Authorization", getAdminToken()))
            .andExpect(status().isOk());

        this.mvc.perform(get("/api/organisaties").accept(MediaType.APPLICATION_JSON).header("Authorization", getAdminToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test(expected = NestedServletException.class)
    public void deleteOrganisatie_nonExistingOrganisatie() throws Exception
    {
        String json = gson.toJson(new Organisatie("KdG Organisatie", "KdG", gebruiker));

        this.mvc.perform(post("/api/organisaties").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.mvc.perform(delete("/api/organisaties/2").header("Authorization", getAdminToken()));
    }

    @Test(expected = NestedServletException.class)
    public void deleteOrganisatie_wrongRole() throws Exception
    {
        String json = gson.toJson(new Organisatie("Organisatie", "KdG", gebruiker));

        this.mvc.perform(post("/api/organisaties").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.mvc.perform(delete("/api/organisaties/1").header("Authorization", getUserToken()))
            .andExpect(status().isForbidden());
    }

    @Test(expected = ServletException.class)
    public void deleteOrganisatie_wrongCredentials() throws Exception
    {
        String json = gson.toJson(new Organisatie("Organisatie", "KdG", gebruiker));

        this.mvc.perform(post("/api/organisaties").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.mvc.perform(delete("/api/organisaties/1").header("Authorization", getTokenAsInexistent()))
            .andExpect(status().isUnauthorized());
    }

    private String getUserToken() throws Exception
    {
        String json = gson.toJson(new Gebruiker("user", "user"));
        MvcResult mvcResult = mvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(json)).andReturn();

        return "Bearer " + (gson.fromJson(mvcResult.getResponse().getContentAsString(), Token.class)).getToken();
    }

    private String getAdminToken() throws Exception
    {
        String json = gson.toJson(new Gebruiker("admin", "admin"));
        MvcResult mvcResult = mvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(json)).andReturn();

        return "Bearer " + (gson.fromJson(mvcResult.getResponse().getContentAsString(), Token.class)).getToken();
    }

    private String getTokenAsInexistent() throws Exception
    {
        String json = gson.toJson(new Gebruiker("wrong", "wrong"));
        MvcResult mvcResult = mvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(json)).andReturn();

        return "Bearer " + (gson.fromJson(mvcResult.getResponse().getContentAsString(), Token.class)).getToken();
    }
}
