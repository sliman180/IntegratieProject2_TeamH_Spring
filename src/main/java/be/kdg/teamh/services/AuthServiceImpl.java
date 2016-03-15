package be.kdg.teamh.services;

import be.kdg.teamh.dtos.response.GebruikerResponse;
import be.kdg.teamh.dtos.response.LoginResponse;
import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.entities.Rol;
import be.kdg.teamh.exceptions.notfound.GebruikerNotFound;
import be.kdg.teamh.repositories.GebruikerRepository;
import be.kdg.teamh.services.contracts.AuthService;
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
    private GebruikerRepository repository;

    @Autowired
    public AuthServiceImpl(GebruikerRepository repository)
    {
        this.repository = repository;
    }

    @Override
    public LoginResponse generateToken(GebruikerResponse gebruiker)
    {
        Claims claims = Jwts.claims().setSubject(String.valueOf(gebruiker.getId()));

        claims.put("gebruikersnaam", gebruiker.getGebruikersnaam());
        claims.put("rollen", gebruiker.getRollen().stream().map(Rol::getNaam).collect(Collectors.toList()));

        return new LoginResponse(Jwts.builder().setClaims(claims).setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, key).compact());
    }

    @Override
    public GebruikerResponse findByToken(String token) throws GebruikerNotFound
    {
        Gebruiker gebruiker = repository.findOne(Integer.parseInt(parseToken(token).getSubject()));

        if (gebruiker == null)
        {
            throw new GebruikerNotFound();
        }

        GebruikerResponse dto = new GebruikerResponse();

        dto.setId(gebruiker.getId());
        dto.setGebruikersnaam(gebruiker.getGebruikersnaam());
        dto.setOrganisaties(gebruiker.getOrganisaties());
        dto.setHoofdthemas(gebruiker.getHoofdthemas());
        dto.setCirkelsessies(gebruiker.getCirkelsessies());
        dto.setKaarten(gebruiker.getKaarten());
        dto.setDeelnames(gebruiker.getDeelnames());
        dto.setBerichten(gebruiker.getBerichten());
        dto.setCommentaren(gebruiker.getCommentaren());
        dto.setRollen(gebruiker.getRollen());

        return dto;
    }

    @Override
    public boolean isGuest(String token)
    {
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
