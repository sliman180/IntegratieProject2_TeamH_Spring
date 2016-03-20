package be.kdg.teamh.exceptions.gebruiker;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class GebruikerNietGevonden extends RuntimeException
{
    //
}
