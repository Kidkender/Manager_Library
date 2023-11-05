package vn.sparkminds.security;

import java.util.UUID;

public final class SecurityUtil {
    private SecurityUtil() {};

    public static String generateRandomCode() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
