package be.kdg.teamh.controllers;

import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.dto.Token;
import be.kdg.teamh.entities.Rol;
import com.google.common.hash.Hashing;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController
{
    private final List<Gebruiker> userDb = new ArrayList<>();

    public AuthController()
    {
        List<Rol> user = new ArrayList<>();
        List<Rol> admin = new ArrayList<>();

        user.add(new Rol("user", "user"));
        admin.add(new Rol("user", "user"));
        admin.add(new Rol("admin", "admin"));

        userDb.add(new Gebruiker("user", Hashing.sha256().hashString("user", StandardCharsets.UTF_8).toString(), user));
        userDb.add(new Gebruiker("admin", Hashing.sha256().hashString("admin", StandardCharsets.UTF_8).toString(), admin));
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Token login(@RequestBody final Gebruiker login) throws Exception
    {
        login.setWachtwoord(Hashing.sha256().hashString(login.getWachtwoord(), StandardCharsets.UTF_8).toString());

        if (!userDb.contains(login))
        {
            throw new Exception("Invalid login");
        }

        return new Token(Jwts.builder()
            .setSubject(login.getGebruikersnaam())
            .claim("roles", login.getRollen().stream().map(Rol::getNaam).collect(Collectors.toList()))
            .setIssuedAt(new Date())
            .signWith(SignatureAlgorithm.HS256, "kandoe")
            .compact());
    }
}
