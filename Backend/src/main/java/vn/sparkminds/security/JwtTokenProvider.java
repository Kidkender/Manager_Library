package vn.sparkminds.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import vn.sparkminds.constants.SecurityContext;
import vn.sparkminds.model.User;

@Service
@Data
public class JwtTokenProvider {

    private static final long ACCESS_TOKEN_MINUTES = 60;
    private static final long REFRESH_TOKEN_HOURS = 24;
    SecretKey key = Keys.hmacShaKeyFor(SecurityContext.JWT_KEY.getBytes());

    public GenerateJwtResult generateToken(User user) {
        return generateToken(new HashMap<>(), user);
    }

    public GenerateJwtResult generateToken(Map<String, Object> extraClaims, User user) {
        String refreshTokenId = SecurityUtil.generateRandomCode();
        String accessTokenId = SecurityUtil.generateRandomCode();
        Date issueAt = new Date(System.currentTimeMillis());
        Set<String> authorities = Set.of(user.getRole().toString());

        String refreshToken = Jwts.builder().setId(refreshTokenId).claim("userId", user.getId())
                .setSubject(user.getEmail()).signWith(key).setIssuedAt(issueAt)
                .setExpiration(
                        new Date(System.currentTimeMillis() + 1000 * 60 * 60 * REFRESH_TOKEN_HOURS))
                .compact();

        String accessToken = Jwts.builder().setClaims(extraClaims).setId(accessTokenId)
                .claim("userId", user.getId()).setSubject(user.getEmail())
                .claim("refreshTokenId", refreshTokenId).claim("authorities", authorities)
                .setIssuedAt(issueAt)
                .setExpiration(
                        new Date(System.currentTimeMillis() + 1000 * 60 * ACCESS_TOKEN_MINUTES))
                .signWith(key).compact();

        return new GenerateJwtResult(accessTokenId, accessToken, refreshToken);
    }

    public ExtractToken extractToken(String token) {
        try {
            return new ExtractToken(StatusToken.VALID, extractAllClaims(token));
        } catch (ExpiredJwtException e) {
            return new ExtractToken(StatusToken.EXPIRED, e.getClaims());
        } catch (Exception e) {
            return new ExtractToken(StatusToken.INVALID, null);
        }
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public JwtTokenClaims getClaimsFromToken(String token) {

        SecretKey key = Keys.hmacShaKeyFor(SecurityContext.JWT_KEY.getBytes());
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        String username = String.valueOf(claims.get("username"));
        JwtTokenClaims jwtTokenClaims = new JwtTokenClaims();
        jwtTokenClaims.setUserName(username);
        return jwtTokenClaims;
    }
}

