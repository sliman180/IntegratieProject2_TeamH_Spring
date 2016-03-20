package be.kdg.teamh.exceptions.kaart;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class KaartNietGevonden extends RuntimeException
{
    //
}
