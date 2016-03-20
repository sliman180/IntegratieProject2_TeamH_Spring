package be.kdg.teamh.exceptions.gebruiker;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class GebruikerBestaatAl extends RuntimeException
{
    //
}
