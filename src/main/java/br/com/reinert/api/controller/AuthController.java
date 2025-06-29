package br.com.reinert.api.controller;

import br.com.reinert.api.converters.UserConverter;
import br.com.reinert.api.domain.entities.User;
import br.com.reinert.api.domain.enums.Role;
import br.com.reinert.api.dto.AuthResponseDTO;
import br.com.reinert.api.dto.LoginDTO;
import br.com.reinert.api.dto.RegisterDTO;
import br.com.reinert.api.infra.security.TokenService;
import br.com.reinert.api.service.UserService;
import jakarta.inject.Inject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor(onConstructor_ = @Inject)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    UserService userService;
    TokenService tokenService;
    PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO body) {
        Optional<User> userOpt = userService.getByEmail(body.email());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(body.password(), user.getPassword())) {
                String token = tokenService.generate(user);
                return ResponseEntity.ok(new AuthResponseDTO(UserConverter.toUserDTO(user), token));
            }
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterDTO body) {
        Optional<User> userOpt = userService.getByEmail(body.email());
        if (userOpt.isEmpty()) {
            var user = createUser(body);
            String token = tokenService.generate(user);

            return ResponseEntity.ok(new AuthResponseDTO(UserConverter.toUserDTO(user), token));
        }

        return ResponseEntity.badRequest().build();
    }

    private User createUser(RegisterDTO registerDTO) {
        User user = new User();
        user.setName(registerDTO.name());
        user.setEmail(registerDTO.email());
        user.setPassword(passwordEncoder.encode(registerDTO.password()));
        user.setRole(Role.valueOf(registerDTO.role()));

        return userService.create(user);
    }
}
