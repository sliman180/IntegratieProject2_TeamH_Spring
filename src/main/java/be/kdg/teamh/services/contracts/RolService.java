package be.kdg.teamh.services.contracts;

import be.kdg.teamh.dtos.request.RolRequest;
import be.kdg.teamh.dtos.response.RolResponse;
import be.kdg.teamh.exceptions.notfound.RolNotFound;

import java.util.List;

public interface RolService
{
    List<RolResponse> all();

    void create(RolRequest dto);

    RolResponse find(int id) throws RolNotFound;

    void update(int id, RolRequest dto) throws RolNotFound;

    void delete(int id) throws RolNotFound;
}
