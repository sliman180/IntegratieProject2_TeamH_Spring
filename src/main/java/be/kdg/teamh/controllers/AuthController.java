package be.kdg.teamh.controllers;

import be.kdg.teamh.dtos.request.LoginRequest;
import be.kdg.teamh.dtos.request.RegistratieRequest;
import be.kdg.teamh.dtos.response.LoginResponse;
import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.exceptions.InvalidCredentials;
import be.kdg.teamh.exceptions.PasswordsDoNotMatch;
import be.kdg.teamh.exceptions.notfound.GebruikerNotFound;
import be.kdg.teamh.exceptions.notfound.RolNotFound;
import be.kdg.teamh.services.contracts.AuthService;
import be.kdg.teamh.services.contracts.GebruikerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private GebruikerService service;
    private AuthService auth;

    @Autowired
    public AuthController(AuthService auth, GebruikerService service) {
        this.auth = auth;
        this.service = service;
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public LoginResponse login(@RequestBody LoginRequest login) throws GebruikerNotFound, InvalidCredentials {
        Gebruiker gebruiker = service.findByLogin(login);

        return auth.generateToken(gebruiker);
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public void register(@RequestBody RegistratieRequest registratie) throws RolNotFound, PasswordsDoNotMatch {
        service.register(registratie);
    }
}
