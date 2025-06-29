package br.com.reinert.api.service;

import br.com.reinert.api.domain.entities.User;
import br.com.reinert.api.repositories.UserRepository;
import jakarta.inject.Inject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = @Inject)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository repository;

    public User create(User user) {
        return repository.save(user);
    }

    public User loadByEmail(String email) throws RuntimeException {
        return repository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found!"));
    }

    public Optional<User> getByEmail(String email) {
        return repository.findByEmail(email);
    }
}
