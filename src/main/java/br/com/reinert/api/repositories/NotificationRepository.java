package br.com.reinert.api.repositories;

import br.com.reinert.api.domain.entities.Notification;
import br.com.reinert.api.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    List<Notification> findAllByUser(User user);
}