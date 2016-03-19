package be.kdg.teamh.api;

import be.kdg.teamh.dtos.request.*;
import be.kdg.teamh.entities.Status;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

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

        http.perform(post("/api/organisaties", objectMapper.writeValueAsString(organisatie)).header("Authorization", getUserOneToken()));
        http.perform(post("/api/hoofdthemas", objectMapper.writeValueAsString(hoofdthema)).header("Authorization", getUserOneToken()));
        http.perform(post("/api/subthemas", objectMapper.writeValueAsString(subthema)).header("Authorization", getUserOneToken()));
        http.perform(post("/api/cirkelsessies", objectMapper.writeValueAsString(cirkelsessie)).header("Authorization", getUserOneToken()));
    }

    @Test
    public void updateDeelname() throws Exception
    {
        DeelnameRequest deelname = new DeelnameRequest(20, true, DateTime.now(), 1, 1);

        http.perform(post("/api/cirkelsessies/1/deelnames", objectMapper.writeValueAsString(deelname)).header("Authorization", getUserOneToken()));

        deelname = new DeelnameRequest(10, true, DateTime.now(), 1, 1);

        http.perform(put("/api/deelnames/1", objectMapper.writeValueAsString(deelname)).header("Authorization", getUserOneToken()))
            .andExpect(status().isOk());
    }

    @Test
    public void updateCirkelsessie_nonExistingDeelname() throws Exception
    {
        DeelnameRequest deelname = new DeelnameRequest(20, true, DateTime.now(), 1, 1);

        http.perform(put("/api/deelnames/1", objectMapper.writeValueAsString(deelname)).header("Authorization", getUserOneToken()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void deleteDeelname() throws Exception
    {
        DeelnameRequest deelname = new DeelnameRequest(15, false, DateTime.now(), 1, 1);

        http.perform(post("/api/cirkelsessies/1/deelnames", objectMapper.writeValueAsString(deelname)).header("Authorization", getUserOneToken()))
            .andExpect(status().isCreated());

        http.perform(delete("/api/deelnames/1").header("Authorization", getUserOneToken()))
            .andExpect(status().isOk());
    }

    @Test
    public void deleteDeelname_nonExistingDeelname() throws Exception
    {
        http.perform(delete("/api/deelnames/1").header("Authorization", getUserOneToken()))
            .andExpect(status().isNotFound());
    }
}
