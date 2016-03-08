package be.kdg.teamh;

import be.kdg.teamh.dtos.LoginResponse;
import be.kdg.teamh.entities.Bericht;
import be.kdg.teamh.entities.Chat;
import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.entities.Rol;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

public class ChatTest
{
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception
    {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    @JsonIgnore
    public void indexChat() throws Exception
    {
        this.mvc.perform(get("/api/chats").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @JsonIgnore
    public void createChat() throws Exception
    {
        Chat chat = new Chat("Leuke chat");
        String json = objectMapper.writeValueAsString(chat);

        this.mvc.perform(post("/api/chats").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/api/chats").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].id", is(1)))
            .andExpect(jsonPath("$[0].naam", is("Leuke chat")));
    }

    @Test(expected = NestedServletException.class)
    public void createChat_nullInput() throws Exception
    {
        Chat chat = new Chat(null);
        String json = objectMapper.writeValueAsString(chat);

        this.mvc.perform(post("/api/chats").contentType(MediaType.APPLICATION_JSON).content(json));
    }

    @Test
    public void showChat() throws Exception
    {
        Chat chat = new Chat("Leuke chat");
        String json = objectMapper.writeValueAsString(chat);

        this.mvc.perform(post("/api/chats").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/api/chats/1").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.naam", is("Leuke chat")));
    }

    @Test(expected = NestedServletException.class)
    public void showChat_nonExistingChat() throws Exception
    {
        Chat chat = new Chat("Leuke chat");
        String json = objectMapper.writeValueAsString(chat);

        this.mvc.perform(post("/api/chats").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/api/chats/2").accept(MediaType.APPLICATION_JSON));
    }

    @Test
    public void updateChat() throws Exception
    {
        Chat chat = new Chat("Leuke chat");
        String json = objectMapper.writeValueAsString(chat);

        this.mvc.perform(post("/api/chats").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated());

        chat.setNaam("Gewijzigde chat");
        json = objectMapper.writeValueAsString(chat);

        this.mvc.perform(put("/api/chats/1").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isOk());

        this.mvc.perform(get("/api/chats/1").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.naam", is("Gewijzigde chat")));
    }

    @Test(expected = NestedServletException.class)
    public void updateChat_nullInput() throws Exception
    {
        Chat chat = new Chat("Leuke chat");
        String json = objectMapper.writeValueAsString(chat);

        this.mvc.perform(post("/api/chats").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated());

        chat.setNaam(null);
        json = objectMapper.writeValueAsString(chat);

        this.mvc.perform(put("/api/chats/1").contentType(MediaType.APPLICATION_JSON).content(json));
    }

    @Test(expected = NestedServletException.class)
    public void updateChat_nonExistingChat() throws Exception
    {
        Chat chat = new Chat("Leuke chat");
        String json = objectMapper.writeValueAsString(chat);

        this.mvc.perform(post("/api/chats").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated());

        chat.setNaam("Gewijzigde naam");
        json = objectMapper.writeValueAsString(chat);

        this.mvc.perform(put("/api/chats/2").contentType(MediaType.APPLICATION_JSON).content(json));
    }

    @Test
    public void deleteChat() throws Exception
    {
        Chat chat = new Chat("Leuke chat");
        String json = objectMapper.writeValueAsString(chat);

        this.mvc.perform(post("/api/chats").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated());

        this.mvc.perform(delete("/api/chats/1"))
            .andExpect(status().isOk());

        this.mvc.perform(get("/api/chats").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test(expected = NestedServletException.class)
    public void deleteChat_nonExistingChat() throws Exception
    {
        Chat chat = new Chat("Leuke chat");
        String json = objectMapper.writeValueAsString(chat);

        this.mvc.perform(post("/api/chats").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated());

        this.mvc.perform(delete("/api/chats/2"));
    }

    @Test
    public void addMessagesToChat() throws Exception
    {
        Chat chat = new Chat("Leuke chat");
        String json = objectMapper.writeValueAsString(chat);

        this.mvc.perform(post("/api/chats").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated());

        for (int i = 0; i < 5; i++)
        {
            json = objectMapper.writeValueAsString(new Bericht("Hey " + i, null));

            this.mvc.perform(post("/api/chats/1/messages").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getUserToken()))
                .andExpect(status().isCreated());
        }

        this.mvc.perform(get("/api/chats/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.berichten", hasSize(5)));
    }

    private String getUserToken() throws Exception {
        String json = objectMapper.writeValueAsString(new Gebruiker("user", "user", new ArrayList<>(Collections.singletonList(new Rol("user", "user")))));
        MvcResult mvcResult = mvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(json)).andReturn();

        return "Bearer " + objectMapper.readValue(mvcResult.getResponse().getContentAsString(), LoginResponse.class).getToken();
    }

    private String getAdminToken() throws Exception {
        String json = objectMapper.writeValueAsString(new Gebruiker("admin", "admin", new ArrayList<>(Arrays.asList(new Rol("admin", "admin"), new Rol("user", "user")))));
        MvcResult mvcResult = mvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(json)).andReturn();

        return "Bearer " + objectMapper.readValue(mvcResult.getResponse().getContentAsString(), LoginResponse.class).getToken();
    }

    private String getTokenAsInexistent() throws Exception {
        String json = objectMapper.writeValueAsString(new Gebruiker("wrong", "wrong", new ArrayList<>(Collections.singletonList(new Rol("wrong", "wrong")))));
        MvcResult mvcResult = mvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(json)).andReturn();

        return "Bearer " + objectMapper.readValue(mvcResult.getResponse().getContentAsString(), LoginResponse.class).getToken();
    }

}
