package ru.itmo.eduassistant.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.eduassistant.backend.mapper.UserMapper;
import ru.itmo.eduassistant.backend.repository.UserRepository;
import ru.itmo.eduassistant.commons.dto.user.UserResponse;
import ru.itmo.eduassistant.commons.exception.EntityNotFoundException;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @GetMapping("/{telegramId}")
    public UserResponse getUser(@PathVariable long telegramId) {
        return userRepository.findByTelegramId(telegramId)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("User with id %s not found".formatted(telegramId)));
    }
}
