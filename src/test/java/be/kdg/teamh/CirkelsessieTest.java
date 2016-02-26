package be.kdg.teamh;

import be.kdg.teamh.entities.Cirkelsessie;
import be.kdg.teamh.entities.Deelname;
import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.entities.Subthema;
import be.kdg.teamh.exceptions.CirkelsessieNotFound;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import javax.servlet.Filter;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CirkelsessieTest {

    private MockMvc mvc;

//    private LocalDateTime date;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private FilterChainProxy filterChainProxy;

    @Autowired
    private Gson gson;

    @Mock
    Gebruiker gebruiker;

    @Mock
    Subthema subthema;

    @Before
    public void setUp()
    {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).addFilter(filterChainProxy).build();
//        date = LocalDateTime.now();
    }

    //index
    @Test
    public void indexCirkesessie() throws Exception {
        this.mvc.perform(get("/cirkelsessies").contentType(MediaType.APPLICATION_JSON)
            .with(loginAsAdmin()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$",hasSize(0)));
    }

//    Create  null input
    @Test
    public void createCirkelsessie() throws Exception {
        String json = gson.toJson(new Cirkelsessie("Session one",gebruiker,10,subthema));

        this.mvc.perform(post("/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json)
                .with(loginAsAdmin()))
                .andExpect(status().isCreated());

        this.mvc.perform(get("/cirkelsessies").contentType(MediaType.APPLICATION_JSON)
                .with(loginAsAdmin()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].id",is(1)))
                .andExpect(jsonPath("$[0].naam",is("Session one")))
                .andExpect(jsonPath("$[0].maxAantalKaarten",is(10)));
    }

    @Test(expected = NullPointerException.class)
    public void createCirkelsessie_nullInput() throws Exception {
        String json = gson.toJson(new Cirkelsessie(null,gebruiker,null,subthema));

        this.mvc.perform(post("/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json)
                .with(loginAsAdmin()));

        this.mvc.perform(get("/cirkelsessies").contentType(MediaType.APPLICATION_JSON)
                .with(loginAsAdmin())).andDo(print());
    }
//    Read non existing
    @Test
    public void showCirkelsessie() throws Exception {
        String json = gson.toJson(new Cirkelsessie("Session one",gebruiker,10,subthema));

        this.mvc.perform(post("/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json)
                .with(loginAsAdmin()))
                .andExpect(status().isCreated());

        this.mvc.perform(get("/cirkelsessies/1").contentType(MediaType.APPLICATION_JSON)
                .with(loginAsAdmin()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.naam",is("Session one")))
                .andExpect(jsonPath("$.maxAantalKaarten",is(10)));
    }

    @Test(expected = NestedServletException.class)
    public void showCirkelsessie_nonExistingCirkelsessie() throws Exception {
        String json = gson.toJson(new Cirkelsessie("Session one",gebruiker,10,subthema));

        this.mvc.perform(post("/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json)
                .with(loginAsAdmin()))
                .andExpect(status().isCreated());

        this.mvc.perform(get("/cirkelsessies/2").contentType(MediaType.APPLICATION_JSON)
                .with(loginAsAdmin()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(2)))
                .andExpect(jsonPath("$.naam",is("Session one")))
                .andExpect(jsonPath("$.maxAantalKaarten",is(10)));
    }
//    Update null input non existing

    @Test(expected = NestedServletException.class)
    public void updateCirkelsessie() throws Exception {
        String json = gson.toJson(new Cirkelsessie("Session one",gebruiker,10,subthema));

        this.mvc.perform(post("/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json)
                .with(loginAsAdmin()))
                .andExpect(status().isCreated());

        json = gson.toJson(new Cirkelsessie("Session two",gebruiker,15,subthema));

        this.mvc.perform(put("/cirkelsessies/1").contentType(MediaType.APPLICATION_JSON).content(json)
                .with(loginAsAdmin()))
                .andExpect(status().isOk());

        this.mvc.perform(get("/cirkelsessies/1").contentType(MediaType.APPLICATION_JSON)
                .with(loginAsAdmin()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.naam",is("Session two")))
                .andExpect(jsonPath("$.maxAantalKaarten",is(15)));
    }

    @Test(expected = NullPointerException.class)
    public void updateCirkelsessie_nullInput() throws Exception {
        String json = gson.toJson(new Cirkelsessie("Session one",gebruiker,10,subthema));

        this.mvc.perform(post("/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json)
                .with(loginAsAdmin()))
                .andExpect(status().isCreated());

        json = gson.toJson(new Cirkelsessie(null,gebruiker,null,subthema));

        this.mvc.perform(put("/cirkelsessies/1").contentType(MediaType.APPLICATION_JSON).content(json)
                .with(loginAsAdmin()));

    }

    @Test(expected = NestedServletException.class)
    public void updateCirkelsessie_nonExistingCirkelsessie() throws Exception {
        String json = gson.toJson(new Cirkelsessie("Session one",gebruiker,10,subthema));

        this.mvc.perform(post("/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json)
                .with(loginAsAdmin()))
                .andExpect(status().isCreated());

        json = gson.toJson(new Cirkelsessie("Session one",gebruiker,10,subthema));

        this.mvc.perform(put("/cirkelsessies/2").contentType(MediaType.APPLICATION_JSON).content(json)
                .with(loginAsAdmin())).andExpect(status().isOk());

    }
//    Delete non existing

    @Test(expected = NestedServletException.class)
    public void deleteCirkelsessie() throws Exception {
        String json = gson.toJson(new Cirkelsessie("Session one",gebruiker,10,subthema));

        this.mvc.perform(post("/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json)
                .with(loginAsAdmin()))
                .andExpect(status().isCreated());

        this.mvc.perform(delete("/cirkelsessies/1")
                .with(loginAsAdmin()))
                .andExpect(status().isOk());

        this.mvc.perform(get("/cirkelsessies/1").contentType(MediaType.APPLICATION_JSON)
            .with(loginAsAdmin()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(0)));
    }

    @Test(expected = NestedServletException.class)
    public void deleteCirkelsessie_nonExistingCirkelsessie() throws Exception {
        String json = gson.toJson(new Cirkelsessie("Session one",gebruiker,10,subthema));

        this.mvc.perform(post("/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json)
                .with(loginAsAdmin()))
                .andExpect(status().isCreated());

        this.mvc.perform(delete("/cirkelsessies/2")
                .with(loginAsAdmin()));
    }

    @Test
    public void checkCirkelsessieLinkedToSubthema() throws Exception {
        subthema = new Subthema("Houffalize","Route 6",null);
        String json = gson.toJson(new Cirkelsessie("Session one",gebruiker,10,subthema));

        this.mvc.perform(post("/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json)
                .with(loginAsAdmin()))
                .andExpect(status().isCreated());

        this.mvc.perform(get("/cirkelsessies/1/subthema").contentType(MediaType.APPLICATION_JSON)
                .with(loginAsAdmin()))
                .andExpect(status().isOk()).andDo(print())
                .andExpect(jsonPath("$.id",is(1))).andDo(print())
                .andExpect(jsonPath("$.naam",is("Houffalize"))).andDo(print())
                .andExpect(jsonPath("$.beschrijving",is("Route 6"))).andDo(print());
    }


    private RequestPostProcessor loginAsUser()
    {
        return httpBasic("user", "user");
    }

    private RequestPostProcessor loginAsAdmin()
    {
        return httpBasic("admin", "admin");
    }

    private RequestPostProcessor loginWithWrongCredentials()
    {
        return httpBasic("wrong", "wrong");
    }

}
