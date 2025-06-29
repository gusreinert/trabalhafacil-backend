package br.com.reinert.api.converters;

import br.com.reinert.api.domain.entities.User;
import br.com.reinert.api.dto.UserDTO;
import br.com.reinert.api.dto.UserProfileDTO;
import lombok.experimental.UtilityClass;

import static java.util.Objects.isNull;

@UtilityClass
public class UserConverter {

    public UserDTO toUserDTO(User user) {
        if (isNull(user)) {
            return new UserDTO();
        }

        return UserDTO.builder()
                .id(String.valueOf(user.getId()))
                .name(user.getName())
                .role(user.getRole().getValue()).build();
    }

    public UserProfileDTO toUserProfileDTO(User user) {
        if (isNull(user)) {
            return new UserProfileDTO();
        }

        return UserProfileDTO.builder()
                .id(String.valueOf(user.getId()))
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole().getValue()).build();
    }
}
