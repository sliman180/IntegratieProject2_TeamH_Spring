package be.kdg.teamh.controllers;

import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.entities.LoginResponse;
import be.kdg.teamh.entities.Rol;
import com.google.common.hash.Hashing;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.*;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.servlet.ServletException;

@RestController
@RequestMapping("/user")
public class UserController {
    private final List<Gebruiker> userDb;

    public UserController() {
        userDb = new ArrayList<>();
        List<Rol> rollen = new ArrayList<>();
        rollen.add(new Rol("user", "user"));
        String gehasteWachtwoord = Hashing.sha256()
                .hashString("user", StandardCharsets.UTF_8)
                .toString();
        userDb.add(new Gebruiker("user", gehasteWachtwoord, rollen));

        List<Rol> rollen2 = new ArrayList<>();

        rollen2.add(new Rol("admin", "admin"));
        rollen2.add(new Rol("user", "user"));
        gehasteWachtwoord = Hashing.sha256()
                .hashString("admin", StandardCharsets.UTF_8)
                .toString();
        userDb.add(new Gebruiker("admin", gehasteWachtwoord, rollen2));
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public LoginResponse login(@RequestBody final Gebruiker login) throws ServletException {
        String gehasteWachtwoord = Hashing.sha256()
                .hashString(login.getWachtwoord(), StandardCharsets.UTF_8)
                .toString();
        login.setWachtwoord(gehasteWachtwoord);
        if (login.getGebruikersnaam() == null || !userDb.contains(login)) {
            throw new ServletException("Invalid login");
        }

        String[] rollen = rolnamen(login.getRollen());

        return new LoginResponse(Jwts.builder().setSubject(login.getGebruikersnaam())
                .claim("roles", rollen).setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "secretKey").compact());
    }

    private String[] rolnamen(List<Rol> rols) {
        String[] temp = new String[rols.size()];
        for (int i = 0; i < rols.size(); i++) {
            temp[i] = rols.get(i).getNaam();
        }

        return temp;
    }
}
