package ru.itmo.eduassistant.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.itmo.eduassistant.backend.mapper.DialogMapper;
import ru.itmo.eduassistant.backend.mapper.MessageMapper;
import ru.itmo.eduassistant.backend.service.impl.DialogServiceImpl;
import ru.itmo.eduassistant.commons.dto.dialog.*;

@RestController
@RequestMapping("/dialog")
@RequiredArgsConstructor
public class DialogController {

    private final MessageMapper messageMapper;
    private final DialogServiceImpl dialogService;
    private final DialogMapper dialogMapper;

    @PostMapping("/question")
    public QuestionResponse createQuestion(NewQuestionRequest request) {
        return messageMapper.toQuestionResponse(dialogService.createQuestion(request));
    }

    @GetMapping
    public AllDialogsResponse getDialogs(@RequestParam Long userId) {
        var dialogs = dialogService.getAllQuestions(userId).stream()
                .map(dialogMapper::toQuestionResponse).toList();
        return new AllDialogsResponse(dialogs);
    }

    @PostMapping
    public MessageResponse addMessageToDialog(@RequestBody NewMessageRequest newMessageRequest) {
        return messageMapper.toMessageResponse(dialogService.addMessage(newMessageRequest));
    }

    @PostMapping("/close")
    public void markAsDiscussed(@RequestParam Long dialogId) {
        dialogService.markAsDiscussed(dialogId);
    }

    @GetMapping("/{id}")
    public AllMessagesResponse getDialog(@PathVariable Long id) {
        return new AllMessagesResponse(dialogService.getAllMessagesInDialog(id)
                .stream()
                .map(messageMapper::toMessageResponse)
                .toList());
    }
}
