package vn.sparkminds.config;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.crypto.SecretKey;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.sparkminds.constants.SecurityContext;

public class JwtTokenGenerator extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            SecretKey key = Keys.hmacShaKeyFor(SecurityContext.SECRET_KEY.getBytes());
            String jwt = Jwts.builder().setIssuer("library").setIssuedAt(new Date())
                    .claim("authorities", populateAuthorities(authentication.getAuthorities()))
                    .claim("username", authentication.getName())
                    .setExpiration(new Date(new Date().getTime() + 3600000)).signWith(key)
                    .compact();
            response.setHeader("Content-Type", "application/json");
            response.setHeader(SecurityContext.JWT_HEADER, jwt);
        }
        filterChain.doFilter(request, response);
    }

    public String populateAuthorities(Collection<? extends GrantedAuthority> collection)
            throws ServletException {
        Set<String> authorities = new HashSet<String>();
        for (GrantedAuthority authority : collection) {
            authorities.add(authority.getAuthority());
        }
        return String.join(",", authorities);
    }

    public boolean shouldNotFilter(HttpServletRequest req) {
        return !req.getServletPath().equals("/signin");
    }
}
