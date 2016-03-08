package be.kdg.teamh;

import be.kdg.teamh.dtos.Token;
import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.entities.Organisatie;
import be.kdg.teamh.entities.Rol;
import be.kdg.teamh.filters.JwtFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
    private ObjectMapper objectMapper;

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

    @Test(expected = NestedServletException.class)
    public void createOrganisatie_wrongRole() throws Exception
    {
        String json = objectMapper.writeValueAsString(new Organisatie("", "", null));

        this.mvc.perform(post("/api/organisaties").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getUserToken()))
            .andExpect(status().isForbidden());
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

    @Test
    public void deleteOrganisatie() throws Exception
    {
        String json = objectMapper.writeValueAsString(new Organisatie("teVerwijderenOrganisatie", "Beschrijving", null));

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
        String json = objectMapper.writeValueAsString(new Organisatie("KdG Organisatie", "KdG", null));

        this.mvc.perform(post("/api/organisaties").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.mvc.perform(delete("/api/organisaties/2").header("Authorization", getAdminToken()));
    }

    @Test(expected = NestedServletException.class)
    public void deleteOrganisatie_wrongRole() throws Exception
    {
        String json = objectMapper.writeValueAsString(new Organisatie("Organisatie", "KdG", null));

        this.mvc.perform(post("/api/organisaties").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.mvc.perform(delete("/api/organisaties/1").header("Authorization", getUserToken()))
            .andExpect(status().isForbidden());
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

    private String getUserToken() throws Exception
    {
        String json = objectMapper.writeValueAsString(new Gebruiker("user", "user", new ArrayList<>(Collections.singletonList(new Rol("user", "user")))));
        MvcResult mvcResult = mvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(json)).andReturn();

        return "Bearer " + objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Token.class).getToken();
    }

    private String getAdminToken() throws Exception
    {
        String json = objectMapper.writeValueAsString(new Gebruiker("admin", "admin", new ArrayList<>(Arrays.asList(new Rol("admin", "admin"), new Rol("user", "user")))));
        MvcResult mvcResult = mvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(json)).andReturn();

        return "Bearer " + objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Token.class).getToken();
    }

    private String getTokenAsInexistent() throws Exception
    {
        String json = objectMapper.writeValueAsString(new Gebruiker("wrong", "wrong", new ArrayList<>(Collections.singletonList(new Rol("wrong", "wrong")))));
        MvcResult mvcResult = mvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(json)).andReturn();

        return "Bearer " + objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Token.class).getToken();
    }
}
