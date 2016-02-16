package be.kdg.teamh;

import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.entities.Hoofdthema;
import be.kdg.teamh.entities.Subthema;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class SubthemaIT {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    Gson gson;
    @Mock
    private Gebruiker gebruiker;
    @Mock
    private Hoofdthema hoofdthema;

    @Before
    public void initialize(){
        // MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void subthemaAanmakenEnKoppelenAanHoofdthema() throws Exception {
        Subthema subthema = new Subthema("MTB","Houffalize",hoofdthema,gebruiker);
        String jsonContent = gson.toJson(subthema);

        System.out.println("JSONbyGSON:"+jsonContent);

        this.mockMvc
                .perform(post("/subthemas/create").contentType(MediaType.APPLICATION_JSON).content(jsonContent))
                .andExpect(status().isCreated())
                .andDo(print());

        this.mockMvc
                .perform(get("/subthemas").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andDo(print())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].naam", is("MTB")))
                .andExpect(jsonPath("$[0].beschrijving", is("Houffalize")))
                .andExpect(jsonPath("$[0].hoofdthema.id", is(1)))
                .andExpect(jsonPath("$[0].gebruiker.id", is(1)));
    }

}
