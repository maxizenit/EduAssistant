package ru.itmo.eduassistant.commons.dto.user;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class NextUserResponse {
    private User current;
    private String messageForCurrent;
    private User next;
    private String messageForNext;
    private List<User> queue;

    public record User(
            long telegramId,
            String fio
    ) {

    }
}

