package br.com.reinert.api.infra.security;

import br.com.reinert.api.domain.entities.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${application.security.jwt.secret-key}")
    private String secret_key;

    public String generate(User user) {
        try {
            return JWT.create()
                    .withIssuer("reinert-api")
                    .withSubject(user.getEmail())
                    .withClaim("scope", user.getRole().getValue())
                    .withExpiresAt(generateExpirationDate())
                    .sign(Algorithm.HMAC256(secret_key));

        } catch (JWTCreationException e) {
            throw new RuntimeException("Não foi possível autenticar", e);
        }
    }

    public String validate(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(secret_key))
                    .withIssuer("reinert-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            return null;
        }
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
