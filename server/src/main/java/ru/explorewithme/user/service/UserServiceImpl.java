package ru.explorewithme.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.explorewithme.user.dto.UserDto;
import ru.explorewithme.user.dto.UserMapper;
import ru.explorewithme.user.model.User;
import ru.explorewithme.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserDto> getAllUsers(Integer from, Integer size) {
        return userRepository.findAll(PageRequest.of(from, size))
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        User result = userRepository.save(UserMapper.toUser(userDto));
        return UserMapper.toDto(result);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
