package be.kdg.teamh.exceptions.subthema;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SubthemaNietGevonden extends RuntimeException
{
    //
}
