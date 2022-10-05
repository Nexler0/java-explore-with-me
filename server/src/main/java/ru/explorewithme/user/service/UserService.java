package ru.explorewithme.user.service;

import ru.explorewithme.request.dto.RequestDto;
import ru.explorewithme.user.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers(List<Long> userIds, Integer from, Integer size);

    UserDto addUser(UserDto userDto);

    void deleteUser(Long id);

    List<RequestDto> getAllUserRequests(Long userId);

    RequestDto postUserRequest(Long userId, Long eventId);

    RequestDto cancelRequestByUser(Long userId, Long requestId);
}
