package be.kdg.teamh;

import be.kdg.teamh.entities.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import java.util.Date;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
    private Gson gson;

    @Mock
    private Gebruiker gebruiker;

    @Mock
    private Cirkelsessie cirkelsessie;

    @Mock
    private Subthema subthema;

    @Mock
    private Chat chat;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception
    {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        cirkelsessie = new Cirkelsessie("Een circelsessie", 10, 10, false, new Date(), subthema, gebruiker);
        chat = new Chat("Leuke chat");
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
        String json = objectMapper.writeValueAsString(chat);
        this.mvc.perform(post("/api/chats").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/api/chats").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].naam", is("Leuke chat"))).andDo(print());
    }

    @Test(expected = NestedServletException.class)
    public void createChat_nullInput() throws Exception
    {
        chat.setNaam(null);
        String json = objectMapper.writeValueAsString(chat);
        this.mvc.perform(post("/api/chats").contentType(MediaType.APPLICATION_JSON).content(json));
    }

    @Test
    public void showChat() throws Exception
    {
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
        String json = objectMapper.writeValueAsString(chat);

        this.mvc.perform(post("/api/chats").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/api/chats/2").accept(MediaType.APPLICATION_JSON));
    }

    @Test
    public void updateChat() throws Exception
    {
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
        String json = objectMapper.writeValueAsString(chat);

        this.mvc.perform(post("/api/chats").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated());

        this.mvc.perform(delete("/api/chats/2"));
    }

    @Test
    public void addMessagesToChat() throws Exception
    {
        Bericht bericht;
        String messageJson;

        String json = objectMapper.writeValueAsString(chat);
        this.mvc.perform(post("/api/chats").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated());

        for (int x = 0; x < 5; x++)
        {
            bericht = new Bericht("Heey" + x, gebruiker);
            messageJson = objectMapper.writeValueAsString(bericht);
            this.mvc.perform(post("/api/chats/1/messages").contentType(MediaType.APPLICATION_JSON).content(messageJson))
                .andExpect(status().isCreated());
        }

        this.mvc.perform(get("/api/chats/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.berichten", hasSize(5)));

    }


}
