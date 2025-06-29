package br.com.reinert.api.converters;

import br.com.reinert.api.domain.entities.Notification;
import br.com.reinert.api.dto.NotificationDTO;
import lombok.experimental.UtilityClass;

import static java.util.Objects.isNull;

@UtilityClass
public class NotificationConverter {

    public NotificationDTO toNotificationDTO(Notification notification) {
        if (isNull(notification)) {
            return new NotificationDTO();
        }

        return NotificationDTO.builder()
                .id(String.valueOf(notification.getId()))
                .title(notification.getTitle())
                .message(notification.getMessage())
                .unread(notification.getUnread())
                .createdDate(notification.getCreatedDate())
                .build();

    }
}