package ru.itmo.eduassistant.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.eduassistant.backend.mapper.UserMapper;
import ru.itmo.eduassistant.backend.service.UserService;
import ru.itmo.eduassistant.commons.dto.user.UserResponse;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserMapper userMapper;
    private final UserService userService;

    @GetMapping("/{telegramId}")
    public UserResponse getUser(@PathVariable long telegramId) {
        return userMapper.toResponse(userService.getUserByTelegramId(telegramId));
    }
}
