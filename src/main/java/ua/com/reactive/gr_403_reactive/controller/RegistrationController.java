package ua.com.reactive.gr_403_reactive.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import ua.com.reactive.gr_403_reactive.entity.RolesHasUsers;
import ua.com.reactive.gr_403_reactive.entity.User;
import ua.com.reactive.gr_403_reactive.repository.RoleRepository;
import ua.com.reactive.gr_403_reactive.repository.RolesHasUsersRepository;
import ua.com.reactive.gr_403_reactive.repository.UserRepository;

@RestController
@RequiredArgsConstructor
public class RegistrationController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RolesHasUsersRepository rolesHasUsersRepository;

    // Реєстрація нового користувача: зберігаємо його і призначаємо роль ROLE_User
    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<User> register(@RequestBody User request) {

        if (request.getUsername() == null || request.getUsername().isBlank()
                || request.getPassword() == null || request.getPassword().isBlank()) {
            return Mono.error(new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "username and password are required"));
        }

        return userRepository.findByUsername(request.getUsername())
                // такий логін уже є -> помилка 409
                .flatMap(existing -> Mono.<User>error(new ResponseStatusException(
                        HttpStatus.CONFLICT, "User " + request.getUsername() + " already exists")))
                // логіна немає -> створюємо користувача
                .switchIfEmpty(Mono.defer(() ->
                        userRepository.save(new User(null, request.getUsername(), request.getPassword()))
                                .flatMap(saved -> roleRepository.findByName("ROLE_User")
                                        .flatMap(role -> rolesHasUsersRepository.save(
                                                new RolesHasUsers(null, role.getId(), saved.getId())))
                                        .thenReturn(saved))
                ));
    }
}