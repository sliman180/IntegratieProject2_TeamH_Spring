package be.kdg.teamh.controllers;

import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.entities.LoginResponse;
import be.kdg.teamh.entities.Rol;
import com.google.common.hash.Hashing;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
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
    public LoginResponse login(@RequestBody final Gebruiker login) throws Exception
    {
        login.setWachtwoord(Hashing.sha256().hashString(login.getWachtwoord(), StandardCharsets.UTF_8).toString());

        if (!userDb.contains(login))
        {
            throw new Exception("Invalid login");
        }

        return new LoginResponse(Jwts.builder().setSubject(login.getGebruikersnaam()).claim("roles", rolnamen(login.getRollen())).setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, "secretKey").compact());
    }

    private String[] rolnamen(List<Rol> rollen)
    {
        String[] _rollen = new String[rollen.size()];

        for (int i = 0; i < rollen.size(); i++)
        {
            _rollen[i] = rollen.get(i).getNaam();
        }

        return _rollen;
    }
}
