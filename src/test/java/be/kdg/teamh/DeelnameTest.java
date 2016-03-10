package be.kdg.teamh;

import be.kdg.teamh.entities.Cirkelsessie;
import be.kdg.teamh.entities.Deelname;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.web.util.NestedServletException;

import java.util.Date;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DeelnameTest extends ApiTest
{
    @Test
    public void indexDeelname() throws Exception
    {
        this.mvc.perform(get("/api/deelnames").contentType(MediaType.APPLICATION_JSON).header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void createDeelname() throws Exception
    {
        Cirkelsessie cirkelsessie = new Cirkelsessie("Een circelsessie", 10, 10, false, new Date(), null, null);
        Deelname deelname = new Deelname(15, false, cirkelsessie, null);
        String json = objectMapper.writeValueAsString(deelname);

        this.mvc.perform(post("/api/deelnames").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());
    }

    @Test
    public void showDeelname() throws Exception
    {
        Cirkelsessie cirkelsessie = new Cirkelsessie("Een circelsessie", 10, 10, false, new Date(), null, null);
        Deelname deelname = new Deelname(15, false, cirkelsessie, null);
        String json = objectMapper.writeValueAsString(deelname);

        this.mvc.perform(post("/api/deelnames").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/api/deelnames/1").contentType(MediaType.APPLICATION_JSON).header("Authorization", getUserToken()))
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

        this.mvc.perform(post("/api/deelnames").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/api/deelnames/2").contentType(MediaType.APPLICATION_JSON).header("Authorization", getUserToken()))
            .andExpect(status().isOk());
    }

    @Test
    public void updateDeelname() throws Exception
    {
        Cirkelsessie cirkelsessie = new Cirkelsessie("Een circelsessie", 10, 10, false, new Date(), null, null);
        Deelname deelname = new Deelname(15, false, cirkelsessie, null);
        String json = objectMapper.writeValueAsString(deelname);

        this.mvc.perform(post("/api/deelnames").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        json = objectMapper.writeValueAsString(new Deelname(20, true, cirkelsessie, null));

        this.mvc.perform(put("/api/deelnames/1").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isOk());

        this.mvc.perform(get("/api/deelnames/1").contentType(MediaType.APPLICATION_JSON).header("Authorization", getUserToken()))
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

        this.mvc.perform(post("/api/deelnames").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        json = objectMapper.writeValueAsString(new Deelname(20, true, cirkelsessie, null));

        this.mvc.perform(put("/api/deelnames/2").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()));
    }

    @Test(expected = NestedServletException.class)
    public void deleteDeelname() throws Exception
    {
        Cirkelsessie cirkelsessie = new Cirkelsessie("Een circelsessie", 10, 10, false, new Date(), null, null);
        String json = objectMapper.writeValueAsString(new Deelname(15, false, cirkelsessie, null));

        this.mvc.perform(post("/api/deelnames").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.mvc.perform(delete("/api/deelnames/1").contentType(MediaType.APPLICATION_JSON).header("Authorization", getAdminToken()))
            .andExpect(status().isOk());

        this.mvc.perform(get("/api/deelnames/1").contentType(MediaType.APPLICATION_JSON).header("Authorization", getUserToken()))
            .andExpect(status().isOk());
    }

    @Test(expected = NestedServletException.class)
    public void deleteDeelname_nonExistingDeelname() throws Exception
    {
        Cirkelsessie cirkelsessie = new Cirkelsessie("Een circelsessie", 10, 10, false, new Date(), null, null);
        String json = objectMapper.writeValueAsString(new Deelname(15, false, cirkelsessie, null));

        this.mvc.perform(post("/api/deelnames").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.mvc.perform(delete("/api/deelnames/2").contentType(MediaType.APPLICATION_JSON).header("Authorization", getAdminToken()));
    }
}
