package be.kdg.teamh;

import be.kdg.teamh.configuration.JwtFilter;
import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.entities.LoginResponse;
import be.kdg.teamh.entities.Organisatie;
import be.kdg.teamh.entities.Rol;
import be.kdg.teamh.exceptions.IsForbidden;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import javax.servlet.ServletException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
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
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).addFilter(new JwtFilter(), "/organisaties/*").build();
    }

    @Test
    public void indexOrganisatie() throws Exception
    {
        this.mvc.perform(get("/organisaties").accept(MediaType.APPLICATION_JSON)
            .header("Authorization", getTokenAsUser()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test(expected = ServletException.class)
    public void indexOrganisatie_wrongCredentials() throws Exception
    {
        this.mvc.perform(get("/organisaties").accept(MediaType.APPLICATION_JSON)
            .header("Authorization", getTokenAsInexistent()));
    }

    @Test
    public void createOrganisatie() throws Exception
    {
        String json = gson.toJson(new Organisatie("NaamOrganisatie", "Beschrijving", gebruiker));

        this.mvc.perform(post("/organisaties").contentType(MediaType.APPLICATION_JSON).content(json)
            .header("Authorization", getTokenAsAdmin()))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/organisaties").contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", getTokenAsAdmin()))
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

        this.mvc.perform(post("/organisaties").contentType(MediaType.APPLICATION_JSON).content(json)
                .header("Authorization", getTokenAsAdmin()));
    }

    @Test(expected = NestedServletException.class)
    public void createOrganisatie_wrongRole() throws Exception
    {
        String json = gson.toJson(new Organisatie("", "", gebruiker));

        this.mvc.perform(post("/organisaties").contentType(MediaType.APPLICATION_JSON).content(json)
            .header("Authorization", getTokenAsUser()))
            .andExpect(status().isForbidden());
    }

    @Test(expected = ServletException.class)
    public void createOrganisatie_wrongCredentials() throws Exception
    {
        String json = gson.toJson(new Organisatie(null, null, gebruiker));

        this.mvc.perform(post("/organisaties").contentType(MediaType.APPLICATION_JSON).content(json)
            .header("Authorization", getTokenAsInexistent()))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void showOrganisatie() throws Exception
    {
        String json = gson.toJson(new Organisatie("NaamOrganisatie", "Beschrijving", gebruiker));

        this.mvc.perform(post("/organisaties").contentType(MediaType.APPLICATION_JSON).content(json)
            .header("Authorization", getTokenAsAdmin()))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/organisaties/1").contentType(MediaType.APPLICATION_JSON_UTF8)
            .header("Authorization", getTokenAsUser()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.naam", is("NaamOrganisatie")))
            .andExpect(jsonPath("$.beschrijving", is("Beschrijving")));
    }

    @Test(expected = ServletException.class)
    public void showOrganisatie_wrongCredentials() throws Exception
    {
        String json = gson.toJson(new Organisatie("NaamOrganisatie", "Beschrijving", gebruiker));

        this.mvc.perform(post("/organisaties").contentType(MediaType.APPLICATION_JSON).content(json)
            .header("Authorization", getTokenAsAdmin()))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/organisaties/1").contentType(MediaType.APPLICATION_JSON_UTF8)
            .header("Authorization", getTokenAsInexistent()));
    }

    @Test
    public void updateOrganisatie() throws Exception
    {
        String json = gson.toJson(new Organisatie("NaamOrganisatie", "Beschrijving", gebruiker));

        this.mvc.perform(post("/organisaties").contentType(MediaType.APPLICATION_JSON).content(json)
            .header("Authorization", getTokenAsAdmin()))
            .andExpect(status().isCreated());

        json = gson.toJson(new Organisatie("NieuweNaamOrganisatie", "Beschrijving", gebruiker));

        this.mvc.perform(put("/organisaties/1").contentType(MediaType.APPLICATION_JSON).content(json)
            .header("Authorization", getTokenAsAdmin()))
            .andExpect(status().isOk());

        this.mvc.perform(get("/organisaties/1").contentType(MediaType.APPLICATION_JSON_UTF8)
            .header("Authorization", getTokenAsUser()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.naam", is("NieuweNaamOrganisatie")))
            .andExpect(jsonPath("$.beschrijving", is("Beschrijving")));
    }

    @Test(expected = NestedServletException.class)
    public void updateOrganisatie_nullInput() throws Exception
    {
        String json = gson.toJson(new Organisatie("NieuweNaamOrganisatie", "KdG", gebruiker));

        this.mvc.perform(post("/organisaties").contentType(MediaType.APPLICATION_JSON).content(json)
            .header("Authorization", getTokenAsAdmin()))
            .andExpect(status().isCreated());

        json = gson.toJson(new Organisatie(null, null, gebruiker));

        this.mvc.perform(put("/organisaties/1").contentType(MediaType.APPLICATION_JSON).content(json)
            .header("Authorization", getTokenAsAdmin()));
    }

    @Test(expected = NestedServletException.class)
    public void updateOrganisatie_nonExistingOrganisatie() throws Exception
    {
        String json = gson.toJson(new Organisatie("Organisatie", "KdG", gebruiker));

        this.mvc.perform(post("/organisaties").contentType(MediaType.APPLICATION_JSON).content(json)
            .header("Authorization", getTokenAsAdmin()))
            .andExpect(status().isCreated());

        json = gson.toJson(new Organisatie("NieuweNaamOrganisatie", "Beschrijving", gebruiker));

        this.mvc.perform(put("/organisaties/2").contentType(MediaType.APPLICATION_JSON).content(json)
            .header("Authorization", getTokenAsAdmin()));
    }

    @Test(expected = NestedServletException.class)
    public void updateOrganisatie_wrongRole() throws Exception
    {
        String json = gson.toJson(new Organisatie("Organisatie", "KdG", gebruiker));

        this.mvc.perform(post("/organisaties").contentType(MediaType.APPLICATION_JSON).content(json)
            .header("Authorization", getTokenAsAdmin()))
            .andExpect(status().isCreated());

        json = gson.toJson(new Organisatie("NieuweNaamOrganisatie", "Beschrijving", gebruiker));

        this.mvc.perform(put("/organisaties/1").contentType(MediaType.APPLICATION_JSON).content(json)
            .header("Authorization", getTokenAsUser()))
            .andExpect(status().isForbidden());
    }

    @Test(expected = ServletException.class)
    public void updateOrganisatie_wrongCredentials() throws Exception
    {
        String json = gson.toJson(new Organisatie("Organisatie", "KdG", gebruiker));

        this.mvc.perform(post("/organisaties").contentType(MediaType.APPLICATION_JSON).content(json)
            .header("Authorization", getTokenAsAdmin()))
            .andExpect(status().isCreated());

        json = gson.toJson(new Organisatie("NieuweNaamOrganisatie", "Beschrijving", gebruiker));

        this.mvc.perform(put("/organisaties/1").contentType(MediaType.APPLICATION_JSON).content(json)
            .header("Authorization", getTokenAsInexistent()))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void deleteOrganisatie() throws Exception
    {
        String json = gson.toJson(new Organisatie("teVerwijderenOrganisatie", "Beschrijving", gebruiker));

        this.mvc.perform(post("/organisaties").contentType(MediaType.APPLICATION_JSON).content(json)
            .header("Authorization", getTokenAsAdmin()))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/organisaties/1")
            .header("Authorization", getTokenAsAdmin()))
            .andExpect(status().isOk());

        this.mvc.perform(delete("/organisaties/1")
            .header("Authorization", getTokenAsAdmin()))
            .andExpect(status().isOk());

        this.mvc.perform(get("/organisaties").accept(MediaType.APPLICATION_JSON)
            .header("Authorization", getTokenAsAdmin()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test(expected = NestedServletException.class)
    public void deleteOrganisatie_nonExistingOrganisatie() throws Exception
    {
        String json = gson.toJson(new Organisatie("KdG Organisatie", "KdG", gebruiker));

        this.mvc.perform(post("/organisaties").contentType(MediaType.APPLICATION_JSON).content(json)
            .header("Authorization", getTokenAsAdmin()))
            .andExpect(status().isCreated());

        this.mvc.perform(delete("/organisaties/2")
            .header("Authorization", getTokenAsAdmin()));
    }

    @Test(expected = NestedServletException.class)
    public void deleteOrganisatie_wrongRole() throws Exception
    {
        String json = gson.toJson(new Organisatie("Organisatie", "KdG", gebruiker));

        this.mvc.perform(post("/organisaties").contentType(MediaType.APPLICATION_JSON).content(json)
            .header("Authorization", getTokenAsAdmin()))
            .andExpect(status().isCreated());

        this.mvc.perform(delete("/organisaties/1")
            .header("Authorization", getTokenAsUser()))
            .andExpect(status().isForbidden());
    }

    @Test(expected = ServletException.class)
    public void deleteOrganisatie_wrongCredentials() throws Exception
    {
        String json = gson.toJson(new Organisatie("Organisatie", "KdG", gebruiker));

        this.mvc.perform(post("/organisaties").contentType(MediaType.APPLICATION_JSON).content(json)
            .header("Authorization", getTokenAsAdmin()))
            .andExpect(status().isCreated());

        this.mvc.perform(delete("/organisaties/1")
            .header("Authorization", getTokenAsInexistent()))
            .andExpect(status().isUnauthorized());
    }

    private String getTokenAsUser() throws Exception {
        List<Rol> rollen = new ArrayList<>();
        rollen.add(new Rol("user", "user"));
        String json = gson.toJson(new Gebruiker("user", "user", rollen));

        MvcResult mvcResult = mvc.perform(post("/user/login").contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andReturn();

        return  "Bearer " + (gson.fromJson(mvcResult.getResponse().getContentAsString(), LoginResponse.class)).getToken();
    }

    private String getTokenAsAdmin() throws Exception {
        List<Rol> rollen = new ArrayList<>();
        rollen.add(new Rol("admin", "admin"));
        rollen.add(new Rol("user", "user"));
        String json = gson.toJson(new Gebruiker("admin", "admin", rollen));

        MvcResult mvcResult = mvc.perform(post("/user/login").contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andReturn();

        return  "Bearer " + (gson.fromJson(mvcResult.getResponse().getContentAsString(), LoginResponse.class)).getToken();
    }

    private String getTokenAsInexistent() throws Exception {
        List<Rol> rollen = new ArrayList<>();
        rollen.add(new Rol("wrong", ""));
        String json = gson.toJson(new Gebruiker("wrong", "wrong", rollen));

        MvcResult mvcResult = mvc.perform(post("/user/login").contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andReturn();

        return  "Bearer " + (gson.fromJson(mvcResult.getResponse().getContentAsString(), LoginResponse.class)).getToken();
    }
}
