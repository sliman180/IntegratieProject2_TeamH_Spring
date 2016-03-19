package be.kdg.teamh.api;

import be.kdg.teamh.dtos.request.*;
import be.kdg.teamh.entities.Cirkelsessie;
import be.kdg.teamh.entities.Deelname;
import be.kdg.teamh.entities.Status;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.util.NestedServletException;

import java.time.LocalDateTime;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DeelnameApiTest extends ApiTest
{
    @Before
    public void setUpParents() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Naam Organisatie", "Beschrijving Organisatie", 1);
        HoofdthemaRequest hoofdthema = new HoofdthemaRequest("Naam Hoofdthema", "Beschrijving Hoofdthema", 1, 1);
        SubthemaRequest subthema = new SubthemaRequest("Naam Subthema", "Beschrijving Subthema", 1, 1);
        CirkelsessieRequest cirkelsessie = new CirkelsessieRequest("Naam Cirkelsessie", Status.OPEN, 10, 10, DateTime.now(), 1, 1);

        http.perform(post("/api/organisaties", objectMapper.writeValueAsString(organisatie)).header("Authorization", getUserToken()));
        http.perform(post("/api/hoofdthemas", objectMapper.writeValueAsString(hoofdthema)).header("Authorization", getUserToken()));
        http.perform(post("/api/subthemas", objectMapper.writeValueAsString(subthema)).header("Authorization", getUserToken()));
        http.perform(post("/api/cirkelsessies", objectMapper.writeValueAsString(cirkelsessie)).header("Authorization", getUserToken()));
    }

    @Test
    public void updateDeelname() throws Exception
    {
        DeelnameRequest deelname = new DeelnameRequest(20, true, DateTime.now(), 1, 1);

        http.perform(post("/api/cirkelsessies/1/deelnames", objectMapper.writeValueAsString(deelname)).header("Authorization", getAdminToken()));

        deelname = new DeelnameRequest(10, true, DateTime.now(), 1, 1);

        http.perform(put("/api/deelnames/1", objectMapper.writeValueAsString(deelname)).header("Authorization", getAdminToken()))
            .andExpect(status().isOk());
    }

    @Test
    public void updateCirkelsessie_nonExistingDeelname() throws Exception
    {
        DeelnameRequest deelname = new DeelnameRequest(20, true, DateTime.now(), 1, 1);

        http.perform(put("/api/deelnames/1", objectMapper.writeValueAsString(deelname)).header("Authorization", getAdminToken()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void deleteDeelname() throws Exception
    {
        DeelnameRequest deelname = new DeelnameRequest(15, false, DateTime.now(), 1, 1);

        http.perform(post("/api/cirkelsessies/1/deelnames", objectMapper.writeValueAsString(deelname)).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        http.perform(delete("/api/deelnames/1").header("Authorization", getAdminToken()))
            .andExpect(status().isOk());
    }

    @Test
    public void deleteDeelname_nonExistingDeelname() throws Exception
    {
        http.perform(delete("/api/deelnames/1").header("Authorization", getAdminToken()))
            .andExpect(status().isNotFound());
    }
}
