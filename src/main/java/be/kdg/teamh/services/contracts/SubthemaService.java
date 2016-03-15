package be.kdg.teamh.services.contracts;

import be.kdg.teamh.dtos.request.SubthemaRequest;
import be.kdg.teamh.dtos.response.SubthemaResponse;
import be.kdg.teamh.entities.Subthema;
import be.kdg.teamh.exceptions.notfound.HoofdthemaNotFound;
import be.kdg.teamh.exceptions.notfound.SubthemaNotFound;

import java.util.List;

public interface SubthemaService
{
    List<SubthemaResponse> all();

    void create(SubthemaRequest subthema) throws HoofdthemaNotFound;

    SubthemaResponse find(int id) throws SubthemaNotFound;

    void update(int id, SubthemaRequest subthema) throws SubthemaNotFound, HoofdthemaNotFound;

    void delete(int id) throws SubthemaNotFound;
}
