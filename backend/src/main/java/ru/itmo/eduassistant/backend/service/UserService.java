package ru.itmo.eduassistant.backend.service;

import ru.itmo.eduassistant.backend.entity.User;

public interface UserService {
    User getUserByTelegramId(Long id);
}
