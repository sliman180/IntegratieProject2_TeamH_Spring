package be.kdg.teamh;

import be.kdg.teamh.entities.Cirkelsessie;
import be.kdg.teamh.entities.Deelname;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import java.util.Date;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DeelnameTest
{
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp()
    {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    public void indexDeelname() throws Exception
    {
        this.mvc.perform(get("/api/deelnames").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void createDeelname() throws Exception
    {
        Cirkelsessie cirkelsessie = new Cirkelsessie("Een circelsessie", 10, 10, false, new Date(), null, null);
        Deelname deelname = new Deelname(15, false, cirkelsessie, null);
        String json = objectMapper.writeValueAsString(deelname);

        this.mvc.perform(post("/api/deelnames").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated());
    }

    @Test
    public void showDeelname() throws Exception
    {
        Cirkelsessie cirkelsessie = new Cirkelsessie("Een circelsessie", 10, 10, false, new Date(), null, null);
        Deelname deelname = new Deelname(15, false, cirkelsessie, null);
        String json = objectMapper.writeValueAsString(deelname);

        this.mvc.perform(post("/api/deelnames").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/api/deelnames/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.aangemaakteKaarten", is(15)))
            .andExpect(jsonPath("$.medeorganisator", is(false)))
            .andExpect(jsonPath("$.cirkelsessie.id", is(1)))
            .andExpect(jsonPath("$.cirkelsessie.naam", is("Een circelsessie")));
    }

    @Test(expected = NestedServletException.class)
    public void showDeelname_nonExistingDeelname() throws Exception
    {
        Cirkelsessie cirkelsessie = new Cirkelsessie("Een circelsessie", 10, 10, false, new Date(), null, null);
        Deelname deelname = new Deelname(15, false, cirkelsessie, null);
        String json = objectMapper.writeValueAsString(deelname);

        this.mvc.perform(post("/api/deelnames").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/api/deelnames/2").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void updateDeelname() throws Exception
    {
        Cirkelsessie cirkelsessie = new Cirkelsessie("Een circelsessie", 10, 10, false, new Date(), null, null);
        Deelname deelname = new Deelname(15, false, cirkelsessie, null);
        String json = objectMapper.writeValueAsString(deelname);

        this.mvc.perform(post("/api/deelnames").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated());

        json = objectMapper.writeValueAsString(new Deelname(20, true, cirkelsessie, null));

        this.mvc.perform(put("/api/deelnames/1").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isOk());

        this.mvc.perform(get("/api/deelnames/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.aangemaakteKaarten", is(20)))
            .andExpect(jsonPath("$.medeorganisator", is(true)));
    }

    @Test(expected = NestedServletException.class)
    public void updateCirkelsessie_nonExistingDeelname() throws Exception
    {
        Cirkelsessie cirkelsessie = new Cirkelsessie("Een circelsessie", 10, 10, false, new Date(), null, null);
        Deelname deelname = new Deelname(15, false, cirkelsessie, null);
        String json = objectMapper.writeValueAsString(deelname);

        this.mvc.perform(post("/api/deelnames").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated());

        json = objectMapper.writeValueAsString(new Deelname(20, true, cirkelsessie, null));

        this.mvc.perform(put("/api/deelnames/2").contentType(MediaType.APPLICATION_JSON).content(json));
    }

    @Test(expected = NestedServletException.class)
    public void deleteDeelname() throws Exception
    {
        Cirkelsessie cirkelsessie = new Cirkelsessie("Een circelsessie", 10, 10, false, new Date(), null, null);
        String json = objectMapper.writeValueAsString(new Deelname(15, false, cirkelsessie, null));

        this.mvc.perform(post("/api/deelnames").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated());

        this.mvc.perform(delete("/api/deelnames/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        this.mvc.perform(get("/api/deelnames/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test(expected = NestedServletException.class)
    public void deleteDeelname_nonExistingDeelname() throws Exception
    {
        Cirkelsessie cirkelsessie = new Cirkelsessie("Een circelsessie", 10, 10, false, new Date(), null, null);
        String json = objectMapper.writeValueAsString(new Deelname(15, false, cirkelsessie, null));

        this.mvc.perform(post("/api/deelnames").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated());

        this.mvc.perform(delete("/api/deelnames/2").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
}
