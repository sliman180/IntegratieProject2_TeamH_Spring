package be.kdg.teamh.exceptions.hoofdthema;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class HoofdthemaNietGevonden extends RuntimeException
{
    //
}
