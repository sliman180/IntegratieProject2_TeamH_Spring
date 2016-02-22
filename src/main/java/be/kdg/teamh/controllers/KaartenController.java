package be.kdg.teamh.controllers;

import be.kdg.teamh.entities.Kaart;
import be.kdg.teamh.services.KaartenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kaarten")
public class KaartenController {

    @Autowired
    private KaartenService kaartenService;


    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Kaart> index() {
        return kaartenService.all();
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public void create(@RequestBody Kaart kaart) {
        kaartenService.create(kaart);
    }

}
