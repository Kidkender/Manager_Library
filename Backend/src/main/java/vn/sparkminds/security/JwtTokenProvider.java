package vn.sparkminds.security;

import javax.crypto.SecretKey;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import vn.sparkminds.constants.SecurityContext;

@Service
@Data
public class JwtTokenProvider {
    public JwtTokenClaims getClaimsFromToken(String token) {
        token = token.substring(7);
        SecretKey key = Keys.hmacShaKeyFor(SecurityContext.SECRET_KEY.getBytes());
        Claims claims =
                Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        String username = String.valueOf(claims.get("username"));
        JwtTokenClaims jwtTokenClaims = new JwtTokenClaims();
        jwtTokenClaims.setUserName(username);
        return jwtTokenClaims;
    }
}
