package br.com.reinert.api.controller;

import br.com.reinert.api.converters.NotificationConverter;
import br.com.reinert.api.domain.entities.Notification;
import br.com.reinert.api.domain.entities.User;
import br.com.reinert.api.dto.ListResponseDTO;
import br.com.reinert.api.dto.NotificationDTO;
import br.com.reinert.api.infra.security.TokenService;
import br.com.reinert.api.service.NotificationService;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor(onConstructor_ = @Inject)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationController {

    UserService userService;
    TokenService tokenService;
    NotificationService notificationService;

    @GetMapping("/check")
    public ResponseEntity<ListResponseDTO<NotificationDTO>> getByUser(@RequestHeader("Authorization") String authHeader) {
        String token = AuthUtils.extractTokenFromAuthHeader(authHeader);
        String email = tokenService.validate(token);

        Optional<User> userOpt = userService.getByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            List<Notification> notifications = notificationService.getAllByUser(user);

            return ResponseEntity.ok(new ListResponseDTO<>(notifications.stream().map(NotificationConverter::toNotificationDTO).collect(Collectors.toList()), notifications.size()));
        }

        return ResponseEntity.ok(new ListResponseDTO<>(Collections.emptyList(), 0));
    }
}
