package be.kdg.teamh.services;

import be.kdg.teamh.entities.Organisatie;

import java.util.List;

public interface OrganisatieService
{
    List<Organisatie> all();
    void create(Organisatie organisatie);
    Organisatie find(int id);
    void update(int id, Organisatie organisatie);
    void delete(int id);
}
