package br.com.reinert.api.utils;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@UtilityClass
public class AuthUtils {

    private static final String BEARER = "Bearer";

    public static String extractTokenFromAuthHeader(String authHeader) {
        if (isNull(authHeader) || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid authorization header!");
        }

        return authHeader.replace(BEARER, "").stripLeading();
    }

    public static String extractTokenFromAuthHeader(HttpHeaders authHeader) {
        if (isNull(authHeader) || nonNull(authHeader.getFirst("Authorization"))) {
            throw new IllegalArgumentException("Invalid authorization header!");
        }

        return authHeader.getFirst("Authorization").replace(BEARER, "").stripLeading();
    }
}
