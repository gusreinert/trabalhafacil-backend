package br.com.reinert.api.service;

import br.com.reinert.api.domain.entities.Notification;
import br.com.reinert.api.domain.entities.User;
import br.com.reinert.api.repositories.NotificationRepository;
import jakarta.inject.Inject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Inject)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationService {

    NotificationRepository notificationRepository;

    public void sendToUser(User user, String title, String message) {
        var notification = new Notification();
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setUser(user);
        notificationRepository.save(notification);
    }

    public List<Notification> getAllByUser(User user) {
        return notificationRepository.findAllByUser(user);
    }
}
