package ru.explorewithme.user.service;

import ru.explorewithme.user.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers(Integer from, Integer size);

    UserDto addUser(UserDto userDto);

    void deleteUser(Long id);
}
