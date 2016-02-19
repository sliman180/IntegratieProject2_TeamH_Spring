package be.kdg.teamh;

import be.kdg.teamh.entities.Subthema;
import com.google.gson.Gson;
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

import static org.hamcrest.collection.IsCollectionWithSize.*;
import static org.hamcrest.core.Is.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SubthemaIT {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    Gson gson;

    @Before
    public void init(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void createSubthema() throws Exception {
        Subthema subthema = new Subthema("Houffalize", "Route 6");
        String jsonSubthema = gson.toJson(subthema);

        this.mockMvc.perform(post("/subthemas").contentType(MediaType.APPLICATION_JSON).content(jsonSubthema))
                .andExpect(status().isCreated());

        this.mockMvc.perform(get("/subthemas").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].naam",is("Houffalize")))
                .andExpect(jsonPath("$[0].beschrijving",is("Route 6")));
    }

    @Test
    public void deleteSubthema() throws Exception {
        Subthema subthema = new Subthema("Houffalize", "Route 6");
        String jsonSubthema = gson.toJson(subthema);

        this.mockMvc.perform(post("/subthemas").contentType(MediaType.APPLICATION_JSON).content(jsonSubthema))
                .andExpect(status().isCreated());

        this.mockMvc.perform(get("/subthemas/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        this.mockMvc.perform(delete("/subthemas/1"))
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/subthemas").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void updateSubthema() throws Exception {
        Subthema subthema = new Subthema("Houffalize", "Route 6");
        String jsonSubthema = gson.toJson(subthema);

        this.mockMvc.perform(post("/subthemas").contentType(MediaType.APPLICATION_JSON).content(jsonSubthema))
                .andExpect(status().isCreated());

        this.mockMvc.perform(get("/subthemas/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Subthema subthema1 = new Subthema("Houffalize","Route 3");
        String jsonConten1 = gson.toJson(subthema1);

        this.mockMvc.perform(put("/subthemas/1").contentType(MediaType.APPLICATION_JSON).content(jsonConten1))
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/subthemas/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.naam",is("Houffalize")))
                .andExpect(jsonPath("$.beschrijving",is("Route 3")));

    }

    @Test
    public void showSubthema() throws Exception {
        Subthema subthema = new Subthema("Houffalize", "Route 6");
        String jsonSubthema = gson.toJson(subthema);

        this.mockMvc.perform(post("/subthemas").contentType(MediaType.APPLICATION_JSON).content(jsonSubthema))
                .andExpect(status().isCreated());

        this.mockMvc.perform(get("/subthemas/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/subthemas/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.naam",is("Houffalize")))
                .andExpect(jsonPath("$.beschrijving",is("Route 6")));
    }

    @Test
    public void indexSubthema() throws Exception {
        this.mockMvc.perform(get("/subthemas").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }


}
