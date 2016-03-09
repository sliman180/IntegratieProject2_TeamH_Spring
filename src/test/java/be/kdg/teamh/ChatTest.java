package be.kdg.teamh;

import be.kdg.teamh.entities.Bericht;
import be.kdg.teamh.entities.Chat;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.web.util.NestedServletException;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ChatTest extends ApiTest
{
    @Test
    public void indexChat() throws Exception
    {
        this.mvc.perform(get("/api/chats").accept(MediaType.APPLICATION_JSON).header("Authorization", getAdminToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void createChat() throws Exception
    {
        Chat chat = new Chat("Leuke chat");
        String json = objectMapper.writeValueAsString(chat);

        this.mvc.perform(post("/api/chats").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/api/chats").accept(MediaType.APPLICATION_JSON).header("Authorization", getAdminToken()))
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

        this.mvc.perform(post("/api/chats").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()));
    }

    @Test
    public void showChat() throws Exception
    {
        Chat chat = new Chat("Leuke chat");
        String json = objectMapper.writeValueAsString(chat);

        this.mvc.perform(post("/api/chats").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/api/chats/1").accept(MediaType.APPLICATION_JSON).header("Authorization", getAdminToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.naam", is("Leuke chat")));
    }

    @Test(expected = NestedServletException.class)
    public void showChat_nonExistingChat() throws Exception
    {
        Chat chat = new Chat("Leuke chat");
        String json = objectMapper.writeValueAsString(chat);

        this.mvc.perform(post("/api/chats").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/api/chats/2").accept(MediaType.APPLICATION_JSON).header("Authorization", getAdminToken()));
    }

    @Test
    public void updateChat() throws Exception
    {
        Chat chat = new Chat("Leuke chat");
        String json = objectMapper.writeValueAsString(chat);

        this.mvc.perform(post("/api/chats").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        chat.setNaam("Gewijzigde chat");
        json = objectMapper.writeValueAsString(chat);

        this.mvc.perform(put("/api/chats/1").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isOk());

        this.mvc.perform(get("/api/chats/1").accept(MediaType.APPLICATION_JSON).header("Authorization", getAdminToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.naam", is("Gewijzigde chat")));
    }

    @Test(expected = NestedServletException.class)
    public void updateChat_nullInput() throws Exception
    {
        Chat chat = new Chat("Leuke chat");
        String json = objectMapper.writeValueAsString(chat);

        this.mvc.perform(post("/api/chats").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        chat.setNaam(null);
        json = objectMapper.writeValueAsString(chat);

        this.mvc.perform(put("/api/chats/1").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()));
    }

    @Test(expected = NestedServletException.class)
    public void updateChat_nonExistingChat() throws Exception
    {
        Chat chat = new Chat("Leuke chat");
        String json = objectMapper.writeValueAsString(chat);

        this.mvc.perform(post("/api/chats").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        chat.setNaam("Gewijzigde naam");
        json = objectMapper.writeValueAsString(chat);

        this.mvc.perform(put("/api/chats/2").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()));
    }

    @Test
    public void deleteChat() throws Exception
    {
        Chat chat = new Chat("Leuke chat");
        String json = objectMapper.writeValueAsString(chat);

        this.mvc.perform(post("/api/chats").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.mvc.perform(delete("/api/chats/1").header("Authorization", getAdminToken()))
            .andExpect(status().isOk());

        this.mvc.perform(get("/api/chats").accept(MediaType.APPLICATION_JSON).header("Authorization", getAdminToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test(expected = NestedServletException.class)
    public void deleteChat_nonExistingChat() throws Exception
    {
        Chat chat = new Chat("Leuke chat");
        String json = objectMapper.writeValueAsString(chat);

        this.mvc.perform(post("/api/chats").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.mvc.perform(delete("/api/chats/2").header("Authorization", getAdminToken()));
    }

    @Test
    public void addMessagesToChat() throws Exception
    {
        Chat chat = new Chat("Leuke chat");
        String json = objectMapper.writeValueAsString(chat);

        this.mvc.perform(post("/api/chats").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        for (int i = 0; i < 5; i++)
        {
            json = objectMapper.writeValueAsString(new Bericht("Hey " + i, null));

            this.mvc.perform(post("/api/chats/1/messages").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
                .andExpect(status().isCreated());
        }

        this.mvc.perform(get("/api/chats/1").contentType(MediaType.APPLICATION_JSON).header("Authorization", getAdminToken()))
            .andExpect(jsonPath("$.berichten", hasSize(5)));
    }
}
