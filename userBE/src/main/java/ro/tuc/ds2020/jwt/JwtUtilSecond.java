package ro.tuc.ds2020.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.SignatureException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtilSecond {

    @Value("yshuieq2X+SQPiEuylnhDmp71LfKtpNWwkutRP5s6SI=")
    private String secretKey;

    public Claims extractAllClaims(String token) throws SignatureException {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    public String extractUsername(String token) throws SignatureException {
        return extractAllClaims(token).getSubject();
    }

    public List<SimpleGrantedAuthority> extractAuthorities(String token){
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        List<String> roles = claims.get("roles", List.class);
        System.out.println(roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList()));
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public Boolean validateToken(String token){
        try {
            extractAllClaims(token);
            return true;
        } catch (SignatureException e){
            // log exception
        }
        return false;
    }
}