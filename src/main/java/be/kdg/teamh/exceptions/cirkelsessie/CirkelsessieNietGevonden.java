package be.kdg.teamh.exceptions.cirkelsessie;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CirkelsessieNietGevonden extends RuntimeException
{
    //
}
