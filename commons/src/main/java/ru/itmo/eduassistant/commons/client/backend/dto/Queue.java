package ru.itmo.eduassistant.commons.client.backend.dto;

import java.util.List;

public record Queue(List<User> users, Long subjectId, Boolean isActive, User current) {}
