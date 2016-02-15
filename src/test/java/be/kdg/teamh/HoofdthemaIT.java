package be.kdg.teamh;

import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.entities.Hoofdthema;
import be.kdg.teamh.entities.Organisatie;
import be.kdg.teamh.repositories.HoofdthemaRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class HoofdthemaIT
{
    Gebruiker gebruiker;
    Organisatie organisatie;

    @Autowired
    HoofdthemaRepository repository;

    @Before
    public void setUp()
    {
        gebruiker = new Gebruiker();
        organisatie = new Organisatie();
    }

    @Test
    public void createHoofdthema()
    {
        Hoofdthema hoofdthema = new Hoofdthema(1, "Voetbal", "Nieuwe voetbalveld", organisatie, gebruiker);

        repository.save(hoofdthema);

        assertEquals(1, repository.count());
    }
}
