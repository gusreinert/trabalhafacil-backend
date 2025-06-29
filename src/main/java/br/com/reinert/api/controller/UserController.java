package br.com.reinert.api.controller;

import br.com.reinert.api.converters.UserConverter;
import br.com.reinert.api.domain.entities.User;
import br.com.reinert.api.dto.UserProfileDTO;
import br.com.reinert.api.infra.security.TokenService;
import br.com.reinert.api.service.UserService;
import br.com.reinert.api.utils.AuthUtils;
import jakarta.inject.Inject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor_ = @Inject)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;
    TokenService tokenService;

    @GetMapping("/me")
    public ResponseEntity<UserProfileDTO> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        String token = AuthUtils.extractTokenFromAuthHeader(authHeader);
        String email = tokenService.validate(token);

        Optional<User> userOpt = userService.getByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return ResponseEntity.ok(UserConverter.toUserProfileDTO(user));
        }

        return ResponseEntity.badRequest().build();
    }
}
