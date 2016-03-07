package be.kdg.teamh;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.web.filter.GenericFilterBean;

import javax.naming.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtFilter extends GenericFilterBean
{
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException
    {
        HttpServletRequest request = (HttpServletRequest) req;
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer "))
        {
            throw new ServletException("Missing or invalid Authorization header.");
        }

        String token = authHeader.substring(7);

        try
        {
            request.setAttribute("claims", Jwts.parser().setSigningKey("kandoe").parseClaimsJws(token).getBody());
        }
        catch (SignatureException e)
        {
            throw new SignatureException("Invalid token.");
        }

        chain.doFilter(req, res);
    }
}
