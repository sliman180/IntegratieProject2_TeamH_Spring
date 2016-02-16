package be.kdg.teamh.controllers;

import be.kdg.teamh.entities.Hoofdthema;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/hoofdthemas")
public class HoofdthemaController
{
    @RequestMapping(method = RequestMethod.GET)
    public List<Hoofdthema> index()
    {
        return new ArrayList<>();
    }
}
