package be.kdg.teamh.exceptions.spelkaart;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SpelkaartNietGevonden extends RuntimeException
{
    //
}
