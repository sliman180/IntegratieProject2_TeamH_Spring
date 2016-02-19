package be.kdg.teamh;

import be.kdg.teamh.entities.Hoofdthema;
import be.kdg.teamh.entities.Subthema;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.collection.IsCollectionWithSize.*;
import static org.hamcrest.core.Is.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

/**
 * Created by lollik on 18/02/2016.
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
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
    public void subthemaAanmakenEnKoppelenAanHoofdthema() throws Exception {
        Hoofdthema hoofdthema = new Hoofdthema("MTB","Ardennen");
        Subthema subthema = new Subthema("Houfallize", "Route 6",hoofdthema);
        String jsonSubthema = gson.toJson(subthema);

        MvcResult postResult = this.mockMvc.perform(post("/subthemas/create").contentType(MediaType.APPLICATION_JSON).content(jsonSubthema))
                .andExpect(status().isCreated())
                .andDo(print()).andReturn();

        Subthema responseSubthema = gson.fromJson(postResult.getResponse().getContentAsString(), Subthema.class);

        this.mockMvc.perform(get("/subthemas").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(responseSubthema.getId())))
                .andExpect(jsonPath("$[0].naam",is(responseSubthema.getNaam())))
                .andExpect(jsonPath("$[0].beschrijving",is(responseSubthema.getBeschrijving())))
                .andExpect(jsonPath("$[0].hoofdthema.id",is(responseSubthema.getHoofdthema().getId())))
                .andExpect(jsonPath("$[0].hoofdthema.naam",is(responseSubthema.getHoofdthema().getNaam())))
                .andExpect(jsonPath("$[0].hoofdthema.beschrijving",is(responseSubthema.getHoofdthema().getBeschrijving())))
                .andDo(print());

    }
}
