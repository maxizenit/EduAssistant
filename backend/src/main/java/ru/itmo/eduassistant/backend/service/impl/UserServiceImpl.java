package ru.itmo.eduassistant.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.eduassistant.backend.entity.User;
import ru.itmo.eduassistant.backend.repository.UserRepository;
import ru.itmo.eduassistant.backend.service.UserService;
import ru.itmo.eduassistant.commons.exception.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public User getUserByTelegramId(Long id) {
        return userRepository.findByTelegramId(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id %s not found".formatted(id)));
    }
}
