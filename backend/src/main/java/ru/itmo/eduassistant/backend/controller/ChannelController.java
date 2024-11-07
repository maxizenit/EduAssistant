package ru.itmo.eduassistant.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.itmo.eduassistant.backend.mapper.ChannelMapper;
import ru.itmo.eduassistant.backend.service.ChannelService;
import ru.itmo.eduassistant.commons.dto.channel.AllChannelsResponse;
import ru.itmo.eduassistant.commons.dto.channel.AllStudentsInChannelResponse;
import ru.itmo.eduassistant.commons.dto.channel.ChannelResponse;
import ru.itmo.eduassistant.commons.dto.channel.CreateChannelRequest;
import ru.itmo.eduassistant.commons.dto.notification.AllNotificationsResponse;

import java.util.List;

@RestController
@RequestMapping("/channel")
@RequiredArgsConstructor
public class ChannelController {

    private final ChannelMapper channelMapper;
    private final ChannelService channelService;

    @PostMapping
    public ChannelResponse createChannel(@RequestBody CreateChannelRequest createChannelRequest) {
        return channelMapper.toResponse(channelService.createChannel(createChannelRequest));
    }

    @PostMapping("/{channelId}/user/{telegramUserId}")
    public void addUserToChannel(@PathVariable long channelId, @PathVariable long telegramUserId) {
        channelService.addUserToChannel(channelId, telegramUserId);
    }

    @DeleteMapping("/{channelId}/user/{telegramUserId}")
    public void deleteUserFromChannel(@PathVariable long channelId, @PathVariable long telegramUserId) {
        channelService.deleteUserFromChannel(channelId, telegramUserId);
    }

    @GetMapping
    public AllChannelsResponse getAllStudentsChannel(@RequestParam long studentId) {
        List<ChannelResponse> response = channelService.getAllChannelByStudent(studentId)
                .stream()
                .map(channelMapper::toResponse)
                .toList();

        return new AllChannelsResponse(response);
    }

    @GetMapping("/teacher/{teacherId}")
    public AllChannelsResponse getAllTeachersChannel(@PathVariable long teacherId) {
        List<ChannelResponse> response = channelService.getAllTeachersChannel(teacherId)
                .stream()
                .map(channelMapper::toResponse)
                .toList();

        return new AllChannelsResponse(response);
    }

    @GetMapping("/{id}")
    public ChannelResponse getChannel(@PathVariable long id) {
        return channelMapper.toResponse(channelService.getChannel(id));
    }

    @GetMapping("/{id}/notifications")
    public AllNotificationsResponse getAllNotifications(@PathVariable long id) {
        return channelService.getAllNotifications(id);
    }

    @GetMapping("/{id}/students")
    public AllStudentsInChannelResponse getAllStudents(@PathVariable long id) {
        return channelService.getAllStudents(id);
    }
}
