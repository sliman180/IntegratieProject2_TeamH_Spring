package be.kdg.teamh.controllers;

import be.kdg.teamh.dtos.LoginResponse;
import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.entities.Rol;
import be.kdg.teamh.exceptions.GebruikerNotFound;
import be.kdg.teamh.exceptions.InvalidCredentials;
import be.kdg.teamh.services.contracts.GebruikerService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController
{
    @Autowired
    private GebruikerService service;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public LoginResponse login(@RequestBody Gebruiker credentials) throws GebruikerNotFound, InvalidCredentials
    {
        Gebruiker gebruiker = service.findByLogin(credentials);

        return new LoginResponse(gebruiker.getId(), gebruiker.getGebruikersnaam(), Jwts.builder().setSubject(gebruiker.getGebruikersnaam())
            .claim("roles", gebruiker.getRollen().stream().map(Rol::getNaam).collect(Collectors.toList()))
            .setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, "kandoe").compact());
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public void register(@RequestBody Gebruiker credentials)
    {
        service.create(credentials);
    }
}
