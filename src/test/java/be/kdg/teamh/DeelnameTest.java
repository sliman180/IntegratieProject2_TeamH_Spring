package be.kdg.teamh;

import be.kdg.teamh.entities.Cirkelsessie;
import be.kdg.teamh.entities.Deelname;
import be.kdg.teamh.entities.Gebruiker;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DeelnameTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private Gson gson;

    @Mock
    private Gebruiker gebruiker;

    @Mock
    private Cirkelsessie cirkelsessie;

    @Before
    public void setUp() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        gebruiker = new Gebruiker("Georgy");
        cirkelsessie = new Cirkelsessie("Session one",15,5);
    }

    @Test
    public void deelNemenAanCirkelsessie() throws Exception {
        Deelname deelname = new Deelname(15,false,cirkelsessie,gebruiker);
        String json = gson.toJson(deelname);

        this.mvc.perform(post("/api/deelnames").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated());

        this.mvc.perform(get("/api/deelnames/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.aangemaakteKaarten",is(15)))
                .andExpect(jsonPath("$.medeorganisator",is(false)))
                .andExpect(jsonPath("$.cirkelsessie.id",is(1)))
                .andExpect(jsonPath("$.cirkelsessie.naam",is("Session one")))
                .andExpect(jsonPath("$.gebruiker.id",is(1)))
                .andExpect(jsonPath("$.gebruiker.naam",is("Georgy")))
                .andDo(print());
    }

}
