package ru.itmo.eduassistant.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.itmo.eduassistant.backend.entity.User;
import ru.itmo.eduassistant.commons.dto.queue.AllStudentInQueueResponse;
import ru.itmo.eduassistant.commons.dto.user.UserResponse;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserResponse toResponse(User user);
    AllStudentInQueueResponse.User toAllStudentsResponse(User user);
    default AllStudentInQueueResponse toResponse(List<User> users) {
        return new AllStudentInQueueResponse(
                users.stream()
                        .map(this::toAllStudentsResponse)
                        .toList()
        );
    }
}
