package be.kdg.teamh.exceptions.kaart;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class CommentaarNietToegelaten extends RuntimeException
{
    //
}
