package be.kdg.teamh.services;

import be.kdg.teamh.dtos.Token;
import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.entities.Rol;
import be.kdg.teamh.exceptions.GebruikerNotFound;
import be.kdg.teamh.services.contracts.AuthService;
import be.kdg.teamh.services.contracts.GebruikerService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService
{
    private String key = "kandoe";
    private GebruikerService service;

    @Autowired
    public AuthServiceImpl(GebruikerService service)
    {
        this.service = service;
    }

    @Override
    public Token generateToken(Gebruiker gebruiker)
    {
        Claims claims = Jwts.claims().setSubject(String.valueOf(gebruiker.getId()));

        claims.put("gebruikersnaam", gebruiker.getGebruikersnaam());
        claims.put("rollen", gebruiker.getRollen().stream().map(Rol::getNaam).collect(Collectors.toList()));

        return new Token(Jwts.builder().setClaims(claims).setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, key).compact());
    }

    @Override
    public Gebruiker findByToken(String token) throws GebruikerNotFound
    {
        return service.find(Integer.parseInt(parseToken(token).getSubject()));
    }

    @Override
    public boolean isGuest(String token) {
        return hasRole(token, "guest");
    }

    @Override
    public boolean isRegistered(String token)
    {
        return hasRole(token, "user");
    }

    @Override
    public boolean isAdmin(String token)
    {
        return hasRole(token, "admin");
    }

    private Claims parseToken(String token)
    {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token.substring(7)).getBody();
    }

    private boolean hasRole(String token, String user)
    {
        return ((List) parseToken(token).get("rollen")).contains(user);
    }
}
