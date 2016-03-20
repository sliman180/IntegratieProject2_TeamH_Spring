package be.kdg.teamh.exceptions.deelname;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DeelnameNietGevonden extends RuntimeException
{
    //
}
