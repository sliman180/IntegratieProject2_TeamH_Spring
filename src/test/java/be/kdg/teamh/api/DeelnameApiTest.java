package be.kdg.teamh.api;

import be.kdg.teamh.dtos.request.*;
import be.kdg.teamh.entities.Cirkelsessie;
import be.kdg.teamh.entities.Deelname;
import be.kdg.teamh.entities.Status;
import org.junit.Test;
import org.springframework.web.util.NestedServletException;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DeelnameApiTest extends ApiTest
{
    @Test
    public void updateDeelname() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Voetbal", "Nieuw voetbalveld", 1);
        String json = objectMapper.writeValueAsString(organisatie);

        http.perform(post("/api/organisaties", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        HoofdthemaRequest hoofdthema = new HoofdthemaRequest("Voetbal", "Nieuw voetbalveld", 1, 1);
        json = objectMapper.writeValueAsString(hoofdthema);

        http.perform(post("/api/hoofdthemas", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        SubthemaRequest subthema = new SubthemaRequest("Houffalize", "Route 6", 1, 1);
        json = objectMapper.writeValueAsString(subthema);

        http.perform(post("/api/subthemas", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        CirkelsessieRequest cirkelsessie = new CirkelsessieRequest("Een cirkelsessie", Status.OPEN, 10, 10, LocalDateTime.now(), 1, 1);
        json = objectMapper.writeValueAsString(cirkelsessie);

        http.perform(post("/api/cirkelsessies", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        DeelnameRequest deelname = new DeelnameRequest(20, true, LocalDateTime.now(), 1, 1);
        json = objectMapper.writeValueAsString(deelname);

        http.perform(post("/api/cirkelsessies/1/deelnames", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        deelname = new DeelnameRequest(10, true, LocalDateTime.now(), 1, 1);
        json = objectMapper.writeValueAsString(deelname);

        http.perform(put("/api/deelnames/1", json).header("Authorization", getAdminToken()))
            .andExpect(status().isOk());
    }

    @Test(expected = NestedServletException.class)
    public void updateCirkelsessie_nonExistingDeelname() throws Exception
    {
        DeelnameRequest deelname = new DeelnameRequest(20, true, LocalDateTime.now(), 1, 1);
        String json = objectMapper.writeValueAsString(deelname);

        http.perform(put("/api/deelnames/1", json).header("Authorization", getAdminToken()));
    }

    @Test
    public void deleteDeelname() throws Exception
    {
        OrganisatieRequest organisatie = new OrganisatieRequest("Voetbal", "Nieuw voetbalveld", 1);
        String json = objectMapper.writeValueAsString(organisatie);

        http.perform(post("/api/organisaties", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        HoofdthemaRequest hoofdthema = new HoofdthemaRequest("Voetbal", "Nieuw voetbalveld", 1, 1);
        json = objectMapper.writeValueAsString(hoofdthema);

        http.perform(post("/api/hoofdthemas", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        SubthemaRequest subthema = new SubthemaRequest("Houffalize", "Route 6", 1, 1);
        json = objectMapper.writeValueAsString(subthema);

        http.perform(post("/api/subthemas", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        CirkelsessieRequest cirkelsessie = new CirkelsessieRequest("Een cirkelsessie", Status.OPEN, 10, 10, LocalDateTime.now(), 1, 1);
        json = objectMapper.writeValueAsString(cirkelsessie);

        http.perform(post("/api/cirkelsessies", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        DeelnameRequest deelname = new DeelnameRequest(15, false, LocalDateTime.now(), 1, 1);
        json = objectMapper.writeValueAsString(deelname);

        http.perform(post("/api/cirkelsessies/1/deelnames", json).header("Authorization", getAdminToken()))
            .andExpect(status().isCreated());

        http.perform(delete("/api/deelnames/1").header("Authorization", getAdminToken()))
            .andExpect(status().isOk());
    }

    @Test(expected = NestedServletException.class)
    public void deleteDeelname_nonExistingDeelname() throws Exception
    {
        http.perform(delete("/api/deelnames/1").header("Authorization", getAdminToken()));
    }
}
