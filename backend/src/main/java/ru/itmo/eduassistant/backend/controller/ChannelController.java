package ru.itmo.eduassistant.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.itmo.eduassistant.backend.mapper.ChannelMapper;
import ru.itmo.eduassistant.backend.service.ChannelService;
import ru.itmo.eduassistant.backend.service.impl.DialogServiceImpl;
import ru.itmo.eduassistant.backend.service.impl.NotificationService;
import ru.itmo.eduassistant.commons.dto.notification.AllNotificationsResponse;
import ru.itmo.eduassistant.commons.dto.notification.CreateNotificationRequest;
import ru.itmo.eduassistant.commons.dto.notofication.NotificationStatus;
import ru.itmo.eduassistant.commons.dto.question.AllQuestionsResponse;
import ru.itmo.eduassistant.commons.dto.question.NewMessageRequest;
import ru.itmo.eduassistant.commons.dto.question.NewMessageResponse;
import ru.itmo.eduassistant.commons.dto.channel.AllChannelsResponse;
import ru.itmo.eduassistant.commons.dto.channel.QuestionRequest;
import ru.itmo.eduassistant.commons.dto.channel.ChannelResponse;

import java.util.List;

@RestController
@RequestMapping("/channel")
@RequiredArgsConstructor
public class ChannelController {

    private final ChannelMapper channelMapper;
    private final ChannelService service;
    private final DialogServiceImpl dialogService;
    private final NotificationService notificationService;

    @GetMapping
    public AllChannelsResponse getAllStudentsChannel(@RequestParam long studentId) {
        List<ChannelResponse> response = service.getAllChannelByStudent(studentId)
                .stream()
                .map(channelMapper::toResponse)
                .toList();

        return new AllChannelsResponse(response);
    }

    @GetMapping("/teacher/{teacherId}")
    public AllChannelsResponse getAllTeachersChannel(@PathVariable long teacherId) {
        List<ChannelResponse> response = service.getAllTeachersChannel(teacherId)
                .stream()
                .map(channelMapper::toResponse)
                .toList();

        return new AllChannelsResponse(response);
    }

    @GetMapping("/{id}")
    public ChannelResponse getChannel(@PathVariable long id) {
        return channelMapper.toResponse(service.getChannel(id));
    }

    @GetMapping("/{id}/notifications")
    public AllNotificationsResponse getAllNotifications(@PathVariable long id,
                                                        @RequestParam NotificationStatus status) {
        return service.getAllNotifications(id, status);
    }

    @PostMapping("/notifications")
    public void createNotification(@RequestBody CreateNotificationRequest createNotificationRequest) {
        notificationService.createNotification(createNotificationRequest);
    }


    @PostMapping("/{id}/questions")
    void createQuestion(@PathVariable long id, @RequestBody QuestionRequest request) {
        service.createQuestion(id, request);
    }

    @PostMapping("/dialog/{dialogId}")
    public NewMessageResponse addMessageToDialog(@RequestBody NewMessageRequest newMessageRequest) {
        return new NewMessageResponse(dialogService.addMessage(newMessageRequest));
    }

    @GetMapping("/{id}/questions")
    public AllQuestionsResponse getAllQuestions(@PathVariable long id) {
        return new AllQuestionsResponse(service.getAllQuestions(id));
    }

    @PostMapping("/{id}/questions/{dialogId}")
    void markAsDiscussed(@PathVariable long id, @PathVariable long dialogId) {
        service.markAsDiscussed(id, dialogId);
    }
}
