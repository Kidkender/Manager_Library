package vn.sparkminds.security;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import io.jsonwebtoken.Claims;

public record ExtractToken(StatusToken statusToken, Claims claims) {
    public Set<String> getAuthorities() {
        if (statusToken != StatusToken.INVALID
                && claims.get("authorities") instanceof Collection<?> authorities) {
            return authorities.stream().map(String::valueOf).collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }

    public Long getUserId() {
        if (statusToken != StatusToken.INVALID) {
            return claims.get("userId", Long.class);
        }

        return null;
    }

    public String getEmail() {
        if (statusToken != StatusToken.INVALID) {
            return claims.getSubject();
        }
        return null;

    }

    public String getTokenId() {
        if (statusToken != StatusToken.INVALID) {
            return claims.getId();

        }

        return null;
    }
}
