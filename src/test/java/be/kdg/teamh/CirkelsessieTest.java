package be.kdg.teamh;

import be.kdg.teamh.entities.Cirkelsessie;
import be.kdg.teamh.entities.Subthema;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.web.util.NestedServletException;

import java.util.Date;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CirkelsessieTest extends ApiTest
{
    @Test
    public void indexCirkesessie() throws Exception
    {
        this.mvc.perform(get("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON).header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void createCirkelsessie() throws Exception
    {
        Cirkelsessie cirkelsessie = new Cirkelsessie("Session one", 5, 10, false, new Date(), null, null);
        String json = objectMapper.writeValueAsString(cirkelsessie);

        this.mvc.perform(post("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON).header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].id", is(1)))
            .andExpect(jsonPath("$[0].naam", is("Session one")))
            .andExpect(jsonPath("$[0].maxAantalKaarten", is(10)));
    }

    @Test(expected = NestedServletException.class)
    public void createCirkelsessie_nullInput() throws Exception
    {
        Cirkelsessie cirkelsessie = new Cirkelsessie(null, 0, 0, false, null, null, null);
        String json = objectMapper.writeValueAsString(cirkelsessie);

        this.mvc.perform(post("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()));
    }

    @Test
    public void showCirkelsessie() throws Exception
    {
        Cirkelsessie cirkelsessie = new Cirkelsessie("Session one", 5, 10, false, new Date(), null, null);
        String json = objectMapper.writeValueAsString(cirkelsessie);

        this.mvc.perform(post("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/api/cirkelsessies/1").contentType(MediaType.APPLICATION_JSON).header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.naam", is("Session one")))
            .andExpect(jsonPath("$.aantalCirkels", is(5)))
            .andExpect(jsonPath("$.maxAantalKaarten", is(10)));
    }

    @Test(expected = NestedServletException.class)
    public void showCirkelsessie_nonExistingCirkelsessie() throws Exception
    {
        Cirkelsessie cirkelsessie = new Cirkelsessie("Session one", 5, 10, false, new Date(), null, null);
        String json = objectMapper.writeValueAsString(cirkelsessie);

        this.mvc.perform(post("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/api/cirkelsessies/2").contentType(MediaType.APPLICATION_JSON).header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(2)))
            .andExpect(jsonPath("$.naam", is("Session one")))
            .andExpect(jsonPath("$[0].aantalCirkels", is(5)))
            .andExpect(jsonPath("$.maxAantalKaarten", is(10)));
    }

    @Test
    public void updateCirkelsessie() throws Exception
    {
        Cirkelsessie cirkelsessie = new Cirkelsessie("Session one", 5, 10, false, new Date(), null, null);
        String json = objectMapper.writeValueAsString(cirkelsessie);

        this.mvc.perform(post("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        json = objectMapper.writeValueAsString(new Cirkelsessie("Session two", 8, 15, false, new Date(), null, null));

        this.mvc.perform(put("/api/cirkelsessies/1").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isOk());

        this.mvc.perform(get("/api/cirkelsessies/1").contentType(MediaType.APPLICATION_JSON).header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.naam", is("Session two")))
            .andExpect(jsonPath("$.aantalCirkels", is(8)))
            .andExpect(jsonPath("$.maxAantalKaarten", is(15)));
    }

    @Test(expected = NestedServletException.class)
    public void updateCirkelsessie_nullInput() throws Exception
    {
        Cirkelsessie cirkelsessie = new Cirkelsessie("Session one", 5, 10, false, new Date(), null, null);
        String json = objectMapper.writeValueAsString(cirkelsessie);

        this.mvc.perform(post("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        json = objectMapper.writeValueAsString(new Cirkelsessie(null, 0, 0, false, new Date(), null, null));

        this.mvc.perform(put("/api/cirkelsessies/1").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()));
    }

    @Test(expected = NestedServletException.class)
    public void updateCirkelsessie_nonExistingCirkelsessie() throws Exception
    {
        Cirkelsessie cirkelsessie = new Cirkelsessie("Session one", 5, 10, false, new Date(), null, null);
        String json = objectMapper.writeValueAsString(cirkelsessie);

        this.mvc.perform(post("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        json = objectMapper.writeValueAsString(new Cirkelsessie("Session one", 5, 10, false, new Date(), null, null));

        this.mvc.perform(put("/api/cirkelsessies/2").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isOk());
    }

    @Test(expected = NestedServletException.class)
    public void deleteCirkelsessie() throws Exception
    {
        Cirkelsessie cirkelsessie = new Cirkelsessie("Session one", 5, 10, false, new Date(), null, null);
        String json = objectMapper.writeValueAsString(cirkelsessie);

        this.mvc.perform(post("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.mvc.perform(delete("/api/cirkelsessies/1").header("Authorization", getAdminToken()))
            .andExpect(status().isOk());

        this.mvc.perform(get("/api/cirkelsessies/1").contentType(MediaType.APPLICATION_JSON).header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test(expected = NestedServletException.class)
    public void deleteCirkelsessie_nonExistingCirkelsessie() throws Exception
    {
        this.mvc.perform(delete("/api/cirkelsessies/1").header("Authorization", getAdminToken()));
    }

    @Test
    public void checkCirkelsessieLinkedToSubthema() throws Exception
    {
        Subthema subthema = new Subthema("Houffalize", "Route 6", null);
        Cirkelsessie cirkelsessie = new Cirkelsessie("Session one", 5, 10, false, new Date(), subthema, null);
        String json = objectMapper.writeValueAsString(cirkelsessie);

        this.mvc.perform(post("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/api/cirkelsessies/1/subthema").contentType(MediaType.APPLICATION_JSON).header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.naam", is("Houffalize")))
            .andExpect(jsonPath("$.beschrijving", is("Route 6")));
    }

    @Test
    public void cloneCirkelSessie() throws Exception
    {
        Cirkelsessie cirkelsessie = new Cirkelsessie("Session one", 5, 10, false, new Date(), null, null);
        String json = objectMapper.writeValueAsString(cirkelsessie);

        this.mvc.perform(post("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.mvc.perform(post("/api/cirkelsessies/1/clone").contentType(MediaType.APPLICATION_JSON).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/api/cirkelsessies/2").contentType(MediaType.APPLICATION_JSON).header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(2)))
            .andExpect(jsonPath("$.naam", is("Session one")))
            .andExpect(jsonPath("$.maxAantalKaarten", is(5)))
            .andExpect(jsonPath("$.aantalCirkels", is(10)))
            .andExpect(jsonPath("$.deelnames", hasSize(0)));
    }

    @Test
    public void showGeplandeCirkelSessies() throws Exception
    {
        Cirkelsessie cirkelsessie = new Cirkelsessie("Session one", 5, 10, true, new Date(new Date().getTime() + 3000), null, null);
        String json = objectMapper.writeValueAsString(cirkelsessie);

        this.mvc.perform(post("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        cirkelsessie = new Cirkelsessie("Session two", 5, 10, false, new Date(), null, null);
        json = objectMapper.writeValueAsString(cirkelsessie);

        this.mvc.perform(post("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/api/cirkelsessies/gepland").contentType(MediaType.APPLICATION_JSON).header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].naam", is("Session one")));

    }

    @Test
    public void showActieveCirkelSessies() throws Exception
    {
        Cirkelsessie cirkelsessie = new Cirkelsessie("Session one", 5, 10, true, new Date(new Date().getTime() + 1000), null, null);
        String json = objectMapper.writeValueAsString(cirkelsessie);

        this.mvc.perform(post("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        cirkelsessie = new Cirkelsessie("Session two", 5, 10, false, new Date(new Date().getTime() - 1000), null, null);
        json = objectMapper.writeValueAsString(cirkelsessie);

        this.mvc.perform(post("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        cirkelsessie = new Cirkelsessie("Session three", 5, 10, false, new Date(new Date().getTime() - 1000), null, null);
        json = objectMapper.writeValueAsString(cirkelsessie);

        this.mvc.perform(post("/api/cirkelsessies").contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        this.mvc.perform(get("/api/cirkelsessies/actief").contentType(MediaType.APPLICATION_JSON).header("Authorization", getUserToken()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].naam", is("Session two")))
            .andExpect(jsonPath("$[1].naam", is("Session three")));
    }
}
