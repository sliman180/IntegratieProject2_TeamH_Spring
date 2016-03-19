package be.kdg.teamh.services.contracts;

import be.kdg.teamh.dtos.request.KaartRequest;
import be.kdg.teamh.dtos.request.SubthemaRequest;
import be.kdg.teamh.entities.*;
import be.kdg.teamh.exceptions.notfound.GebruikerNotFound;
import be.kdg.teamh.exceptions.notfound.HoofdthemaNotFound;
import be.kdg.teamh.exceptions.notfound.SubthemaNotFound;

import java.util.List;

public interface SubthemaService
{
    List<Subthema> all();

    void create(SubthemaRequest dto) throws HoofdthemaNotFound, GebruikerNotFound;

    Subthema find(int id) throws SubthemaNotFound;

    void update(int id, SubthemaRequest dto) throws SubthemaNotFound, HoofdthemaNotFound, GebruikerNotFound;

    void delete(int id) throws SubthemaNotFound;

    Organisatie findOrganisatie(Integer id) throws SubthemaNotFound;

    Hoofdthema findHoofdthema(Integer id) throws SubthemaNotFound;

    void addKaart(int subthemaId, KaartRequest kaart) throws SubthemaNotFound, GebruikerNotFound;

    List<Kaart> findKaarten(Integer id) throws SubthemaNotFound;

    List<Cirkelsessie> findCirkelsessies(Integer id) throws SubthemaNotFound;
}
