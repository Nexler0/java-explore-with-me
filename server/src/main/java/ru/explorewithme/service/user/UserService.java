package ru.explorewithme.service.user;

import ru.explorewithme.dto.request.RequestDto;
import ru.explorewithme.dto.user.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers(List<Long> userIds, Integer from, Integer size);

    UserDto addUser(UserDto userDto);

    void deleteUser(Long id);

    List<RequestDto> getAllUserRequests(Long userId);

    RequestDto postUserRequest(Long userId, Long eventId);

    RequestDto cancelRequestByUser(Long userId, Long requestId);
}
