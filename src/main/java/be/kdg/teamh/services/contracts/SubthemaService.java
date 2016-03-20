package be.kdg.teamh.services.contracts;

import be.kdg.teamh.dtos.request.KaartRequest;
import be.kdg.teamh.dtos.request.SubthemaRequest;
import be.kdg.teamh.entities.*;
import be.kdg.teamh.exceptions.gebruiker.GebruikerNietGevonden;
import be.kdg.teamh.exceptions.hoofdthema.HoofdthemaNietGevonden;
import be.kdg.teamh.exceptions.subthema.SubthemaNietGevonden;

import java.util.List;

public interface SubthemaService
{
    List<Subthema> all();

    void create(SubthemaRequest dto) throws HoofdthemaNietGevonden, GebruikerNietGevonden;

    Subthema find(int id) throws SubthemaNietGevonden;

    void update(int id, SubthemaRequest dto) throws SubthemaNietGevonden, HoofdthemaNietGevonden, GebruikerNietGevonden;

    void delete(int id) throws SubthemaNietGevonden;

    Organisatie findOrganisatie(int id) throws SubthemaNietGevonden;

    Hoofdthema findHoofdthema(int id) throws SubthemaNietGevonden;

    List<Cirkelsessie> getCirkelsessies(int id) throws SubthemaNietGevonden;

    List<Kaart> getKaarten(int id) throws SubthemaNietGevonden;

    void addKaart(int id, KaartRequest kaart) throws SubthemaNietGevonden, GebruikerNietGevonden;
}
