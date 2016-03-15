package be.kdg.teamh.controllers;

import be.kdg.teamh.dtos.Token;
import be.kdg.teamh.entities.*;
import be.kdg.teamh.exceptions.GebruikerNotFound;
import be.kdg.teamh.exceptions.InvalidCredentials;
import be.kdg.teamh.exceptions.IsForbidden;
import be.kdg.teamh.repositories.CirkelsessieRepository;
import be.kdg.teamh.repositories.DeelnameRepository;
import be.kdg.teamh.services.contracts.AuthService;
import be.kdg.teamh.services.contracts.GebruikerService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final static String ROLLEN_CLAIM = "rollen";
    private final static String SIGNATURE = "kandoe";
    private final static int STRING_LENGTH = 10;

    @Autowired
    private GebruikerService service;

    @Autowired
    CirkelsessieRepository cirkelsessieRepository;

    @Autowired
    DeelnameRepository deelnameRepository;

    @Autowired
    AuthService auth;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Token login(@RequestBody Gebruiker credentials) throws GebruikerNotFound, InvalidCredentials {
        Gebruiker gebruiker = service.findByLogin(credentials);


        return new Token(Jwts.builder().setSubject(String.valueOf(gebruiker.getId()))
                .claim(ROLLEN_CLAIM, gebruiker.getRollen().stream().map(Rol::getNaam).collect(Collectors.toList()))
                .setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, SIGNATURE).compact());
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public void register(@RequestBody Gebruiker credentials) {
        service.create(credentials);
    }

    @RequestMapping(value = "guest/{id}", method = RequestMethod.POST)
    public Token generateGuestToken(@PathVariable("id") int cirkelsessieId,
                                    @RequestHeader(name = "Authorization") String token) throws IsForbidden {
        if (!auth.isRegistered(token)) throw new IsForbidden();

        Gebruiker gast = new Gebruiker(generateString(STRING_LENGTH), generateString(STRING_LENGTH));
        Cirkelsessie cirkelsessie = cirkelsessieRepository.findOne(cirkelsessieId);
        Deelname deelname = new Deelname(0, false, cirkelsessie, gast);
        deelnameRepository.save(deelname);
        gast.addDeelname(deelname);
        service.createGast(gast);

        return new Token(Jwts.builder().setSubject(String.valueOf(gast.getId()))
                .claim(ROLLEN_CLAIM, gast.getRollen().stream().map(Rol::getNaam).collect(Collectors.toList()))
                .setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, SIGNATURE).compact());
    }

    public static String generateString(int length)
    {
        Random random = new Random();
        String characters = "abcdefghijklmnopqrstuvwxyz0123456789";
        char[] text = new char[length];
        for (int i = 0; i < length; i++)
        {
            text[i] = characters.charAt(random.nextInt(characters.length()));
        }
        return new String(text);
    }
}
